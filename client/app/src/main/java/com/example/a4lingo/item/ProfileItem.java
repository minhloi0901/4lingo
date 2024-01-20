package com.example.a4lingo.item;

public class ProfileItem {
    private String userName;
    private String loginName;
    private String email;
    private String password;
    private String phoneNumber;
    private boolean sound;
    private boolean darkMode;
    private boolean encourageNoti;
    private boolean contact;
    private boolean facebook;
    private boolean gmail;
    private boolean smsNoti;
    private boolean emailNoti;
    private String notiTime;

    // Default constructor
    public ProfileItem() {
        // Default values for boolean fields
        this.userName = "";
        this.sound = true;
        this.darkMode = false;
        this.encourageNoti = true;
        this.contact = true;
        this.facebook = false;
        this.gmail = false;
        this.smsNoti = true;
        this.emailNoti = true;
        // Default value for notiTime can be set as needed
        this.notiTime = "12:00 PM";
    }

    // Parameterized constructor
    public ProfileItem(String userName, String loginName, String email, String password, String phoneNumber,
                       boolean sound, boolean darkMode, boolean encourageNoti, boolean contact, boolean facebook,
                       boolean gmail, boolean smsNoti, boolean emailNoti, String notiTime) {
        this.userName = userName;
        this.loginName = loginName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.sound = sound;
        this.darkMode = darkMode;
        this.encourageNoti = encourageNoti;
        this.contact = contact;
        this.facebook = facebook;
        this.gmail = gmail;
        this.smsNoti = smsNoti;
        this.emailNoti = emailNoti;
        this.notiTime = notiTime;
    }

    // Getter and setter methods for each field

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isSound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }

    public boolean isEncourageNoti() {
        return encourageNoti;
    }

    public void setEncourageNoti(boolean encourageNoti) {
        this.encourageNoti = encourageNoti;
    }

    public boolean isContact() {
        return contact;
    }

    public void setContact(boolean contact) {
        this.contact = contact;
    }

    public boolean isFacebook() {
        return facebook;
    }

    public void setFacebook(boolean facebook) {
        this.facebook = facebook;
    }

    public boolean isGmail() {
        return gmail;
    }

    public void setGmail(boolean gmail) {
        this.gmail = gmail;
    }

    public boolean isSmsNoti() {
        return smsNoti;
    }

    public void setSmsNoti(boolean smsNoti) {
        this.smsNoti = smsNoti;
    }

    public boolean isEmailNoti() {
        return emailNoti;
    }

    public void setEmailNoti(boolean emailNoti) {
        this.emailNoti = emailNoti;
    }

    public String getNotiTime() {
        return notiTime;
    }

    public void setNotiTime(String notiTime) {
        this.notiTime = notiTime;
    }
}
