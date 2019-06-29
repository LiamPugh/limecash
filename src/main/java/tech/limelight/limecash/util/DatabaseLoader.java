package tech.limelight.limecash.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.limelight.limecash.model.Account;
import tech.limelight.limecash.model.Bucket;
import tech.limelight.limecash.model.Budget;
import tech.limelight.limecash.model.Transaction;
import tech.limelight.limecash.repository.AccountRepository;
import tech.limelight.limecash.repository.BucketRepository;
import tech.limelight.limecash.repository.BudgetRepository;
import tech.limelight.limecash.repository.TransactionRepository;
import tech.limelight.limecash.security.SecurityConfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static tech.limelight.limecash.util.Constants.*;


@Configuration
@Slf4j
class DatabaseLoader {

    @Bean
    CommandLineRunner initDatabase(AccountRepository accountRepository, BucketRepository bucketRepository, BudgetRepository budgetRepository, TransactionRepository transactionRepository) {
        return args -> {
            //log.info("Preload: " + accountRepository.save(new Account("Current Account","Santander",1.0,999999.99,1500.00,3.0,300.00,2000.00,0.0,0.0)));
            log.info("Preload: " + bucketRepository.save(new Bucket("Bills",0.0)));
            log.info("Preload: " + transactionRepository.save(new Transaction("Utility Bills",58.65,1,false,false)));
            log.info("Preload: " + budgetRepository.save(new Budget()));
            log.info("Loading accounts: " + getAccountsFromFile(accountRepository));
            //log.info("Loading budgets: " + getBudgetsFromFile(budgetRepository));
            //log.info("Loading transactions: " + getTransactionsFromFile(transactionRepository));
            //log.info("Loading buckets: " + getBucketsFromFile(bucketRepository));

        };
    }

    private String getAccountsFromFile(AccountRepository accountRepository){
        ObjectMapper mapper = new ObjectMapper();
        try {
            accountRepository.saveAll(Arrays.asList(mapper.readValue(new File(ACCOUNTS_FILENAME), Account[].class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Done";
    }

    private String getBucketsFromFile(BucketRepository bucketRepository){
        ObjectMapper mapper = new ObjectMapper();
        try {
            bucketRepository.saveAll(Arrays.asList(mapper.readValue(new File(BUCKETS_FILENAME), Bucket[].class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Done";
    }

    private String getBudgetsFromFile(BudgetRepository budgetRepository){
        ObjectMapper mapper = new ObjectMapper();
        try {
            budgetRepository.saveAll(Arrays.asList(mapper.readValue(new File(BUDGETS_FILENAME), Budget[].class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Done";
    }

    private String getTransactionsFromFile(TransactionRepository transactionRepository){
        ObjectMapper mapper = new ObjectMapper();
        try {
            transactionRepository.saveAll(Arrays.asList(mapper.readValue(new File(TRANSACTIONS_FILENAME), Transaction[].class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Done";
    }

}