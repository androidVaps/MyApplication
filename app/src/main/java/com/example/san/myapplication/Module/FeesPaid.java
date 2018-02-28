package com.example.san.myapplication.Module;

/**
 * Created by vaps on 8/17/2017.
 */

public class FeesPaid
{
    int paidAmount;
    String paidDate;
    String paidMode;
    String bankName,receipt_no,dd_cheque_ref;

    public FeesPaid() {
    }

    public FeesPaid(int paidAmount, String paidDate, String paidMode) {
        this.paidAmount = paidAmount;
        this.paidDate = paidDate;
        this.paidMode = paidMode;
    }

    public FeesPaid(int paidAmount, String paidDate, String paidMode, String bankName, String receipt_no, String dd_cheque_ref) {
        this.paidAmount = paidAmount;
        this.paidDate = paidDate;
        this.paidMode = paidMode;
        this.bankName = bankName;
        this.receipt_no = receipt_no;
        this.dd_cheque_ref = dd_cheque_ref;
    }

    public int getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(int paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public String getPaidMode() {
        return paidMode;
    }

    public void setPaidMode(String paidMode) {
        this.paidMode = paidMode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getReceipt_no() {
        return receipt_no;
    }

    public void setReceipt_no(String receipt_no) {
        this.receipt_no = receipt_no;
    }

    public String getDd_cheque_ref() {
        return dd_cheque_ref;
    }

    public void setDd_cheque_ref(String dd_cheque_ref) {
        this.dd_cheque_ref = dd_cheque_ref;
    }
}
