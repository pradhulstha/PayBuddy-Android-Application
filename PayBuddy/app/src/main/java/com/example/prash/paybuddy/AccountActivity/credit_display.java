package com.example.prash.paybuddy.AccountActivity;



import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prash.paybuddy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class credit_display extends AppCompatActivity {

    private static final String TAG = "Display";

    //adding Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabse;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        mListView = (ListView)findViewById(R.id.view2);

        //Firebase object
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabse = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabse.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    // toastMessage("Successfully Signed in");
                } else {
                    Log.d(TAG, "onAuthStateChanged:Signed_out:");
                }
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //this method is called once the initial value and agian
                //whenver data is updated
                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds: dataSnapshot.getChildren()) {
            UserInformation uInfo = new UserInformation();


            if (ds.hasChild("Credit Card")) {
                uInfo.setCardType(ds.child("Credit Card").getValue(UserInformation.class).getCardType());
                uInfo.setCardname(ds.child("Credit Card").getValue(UserInformation.class).getCardname());
                uInfo.setCardNum(ds.child("Credit Card").getValue(UserInformation.class).getCardNum());
                uInfo.setCVV(ds.child("Credit Card").getValue(UserInformation.class).getCVV());
                uInfo.setCardDate(ds.child("Credit Card").getValue(UserInformation.class).getCardDate());

                ArrayList<String> array = new ArrayList<>();
                array.add(uInfo.getCardType());
                array.add(uInfo.getCardname());
                array.add(uInfo.getCardNum());
                array.add(uInfo.getCardDate());
                array.add(uInfo.getCVV());

                ArrayAdapter adapter = new ArrayAdapter<>(credit_display.this, android.R.layout.simple_list_item_1, array);
                mListView.setAdapter(adapter);
            } else
                Toast.makeText(credit_display.this, "No Credit Card Added", Toast.LENGTH_LONG).show();


        }

    }

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
