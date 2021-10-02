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
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.junit.platform.commons.logging.Logger;
//import org.junit.platform.commons.logging.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class P2pTransactionsApplicationTests {

    @Autowired
    AccountService usaccserv;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /*
     * UserA and UserB will be used to test the p2p transactions
     */
    private static UserAccount userA;
    private static UserAccount userB;

    @Test
    void contextLoads() {
        createUserAccount();
        depositMoney();
        sendMoney();
        transferMoney();
    }

    /*
      Testing User Account service User creation
     */
    
    void createUserAccount() {
        UserAccount usacc = new UserAccount();
        usacc.setBalance(0d);
        usacc.setAccountName("User A");
        Date dt = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(dt);
        usacc.setAccountCreationDate(strDate);
        usacc = usaccserv.createUserAccount(usacc);
        assertNotNull(usacc);
        logger.info("User A created->\n{}", usacc.toString());
        userA = usacc;
        usacc = new UserAccount();
        usacc.setBalance(0d);
        usacc.setAccountName("User B");
        dt = new Date(System.currentTimeMillis());
        strDate = dateFormat.format(dt);
        usacc.setAccountCreationDate(strDate);
        usacc = usaccserv.createUserAccount(usacc);
        assertNotNull(usacc);
        logger.info("User B created->\n{}", usacc.toString());
        userB = usacc;
    }
    /*
       Testing User Account service money deposit
     */
    
    void depositMoney() {
        usaccserv.depositMoney(userA.getAccountNumber(), 10d);
        logger.info("User A deposit ->\n{}", 10);
        assertEquals(usaccserv.getUserAccountBal(userA.getAccountNumber()),10);
        logger.info("User A account balance->\n{}", usaccserv.getUserAccountBal(userA.getAccountNumber()));
        usaccserv.depositMoney(userB.getAccountNumber(), 20d);
        logger.info("User B deposit ->\n{}", 20);
        assertEquals(usaccserv.getUserAccountBal(userB.getAccountNumber()) , 20);
        logger.info("User B account balance->\n{}", usaccserv.getUserAccountBal(userB.getAccountNumber()));
    }
    
    /*
       Testing User Account service money sending
     */
    
    void sendMoney(){
        assertTrue(usaccserv.sendMoney(userB.getAccountNumber(), userA.getAccountNumber(), 15d));
        assertEquals(usaccserv.getUserAccountBal(userA.getAccountNumber()), 25);
        logger.info("User A account balance->\n{}", usaccserv.getUserAccountBal(userA.getAccountNumber()));
        assertEquals(usaccserv.getUserAccountBal(userB.getAccountNumber()), 5);
        logger.info("User B account balance->\n{}", usaccserv.getUserAccountBal(userB.getAccountNumber()));
     
    }
    
     /*
       Testing User Account service money transfer
     */
    
    void transferMoney(){
        assertTrue(usaccserv.transferMoney(userA.getAccountNumber()));
        assertEquals(usaccserv.getUserAccountBal(userA.getAccountNumber()), 0);
        logger.info("User A account balance->\n{}", usaccserv.getUserAccountBal(userA.getAccountNumber()));
        
    }

}
