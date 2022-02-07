package com.order.vo;

import java.sql.Date;


public class OrderVO {
    private Integer ordNo ;     //訂單編號
    private Integer memNo;      //會員編號
    private Integer ordStatus; //訂單狀態
    private String receiver;    //收件人姓名
    private String recPhone;   //收件人電話
    private String recZip;     //郵遞區號
    private String recAddress; //收件地址
    private Integer ordTotal;  //總金額
    private Date ordDate;      //訂單日期

    public Integer getOrdNo() {
        return ordNo;
    }

    public void setOrdNo(Integer ordNo) {
        this.ordNo = ordNo;
    }

    public Integer getMemNo() {
        return memNo;
    }

    public void setMemNo(Integer memNo) {
        this.memNo = memNo;
    }

    public Integer getOrdStatus() {
        return ordStatus;
    }

    public void setOrdStatus(Integer ordStatus) {
        this.ordStatus = ordStatus;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getRecPhone() {
        return recPhone;
    }

    public void setRecPhone(String recPhone) {
        this.recPhone = recPhone;
    }

    public String getRecZip() {
        return recZip;
    }

    public void setRecZip(String recZip) {
        this.recZip = recZip;
    }

    public String getRecAddress() {
        return recAddress;
    }

    public void setRecAddress(String recAddress) {
        this.recAddress = recAddress;
    }

    public Integer getOrdTotal() {
        return ordTotal;
    }

    public void setOrdTotal(Integer ordTotal) {
        this.ordTotal = ordTotal;
    }

    public Date getOrdDate() {
        return ordDate;
    }

    public void setOrdDate(Date ordDate) {
        this.ordDate = ordDate;
    }
}
