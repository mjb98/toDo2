package com.example.mjb.todo.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.util.UUID;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class User {
@Id(autoincrement = true)
    private Long ID;
@Unique
    private String userName;
    private String passWord;


    public User() {

    }

    @Generated(hash = 1960271181)
    public User(Long ID, String userName, String passWord) {
        this.ID = ID;
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPassWord() {
        return passWord;
    }

    public Long getID() {
        return this.ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }
}
