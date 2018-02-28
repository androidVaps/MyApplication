package com.example.san.myapplication.Module;

/**
 * Created by vaps on 14-Oct-17.
 */

public class FeesTerm
{
    String termName;
    String termFee;

    public FeesTerm(String termName, String termFee) {
        this.termName = termName;
        this.termFee = termFee;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getTermFee() {
        return termFee;
    }

    public void setTermFee(String termFee) {
        this.termFee = termFee;
    }
}
