package com.example.mjb.todo.models;

import android.support.annotation.IntDef;

import com.example.mjb.todo.utils.RandomData;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity
public class Task {

    private String mDescription;
    private Date mDate;
    private Boolean mdone;

    @Id
    private Long mId;
    private String mOwnerUserName;
    private Long userId;
    @ToOne(joinProperty = "userId" )
    private User mOwnerUser;
    private String photoFileAdress;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1469429066)
    private transient TaskDao myDao;
    @Generated(hash = 1059568809)
    private transient Long mOwnerUser__resolvedKey;

    public void setId(Long id) {
        mId = id;
    }

    public String getPhotoFileAdress() {
        return photoFileAdress;
    }

    public void setPhotoFileAdress(String photoFileAdress) {
        this.photoFileAdress = photoFileAdress;
    }

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

    public Long getId() {
        return mId;
    }

    public String getMDescription() {
        return this.mDescription;
    }

    public void setMDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public Date getMDate() {
        return this.mDate;
    }

    public void setMDate(Date mDate) {
        this.mDate = mDate;
    }

    public Long getMId() {
        return this.mId;
    }

    public void setMId(Long mId) {
        this.mId = mId;
    }

    public String getMOwnerUserName() {
        return this.mOwnerUserName;
    }

    public void setMOwnerUserName(String mOwnerUserName) {
        this.mOwnerUserName = mOwnerUserName;
    }

    public Task(User user) {
        mDate = new Date();
        mId   = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        mdone = false;
        mOwnerUserName = user.getUserName();

    }
    public Task(User user,Long id) {
        mDate = new Date();
        mId   = id;
        mdone = false;
        mOwnerUserName = user.getUserName();

    }

    @Generated(hash = 867458237)
    public Task(String mDescription, Date mDate, Boolean mdone, Long mId,
            String mOwnerUserName, Long userId, String photoFileAdress) {
        this.mDescription = mDescription;
        this.mDate = mDate;
        this.mdone = mdone;
        this.mId = mId;
        this.mOwnerUserName = mOwnerUserName;
        this.userId = userId;
        this.photoFileAdress = photoFileAdress;

    }

    @Generated(hash = 733837707)
    public Task() {
    }

    public String getPhotoName() {
        return "IMG_" + mId.toString() + ".jpg";
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2087444938)
    public User getMOwnerUser() {
        Long __key = this.userId;
        if (mOwnerUser__resolvedKey == null
                || !mOwnerUser__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User mOwnerUserNew = targetDao.load(__key);
            synchronized (this) {
                mOwnerUser = mOwnerUserNew;
                mOwnerUser__resolvedKey = __key;
            }
        }
        return mOwnerUser;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1402306074)
    public void setMOwnerUser(User mOwnerUser) {
        synchronized (this) {
            this.mOwnerUser = mOwnerUser;
            userId = mOwnerUser == null ? null : mOwnerUser.getID();
            mOwnerUser__resolvedKey = userId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1442741304)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTaskDao() : null;
    }



}
