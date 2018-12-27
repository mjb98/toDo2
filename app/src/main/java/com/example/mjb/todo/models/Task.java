package com.example.mjb.todo.models;

import com.example.mjb.todo.utils.RandomData;

import java.util.Date;
import java.util.Random;
import java.util.UUID;


public class Task {

    private String mDescription;
    private Date mDate;
    private Boolean mdone;
    private UUID mId;

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Boolean getMdone() {
        return mdone;
    }

    public void setMdone(Boolean mdone) {
        this.mdone = mdone;
    }

    public UUID getId() {
        return mId;
    }

    public Task() {
        mDate = RandomData.randomDate();
        mId   = UUID.randomUUID();
        mdone = false;

    }
}
