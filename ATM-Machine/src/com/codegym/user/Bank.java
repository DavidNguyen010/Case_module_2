package com.codegym.user;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<User> users;
    public ArrayList<Account> accounts;
    public Bank(String name){
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

   public  String getNewUserUUID(){
       String uuid;
       Random rng = new Random();
       int len = 6;
       boolean nonUnique;
       do {
           //khoi tao number id
            uuid = "";
            for (int i = 0;i<len;i++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            // kiem tra duy nhat
           nonUnique =false;
            for (User u : this.users){
                if (uuid.compareTo(u.getUUID())==0){
                    nonUnique = true;
                    break;
                }
            }
       }while (nonUnique);

       return uuid;
   }
   public  String getNewAccountUUID(){
       String uuid;
       Random rng = new Random();
       int len = 10;
       boolean nonUnique;
       do {
           //khoi tao number id
           uuid = "";
           for (int i = 0;i<len;i++){
               uuid += ((Integer)rng.nextInt(10)).toString();
           }
           // kiem tra duy nhat
           nonUnique =false;
           for (Account a : this.accounts){
               if (uuid.compareTo(a.getUUID())==0){
                   nonUnique = true;
                   break;
               }
           }
       }while (nonUnique);

       return uuid;
   };
   public void  addAccount(Account anAcct){
       this.accounts.add(anAcct);
   }

   public User addUser(String firstName,String lastName, String pin){
//      tao doi tuong User va add vao list users
       User newUser = new User(firstName,lastName,pin,this);
       this.users.add(newUser);
//       Tao tk tiet kiem cho user va add vao User va Bank
//       account list
       Account newAccount = new Account("Savings",newUser,this);
       newUser.addAccount(newAccount);
       this.accounts.add(newAccount);
       return newUser;
   }

   public User userLogin(String userID, String pin){
       for (User u : this.users){
            //check id correct
           if (u.getUUID().compareTo(userID) == 0
                   && u.validatePin(pin)){
               return u;
           }
       }
       // ko tim thay user or incorrect pin
       return null;
   }

    public String getName() {
        return this.name;
    }
}
