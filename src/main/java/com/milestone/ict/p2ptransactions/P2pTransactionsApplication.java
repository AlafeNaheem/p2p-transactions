package com.milestone.ict.p2ptransactions;

import com.milestone.ict.p2ptransactions.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class P2pTransactionsApplication implements CommandLineRunner{
private Logger logger = LoggerFactory.getLogger(this.getClass());
@Autowired
AccountService usaccserv;
	public static void main(String[] args) {
		SpringApplication.run(P2pTransactionsApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
      logger.info("All user accounts -> \n{}",usaccserv.getAllUserAccount());
    }

}
