package com.example.prash.paybuddy;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.microedition.khronos.egl.EGLDisplay;

public class SavingCard extends AppCompatActivity {

    private EditText editCardName,cardNumber, date, cvv;
    private Button btnsave;
    Spinner crdtype;

    //FireBase Objetcs
    //adding Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabse;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    final int i = 0;

    DatabaseReference databaseCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_card);

        databaseCard = FirebaseDatabase.getInstance().getReference("Card");

        editCardName = (EditText) findViewById(R.id.card_name);
        cardNumber = (EditText) findViewById(R.id.card_num);
        cvv = (EditText) findViewById(R.id.cvv);
        date = (EditText) findViewById(R.id.date);
        btnsave = (Button) findViewById(R.id.save);
        crdtype = (Spinner) findViewById(R.id.cardtype);
        //Firebase object
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabse = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabse.getReference();
        FirebaseUser user = mAuth.getCurrentUser();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                    Toast.makeText(SavingCard.this,"Successfully Signed in", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(SavingCard.this,"Successfully Signed Out", Toast.LENGTH_SHORT).show();                }
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //this method is called once the initial value and agian
                //whenver data is updated
                Object value = dataSnapshot.getValue();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cardnam = editCardName.getText().toString().trim();
                String cardNum = cardNumber.getText().toString().trim();
                String Date = date.getText().toString().trim();
                String CVV = cvv.getText().toString().trim();
                String type = crdtype.getSelectedItem().toString();

                if(TextUtils.isEmpty(cardnam)) {
                    Toast.makeText(SavingCard.this, "Enter a Name", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isDigitsOnly(cardnam) || cardNum.length() < 16 || cardNum.length() > 16)
                    Toast.makeText(SavingCard.this, "Enter Valid Card Num", Toast.LENGTH_SHORT).show();

                else {
                    FirebaseUser user = mAuth.getCurrentUser();
                    userID = user.getUid();
                    myRef.child(userID).child(type).child("Cardname").setValue(cardnam);
                    myRef.child(userID).child(type).child("CardDate").setValue(Date);
                    myRef.child(userID).child(type).child("CardNum").setValue(cardNum);
                    myRef.child(userID).child(type).child("CardType").setValue(type);
                    myRef.child(userID).child(type).child("CVV").setValue(CVV);

                    Toast.makeText(SavingCard.this, "Adding Card To Database", Toast.LENGTH_SHORT).show();

                    editCardName.setText("");
                    cardNumber.setText("");
                    date.setText("");
                    cvv.setText("");
                }


            }
        });
    }
/*
        public void addCard(){
            String cardnam = editCardName.getText().toString().trim();
            String cardNum = cardNumber.getText().toString().trim();
            String Date = date.getText().toString().trim();
            String CVV = cvv.getText().toString().trim();
            String type = cardtype.getSelectedItem().toString();

            if(TextUtils.isEmpty(cardnam)) {
                Toast.makeText(this, "Enter a Name", Toast.LENGTH_SHORT).show();
            }else if(cardNum.length() < 16)
                Toast.makeText(this, "Enter Valid Card Num", Toast.LENGTH_SHORT).show();

            else {
                String id = databaseCard.push().getKey();
                Card card = new Card(id, cardnam, cardNum, Date, CVV, type);

                databaseCard.child(id).setValue(card);

                Toast.makeText(this, "Card Added",Toast.LENGTH_SHORT).show();
            }

    }*/
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null)
            mAuth.removeAuthStateListener(mAuthListener);
    }
}
