/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milestone.ict.p2ptransactions.service;

import com.milestone.ict.p2ptransactions.entity.UserAccount;
import com.milestone.ict.p2ptransactions.repository.UserAccountRepo;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author Babajide
 * This class acct as services through which the p2p
 * operations are exposed to outside world
 */
@Service
public class AccountService {
//    private static final int CREDIT = 1;
//    private static final int DEBIT = 1;
 @Autowired
    UserAccountRepo usaccrep;
    
    /**
     * This method add new user account if user account id is null 
     * or update user account if user account id exists
     * @param usacc is the user account to be added or updated
     * @return the added or modified user
     */
    private UserAccount saveOrUpdateUserAccount(UserAccount usacc){        
        if(usacc.getAccountID() == null){
        Date dt = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String strDate = dateFormat.format(dt);  
        usacc.setAccountCreationDate(strDate);
        usacc.setBalance(0d);
        }
                    
        return usaccrep.save(usacc);//usrep.save(usacc);
    }
    /**
     * This method add new user account 
     * @param usacc is the user account to be added or updated
     * @return the added or modified user
     */
    public UserAccount createUserAccount(UserAccount usacc){        
        return saveOrUpdateUserAccount(usacc);
    }
     /**
     * This method existing user account 
     * @param usacc is the user account to be added or updated
     * @return the added or modified user
     */
    public UserAccount updateUserAccount(UserAccount usacc){        
        return saveOrUpdateUserAccount(usacc);
    }
    /**
     * This method get user account by id
     * @param accountNumber account number of user
     * @return the user account or null if user account does not exists
     */
    public UserAccount getUserAccount(String accountNumber){        
        Integer id = 0;
        try{
        id  = Integer.parseInt(accountNumber);        
        }
            catch(Exception e){
        }
        Optional<UserAccount> opusa = usaccrep.findById(id);
        if(opusa.isEmpty())
            return null;
        return opusa.get();
    }
    public List<UserAccount> getAllUserAccount(){
         List<UserAccount> usaccounts = new ArrayList<>();
        usaccrep.findAll().forEach(usaccount -> usaccounts.add(usaccount));
        return usaccounts;// usrep.getAllUserAccount();
    }
    /**
     * This method remove or delete existing user account
     * @param accountNumber account number of the user 
     * to be removed or deleted
     */
     public void removeUserAccount(String accountNumber){        
        Integer id = 0;
        try{
        id  = Integer.parseInt(accountNumber);
        usaccrep.deleteById(id);
        }
            catch(Exception e){
        }
    }
    /**
     * This method get balance from user account
     * @param accountNumber is account number of the user
     */
     public Double getUserAccountBal(String accountNumber){
         Integer id = 0;
        try{
        id  = Integer.parseInt(accountNumber);        
        }
            catch(Exception e){
        }
        return usaccrep.findById(id).get().getBalance();     
    }
    
     /**
     * This method update user account balance
     * @param accountNumber is the user account number 
     * @param amount amount to be credited to
     * or debited from user account
     */
     public void updateUserAccountBal(String accountNumber,Double amount){         
         UserAccount usacc = getUserAccount(accountNumber);
         usacc.setBalance(usacc.getBalance()+amount);
         saveOrUpdateUserAccount(usacc);
    }
     
     /**
     * This method update user account balance
     * @param accountNumber is the account number of user 
     * @param amt amount to be credited to
     * or debited from user account
     */
     public void depositMoney(String  accountNumber,Double amt){         
         updateUserAccountBal(accountNumber,amt);
    }
     /**
     * This method send money from user A to User B 
     * @param fromAccountNumber is the tAccount Number of user A account to be debited 
     * @param toAccountNumber is the Account Number of user B account to be credited 
     * @param amount the amount to be sent from user A account to user B account balance
     * @return true if successful, false if not successful 
     */
     public Boolean sendMoney(String fromAccountNumber,String toAccountNumber,Double amount){
         if(amount == null || amount <= 0)
             return false;
         updateUserAccountBal(toAccountNumber,amount);
         updateUserAccountBal(fromAccountNumber,-amount);         
        return true;
    }
     
      /**
     * This method transfer user money out of the app 
     * @param accountNumber is the user account number       
     * @return balance from user account to be transferred out
     */
     public Boolean transferMoney(String accountNumber){   
       Double amt = getUserAccountBal(accountNumber);
       updateUserAccountBal(accountNumber,-amt);
       return getUserAccountBal(accountNumber) == 0;
    }
     
     /**
     * This method get user account by id
     * @param accountNumber account number of user
     * @return the user account or null if user account does not exists
     */
    public String getUserAccountDetails(String accountNumber){        
        return getUserAccount(accountNumber).toString();//usrep.findUserAccount(accountNumber).toString();
    }   
}
