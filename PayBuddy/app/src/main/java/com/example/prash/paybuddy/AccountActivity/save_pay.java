package com.example.prash.paybuddy.AccountActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.prash.paybuddy.MainActivity;
import com.example.prash.paybuddy.R;
import com.example.prash.paybuddy.SavingCard;

public class save_pay extends AppCompatActivity {


   private Button add, view, pay, settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_pay);

        add = (Button)findViewById(R.id.add);
        view = (Button)findViewById(R.id.view);
        pay = (Button)findViewById(R.id.send_money);
        settings = (Button)findViewById(R.id.acc_settings);

        Toast.makeText(save_pay.this, "Successfully Signed in", Toast.LENGTH_SHORT).show();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(save_pay.this, SavingCard.class);
                startActivityForResult(intent, 0);

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(save_pay.this, card_choose.class);
                startActivityForResult(intent, 0);

            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(save_pay.this, MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setting = new Intent(save_pay.this, Account_setting.class);
                startActivityForResult(setting, 0);

            }
        });
    }
}
