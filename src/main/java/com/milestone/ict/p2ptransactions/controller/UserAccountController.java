/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milestone.ict.p2ptransactions.controller;

import com.milestone.ict.p2ptransactions.service.AccountService;
import com.milestone.ict.p2ptransactions.entity.SenderModel;
import com.milestone.ict.p2ptransactions.entity.UserAccount;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Babajide
 */
@RestController
public class UserAccountController {
    @Autowired
    AccountService accserv;
    
    @PostMapping(path="/createuseraccount")   
    public UserAccount createUserAccount(@RequestBody UserAccount usacc){
      return accserv.createUserAccount(usacc);       
    }
    
    @GetMapping(path="/getuseraccount/{accountNumber}")   
    public UserAccount getUserAccount(@PathVariable String accountNumber){
      UserAccount usac = accserv.getUserAccount(accountNumber);
      if(usac == null){
          usac = new UserAccount();
      usac.setAccountID(0);
      usac.setAccountCreationDate("");
      usac.setAccountName("");
      usac.setBalance(0d);
      }
      return usac;       
    }

    @GetMapping(path="/getalluseraccount")   
    public List<UserAccount> getAllUserAccount(){
      List<UserAccount> usacl = accserv.getAllUserAccount();
      if(usacl.size() < 1){
      usacl = new ArrayList<UserAccount>(); //UserAccount();
      UserAccount usac = new UserAccount();  
      usac.setAccountID(0);
      usac.setAccountCreationDate("");
      usac.setAccountName("");
      usac.setBalance(0d);
      usacl.add(usac);
      }
      return usacl;       
    }
    
    @GetMapping(path="/deluseraccount/{accountNumber}")   
    public String deleteUserAccount(@PathVariable String accountNumber){
        if(accserv.getUserAccount(accountNumber) != null){
      accserv.removeUserAccount(accountNumber);
      return "user account delete successful";
        }
        else
      return "Error deleting user account";       
    }
 
    @GetMapping(path="/getuseraccountbal/{accountNumber}")   
    public String getUserAccountBal(@PathVariable String accountNumber){
        if(accserv.getUserAccount(accountNumber) != null){
     return "" + accserv.getUserAccountBal(accountNumber);      
        }
        else
      return "Error: unknown user";       
    }    
    
    @PostMapping(path="/userdeposit")
    public String depositMoney(@RequestBody UserAccount usacc){
      UserAccount uac = accserv.getUserAccount(usacc.getAccountNumber());
      if(uac != null){    
      uac.setBalance(uac.getBalance()+usacc.getBalance());              
         accserv.depositMoney(uac.getAccountNumber(), uac.getBalance());
        return "user deposit successful";                 }
      return "user deposit failed";      
    }
    
    @PostMapping(path="/sendmoney")
    public String sendMoney(@RequestBody SenderModel sModel){
      UserAccount uac = accserv.getUserAccount(sModel.senderAccountNumber);
      if(uac != null){                
         accserv.sendMoney(sModel.senderAccountNumber,sModel.receiverAccountNumber, sModel.amount);
        return "user deposit successful";                 }
      return "user deposit failed";      
    }
   
    @GetMapping(path="/transfermoney/{accountNumber}")   
    public String transferMoney(@PathVariable String accountNumber){
     
        if(accserv.getUserAccount(accountNumber) != null){
           if(accserv.transferMoney(accountNumber))
     return "Money transfer successful";
           else
      return "Money transfer failed";
        }
        else
      return "Error: unknown user";       
    } 
}
