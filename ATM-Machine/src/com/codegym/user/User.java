package com.codegym.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User  {
    private String firstName;
    private String lastName;
    private String uuid;
    private byte pinHash[];
    private ArrayList<Account> accounts;

    public User(){

    }

    public User(String firstName, String lastName, String pin, Bank theBank){
        this.firstName = firstName;
        this.lastName = lastName;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        }catch (NoSuchAlgorithmException e){
            System.err.println("error, catch NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        this.uuid  = theBank.getNewUserUUID();

        //creatte emty list  of account
        this.accounts = new ArrayList<Account>();

        System.out.printf("New user %s %s with ID %s create. \n"
                ,lastName,firstName,this.uuid);
    }
    public void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }


    public String getUUID() {
        return this.uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean validatePin(String aPin){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()),this.pinHash);
        }catch (NoSuchAlgorithmException e) {
            System.err.println("error, catch NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }
    public void printAccountSummary(){
        System.out.printf("%s",this.firstName);
        for (int i = 0; i < this.accounts.size();i++){
            System.out.printf("%d) %s\n",(i+1),this.accounts.get(i).getSummaryLine());
        }
    }
    public int numAccount(){
        return this.accounts.size();
    }
    public void printAcctTransHistory(int acctIdx){
        this.accounts.get(acctIdx).printTranHistory();
    }

    public double getAcctBalance(int acctIdx){
        return this.accounts.get(acctIdx).getBalance();
    }
    public String getAcctUUID(int acctIdx){
        return this.accounts.get(acctIdx).getUUID();
    }
    public void addAcctTransaction(int acctIdx,double amount, String memo){
        this.accounts.get(acctIdx).addTransaction(amount,memo);
    }
}
