package com.example.san.myapplication.Module;

/**
 * Created by vaps on 16-Oct-17.
 */

public class FeesDetailsPay {

    String termId;
    String termName;
    String termAmount;

    String fmgId;
    String fmgGroupName;
    String fmgAmount;
    Boolean isChecked ;

    public FeesDetailsPay() {
    }

    public FeesDetailsPay(String termId,String fmgId, String fmgGroupName, String fmgAmount,Boolean isChecked)
    {
        this.fmgId = fmgId;
        this.fmgGroupName = fmgGroupName;
        this.fmgAmount = fmgAmount;
        this.termId = termId;
        this.isChecked = isChecked;

    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getTermAmount() {
        return termAmount;
    }

    public void setTermAmount(String termAmount) {
        this.termAmount = termAmount;
    }




    public String getFmgId() {
        return fmgId;
    }

    public void setFmgId(String fmgId) {
        this.fmgId = fmgId;
    }

    public String getFmgGroupName() {
        return fmgGroupName;
    }

    public void setFmgGroupName(String fmgGroupName) {
        this.fmgGroupName = fmgGroupName;
    }

    public String getFmgAmount() {
        return fmgAmount;
    }

    public void setFmgAmount(String fmgAmount) {
        this.fmgAmount = fmgAmount;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
