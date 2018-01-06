package com.example.prash.paybuddy.AccountActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.prash.paybuddy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class card_choose extends AppCompatActivity implements View.OnClickListener {

    private Button gift_card, credit_card, debit_card;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_choose);

        credit_card = (Button)findViewById(R.id.credit_card);
        debit_card = (Button)findViewById(R.id.debit_card);
        gift_card = (Button)findViewById(R.id.gift_card);


        credit_card.setOnClickListener(this);
        debit_card.setOnClickListener(this);
        gift_card.setOnClickListener(this);






    }

    @Override
    public void onClick(View view) {

        if(view == credit_card) {

            startActivity(new Intent(card_choose.this, credit_display.class));
        }
        else if(view == debit_card)
            startActivity(new Intent(card_choose.this, display.class));
        else
            startActivity(new Intent(card_choose.this, gift_card_display.class));

    }

}
