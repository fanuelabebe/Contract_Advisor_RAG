package com.fan.androidclient.models;

public class ChatData {
    private String message;
    private boolean isSent;
    private String date;

    public ChatData() {
    }

    public ChatData(String message, boolean isSent, String date) {
        this.message = message;
        this.isSent = isSent;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
