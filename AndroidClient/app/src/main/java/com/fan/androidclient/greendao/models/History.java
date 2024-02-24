package com.fan.androidclient.greendao.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class History {
    @Id(autoincrement = true)
    private long Id;
    private String question;
    private String answer;
    private String reference;
    private Date date;
    public History() {
    }

    public History(String question, String answer, String reference, Date date) {
        this.question = question;
        this.answer = answer;
        this.reference = reference;
        this.date = date;
    }

    @Generated(hash = 617136593)
    public History(long Id, String question, String answer, String reference,
            Date date) {
        this.Id = Id;
        this.question = question;
        this.answer = answer;
        this.reference = reference;
        this.date = date;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
