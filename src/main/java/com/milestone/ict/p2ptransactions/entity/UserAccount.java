/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milestone.ict.p2ptransactions.entity;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Babajide
 */
@Entity
@Table(name="useraccount")
public class UserAccount implements Serializable {
    @Id
    @GeneratedValue
    private Integer accountID;  
   
    private String accountName;
    
    private String accountCreationDate;
    
    private Double balance;
    
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    
    public UserAccount(){        
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer AccountID) {
        this.accountID = AccountID;
    }

    public String getAccountNumber() {
        return String.format("%06d", accountID);
    } 

    public String getAccountCreationDate() {
        return accountCreationDate;
    }

    public void setAccountCreationDate(String accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
    
    public String toString(){
        return String.format("Account number:%06d\nAccount name:%s\n"
                + "Date created:%s\nAccount balance:%1.2f", accountID,
                accountName,accountCreationDate,balance);
    }
}
