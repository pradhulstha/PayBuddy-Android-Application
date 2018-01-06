package com.example.prash.paybuddy.AccountActivity;

/**
 * Created by prash on 12/7/2017.
 */

public class UserInformation {

    private String Cardname;
    private String CardNum;
    private String CardType;
    private String CardDate;
    private String CVV;

    public UserInformation(){

    }

    public String getCardname() {
        return Cardname;
    }

    public void setCardname(String cardname) {
        this.Cardname = cardname;
    }

    public String getCardNum() {
        return CardNum;
    }

    public void setCardNum(String cardNum) {
        this.CardNum = cardNum;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        this.CardType = cardType;
    }

    public String getCardDate() {
        return CardDate;
    }

    public void setCardDate(String cardDate) {
        this.CardDate = cardDate;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }
}
