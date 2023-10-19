package com.codegym.user;
import java.util.Scanner;
public class ATM {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Bank theBank  = new Bank("Ngan hang Vietcom");
        //add user
        User aUser = theBank.addUser("Teo","Van","1234");

        Account newAccount = new Account("Saving",aUser,theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true){
            //stay in the login promt utill successful login
            curUser = ATM.mainMenuPrompt(theBank, scanner);

            //Stay in menu utill user quit
            ATM.printUserMenu(curUser, scanner);
        }
    }

    public static User mainMenuPrompt(Bank theBank,Scanner scanner){
        String userID;
        String pin;
        User authUser;
        do {
            System.out.printf("\n\n %s \n\n ",theBank.getName());
            System.out.print("Enter user ID: ");
            userID = scanner.nextLine();
            System.out.print("Enter pin: ");
            pin = scanner.nextLine();
            authUser = theBank.userLogin(userID,pin);
            if (authUser == null){
                System.out.println("Incorect ID/pin. Try again!!!!");
            }
        }while (authUser == null);
        return authUser;
    }
    public static void printUserMenu(User theUser,Scanner scanner){
        theUser.printAccountSummary();

        int choice;
        do {
            System.out.printf("%s,%s ",theUser.getLastName(),theUser.getFirstName());
            System.out.printf("--------------------------------------\n");
            System.out.println("1: Show account transaction history");
            System.out.println("2: Withdraw");
            System.out.println("3: Deposit");
            System.out.println("4: Transfer");
            System.out.println("5: Cook");
            System.out.print("Enter ur choice");
            choice = scanner.nextInt();

            if (choice<1 || choice >5){
                System.out.println("invalid choice");
            }
        }while (choice <1 || choice > 5);

        switch (choice){
            case 1 :
                ATM.showTransHistory(theUser, scanner);
                break;
            case 2 :
                ATM.withdrawFunds(theUser,scanner);
                break;
            case 3 :
                ATM.depositFunds(theUser,scanner);
                break;
            case 4 :
                ATM.transferFunds(theUser,scanner);
                break;
        }

        //stay
        if (choice !=5){
            ATM.printUserMenu(theUser,scanner);
        }
    }
    public static void showTransHistory(User theUser,Scanner scanner){
        int theAcct;
        do {
            System.out.printf("Enter the number (1-%d) of the account \n"+
                    "transactions want to see : " +
                    "",theUser.numAccount());
            theAcct = scanner.nextInt()-1;
            if (theAcct < 0 || theAcct >= theUser.numAccount()){
                System.out.println("invalid account");
            }
        }while (theAcct < 0 || theAcct >= theUser.numAccount());

        theUser.printAcctTransHistory(theAcct);
    }
    public static void transferFunds(User theUser,Scanner scanner){
        int fromAcct;
        int toAcct;
        double amount;
        double acctBalance;
//        chuyen tu account
        do {
            System.out.printf("Enter the number (1-%d) of the account\n"
                    + "to transfer from:",theUser.numAccount());
            fromAcct =scanner.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccount()){
                System.out.println("invalid account");
            }
        }while (fromAcct < 0 || fromAcct >= theUser.numAccount());
        acctBalance = theUser.getAcctBalance(fromAcct);
//        chuyen den account

        do {
            System.out.printf("Enter the number (1-%d) of the account\n"
                    + "from transfer to:",theUser.numAccount());
            toAcct =scanner.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccount()){
                System.out.println("invalid account");
            }
        }while (toAcct < 0 || toAcct >= theUser.numAccount());

        //kiem tra amount to transfer

        do {
            System.out.printf("Enter amount to transfer (max $%.02f): $",acctBalance );
            amount = scanner.nextDouble();
            if (amount < 0){
                System.out.printf("greater 0");
            }else {
                System.out.printf("Not greater than" + "balance of $%.02f. \n",acctBalance);
            }
        }while (amount < 0 || amount > acctBalance);
        //chuyen tien

        theUser.addAcctTransaction(fromAcct,-1*amount,
                String.format("transfer to account %s",theUser.getAcctUUID(fromAcct)));
        theUser.addAcctTransaction(toAcct,amount,
                String.format("transfer to account %s",theUser.getAcctUUID(toAcct)));
    };
    public static void withdrawFunds(User theUser,Scanner scanner){
        int fromAcct;
        int toAcct;
        double amount;
        double acctBalance;
        String memo;
//        chuyen tu account
        do {
            System.out.printf("Enter the number (1-%d) of the account\n"
                    + "to withdraw:",theUser.numAccount());
            fromAcct =scanner.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccount()){
                System.out.println("invalid account");
            }
        }while (fromAcct < 0 || fromAcct >= theUser.numAccount());
        acctBalance = theUser.getAcctBalance(fromAcct);

        do {
            System.out.printf("Enter amount to transfer (max $%.02f): $",acctBalance );
            amount = scanner.nextDouble();
            if (amount < 0){
                System.out.printf("greater 0");
            }else {
                System.out.printf("Not greater than" + "balance of $%.02f. \n",acctBalance);
            }
        }while (amount < 0 || amount > acctBalance);
        scanner.nextLine();

        System.out.println("Enter a memo");
        memo = scanner.nextLine();

        theUser.addAcctTransaction(fromAcct,-1*amount,memo);
    };
    public static void depositFunds(User theUser,Scanner scanner){
        int toAcct;
        double amount;
        double acctBalance;
        String memo;
//        chuyen tu account
        do {
            System.out.printf("Enter the number (1-%d) of the account\n"
                    + "to deposit: ",theUser.numAccount());
            toAcct =scanner.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccount()){
                System.out.println("invalid account");
            }
        }while (toAcct < 0 || toAcct >= theUser.numAccount());
        acctBalance = theUser.getAcctBalance(toAcct);

        do {
            System.out.printf("Enter amount to transfer (max $%.02f): $",acctBalance );
            amount = scanner.nextDouble();
            if (amount < 0){
                System.out.printf("greater 0");
            }

        }while (amount < 0);
        scanner.nextLine();

        System.out.println("Enter a memo");
        memo = scanner.nextLine();

        theUser.addAcctTransaction(toAcct,amount,memo);
    };



}
