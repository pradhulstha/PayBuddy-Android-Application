package com.example.prash.paybuddy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prash.paybuddy.AccountActivity.Account_setting;
import com.example.prash.paybuddy.AccountActivity.Reset_password;
import com.example.prash.paybuddy.AccountActivity.save_pay;
import com.example.prash.paybuddy.Config.Config;
import com.paypal.android.sdk.payments.LoginActivity;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import com.example.prash.paybuddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    public static final int PAYPAL_REQUEST_CODE = 7171;
    private static final String TAG = "Main Activity";
    private String userID;



    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);


    Button btnPayNow, reset_btn;
    EditText edtAmount;
    String amount = "";
    FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    @Override
    protected void onDestroy(){
        stopService(new Intent(this, PayPalService.class));
                super.onDestroy();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get firebase auth instance
        final FirebaseAuth auth = FirebaseAuth.getInstance();

        //Starting PayPal Service
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);

        btnPayNow = (Button)findViewById(R.id.btnPayNow);
        edtAmount = (EditText)findViewById(R.id.edtAmount);


        reset_btn = (Button)findViewById(R.id.reset_btn);



        btnPayNow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                processPayment();
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Account_setting.class));
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    // toastMessage("Successfully Signed in");
                } else {
                    startActivity(new Intent(MainActivity.this, save_pay.class));
                    finish();
                }
            }
        };


// this listener will be called when there is change in firebase user session
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    mAuth.signOut();
                    startActivity(new Intent(MainActivity.this, com.example.prash.paybuddy.AccountActivity.LoginActivity.class));
                    finish();
                }
            }


        };

    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(MainActivity.this, save_pay.class));
        finish();
    }

    private void processPayment() {
        amount = edtAmount.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD",
                  "Pay to your Friend", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config );
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PAYPAL_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if(confirmation != null)
                {
                    try{
                        String paymentDetails = confirmation.toJSONObject().toString(4);

                        startActivity(new Intent(this, PaymentDetails.class)
                                .putExtra("PaymentDetails",paymentDetails)
                                .putExtra("PaymentAmount",amount)
                        );

                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            }
            else if(resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this,"Cancel", Toast.LENGTH_SHORT).show();
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this,"Invalid", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // user auth state is changed - user is null
            // launch login activity
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onStop() {
        super.onStop();
        // this listener will be called when there is change in firebase user session
    }
}
