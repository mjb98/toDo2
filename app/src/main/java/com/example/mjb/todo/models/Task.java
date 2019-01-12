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
    private String mOwnerUserName;

    public String getOwnerUserName() {
        return mOwnerUserName;
    }

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

    public void setOwnerUserName(String ownerUserName) {
        mOwnerUserName = ownerUserName;
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

    public Task(User user) {
        mDate = new Date();
        mId   = UUID.randomUUID();
        mdone = false;
        mOwnerUserName = user.getUserName();

    }
    public Task(User user,UUID id) {
        mDate = new Date();
        mId   = id;
        mdone = false;
        mOwnerUserName = user.getUserName();

    }
}
