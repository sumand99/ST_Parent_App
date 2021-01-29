package com.swipetouch.database;

import android.database.sqlite.SQLiteDatabase;


/**
 * Created by CRUD-PC on 10/5/2016.
 */
public class UserModel {
    public static final String TABLE_NAME = "UserModelCombio";
    public static final String KEY_ID = "_id";
    public static final String KEY_USER_ID = "user_id";
    public static final String KeyUserToken = "userToken";
    public static final String KeyuserCompanyId = "userCompanyId";
    public static final String KeyUserRole= "userRole";
    public static final String KeyprofilePic= "profilePic";
    public static final String KeyName= "name";
    public static final String KeyEmail= "email";


    public static void creteTable(SQLiteDatabase db) {
        String CREATE_CLIENTTABLE = "create table " + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_USER_ID + " text," +
                KeyUserToken + " text,"+
                KeyuserCompanyId + " text,"+
                KeyprofilePic + " text,"+

                KeyName + " text,"+
                KeyEmail + " text,"+
                KeyUserRole + " text " +
                ")";
        db.execSQL(CREATE_CLIENTTABLE);
    }

    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }


    String userId;
    String userToken;
    String userRole;
    String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    String userEmail;

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    String profilePic;

    public String getUserCompanyId() {
        return userCompanyId;
    }

    public void setUserCompanyId(String userCompanyId) {
        this.userCompanyId = userCompanyId;
    }

    String userCompanyId;

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
