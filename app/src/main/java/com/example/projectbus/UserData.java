package com.example.projectbus;

class UserData {
    private int UserID;
    private String UserName;
    private String Email;
    private String PhoneNumber;
    private String Password;
    private int Balance;

    public UserData(int userID, String userName, String email, String phoneNumber, String password, int balance) {
        UserID = userID;
        UserName = userName;
        Email = email;
        PhoneNumber = phoneNumber;
        Password = password;
        Balance = balance;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "UserID=" + UserID +
                ", UserName='" + UserName + '\'' +
                ", Email='" + Email + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", Password='" + Password + '\'' +
                ", Balance=" + Balance +
                '}';
    }

    public UserData()
    {

    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getBalance() {
        return Balance;
    }

    public void setBalance(int balance) {
        Balance = balance;
    }
}
