package com.example.prash.paybuddy;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by prash on 12/5/2017.
 */

public class Card{

     String cardid;
    String cardname;
    String cardnum;
     String date;
     String cvv;
     String cardtype;

    public Card(){

    }

    Card(String cardid, String cardname, String cardnum, String date, String cvv, String cardtype){
        this.cardid = cardid;
        this.cardname = cardname;
        this.cardnum = cardnum;
        this.date = date;
        this.cvv = cvv;
        this.cardtype = cardtype;
    }
    public String getCardid() {
        return cardid;
    }
    public String getCardname() {
        return cardname;
    }

    public String getCardnum() {
        return cardnum;
    }

    public String getDate() {
        return date;
    }

    public String getCvv() {
        return cvv;
    }

    public String getCardtype() {
        return cardtype;
    }




}
