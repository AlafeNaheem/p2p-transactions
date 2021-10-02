/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milestone.ict.p2ptransactions;

import com.milestone.ict.p2ptransactions.service.AccountService;
import com.milestone.ict.p2ptransactions.entity.UserAccount;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author Babajide
 */
public class P2PUserAccountServiceTest {
    private static UserAccount userA;
    private static UserAccount userB;
    public static void main(String... args){
   ApplicationContext ctx = new AnnotationConfigApplicationContext(P2pTransactionsApplication.class);
   AccountService uaccserv = ctx.getBean(AccountService.class);
     testUserAccountService(uaccserv);
    }
    /*
      Testing User Account service User creation
     */
    
   static void testUserAccountService(AccountService usaccserv) {
        UserAccount usacc = new UserAccount();
        usacc.setBalance(0d);
        usacc.setAccountName("User A");
        Date dt = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(dt);
        usacc.setAccountCreationDate(strDate);
        usacc = usaccserv.createUserAccount(usacc); 
        userA = usacc;
        System.out.format("User A added\nwith detals\n %s", usacc.toString());
        usaccserv.depositMoney(userA.getAccountNumber(), 10d);
         System.out.format("User A deposited %d dollars", 10);
        usacc = new UserAccount();
        usacc.setBalance(0d);
        usacc.setAccountName("User B");
        dt = new Date(System.currentTimeMillis());
        strDate = dateFormat.format(dt);
        usacc.setAccountCreationDate(strDate);
        usacc = usaccserv.createUserAccount(usacc);  
        userB = usacc;
        System.out.format("\nUser B added\nwith detals\n %s\n", usacc.toString());
        usaccserv.depositMoney(userB.getAccountNumber(), 20d);
        System.out.format("\nUser B deposited %d dollars\n", 20);
        usaccserv.sendMoney(userB.getAccountNumber(), userA.getAccountNumber(), 15d);
        System.out.format("\nUser B send %d dollars to User A\n", 15);
        System.out.format("\nUser A  balance is %1.2f dollars\n",usaccserv.getUserAccountBal(userA.getAccountNumber()));
        System.out.format("\nUser B  balance is %1.2f dollars\n",usaccserv.getUserAccountBal(userB.getAccountNumber()));
        usaccserv.transferMoney(userA.getAccountNumber());
        System.out.format("\nUser A  transfer %d dollars from account\n",25);
        System.out.format("\nUser A  balance is %1.2f dollars\n",usaccserv.getUserAccountBal(userA.getAccountNumber()));

    
   }
}
