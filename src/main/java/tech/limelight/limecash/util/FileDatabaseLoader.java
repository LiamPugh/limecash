package tech.limelight.limecash.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.limelight.limecash.model.*;
import tech.limelight.limecash.repository.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import static tech.limelight.limecash.util.Constants.*;


@SuppressWarnings("SameReturnValue")
@Configuration
@Slf4j
public class FileDatabaseLoader {

    public static String passwd;

    private Logger log = LoggerFactory.getLogger(FileDatabaseLoader.class);

    @Bean
    CommandLineRunner initDatabase(AccountRepository accountRepository, BucketRepository bucketRepository, BudgetRepository budgetRepository, TransactionRepository transactionRepository, UserRepository userRepository) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Username: ");
            String user = "liampugh@limelight.tech";//scanner.nextLine();
            System.out.print("Enter File Encryption Password: ");
            FileDatabaseLoader.passwd = "SpringPassword01";//scanner.nextLine();
            //encryptDecryptFiles(false, ENCRYPTED_FILENAMES,UNENCRYPTED_FILENAMES, passwd);
            log.info("Loading transactions: " + getTransactionsFromFile(transactionRepository));
            log.info("Loading accounts: " + getAccountsFromFile(accountRepository));
            log.info("Loading budgets: " + getBudgetsFromFile(budgetRepository));
            log.info("Loading buckets: " + getBucketsFromFile(bucketRepository));
            log.info("Loading users: " + getUserFromFile(userRepository));
            Double[] total = {1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, 1.10, 1.11, 1.12};
            Double[] lows = {-1.1,-1.1,-1.1,-1.1,-1.1,-1.1,-1.1,-1.1,-1.1,-1.1,-1.1,-1.1};
            Double[] zero = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
            budgetRepository.save(new Budget("Income Test",total,lows,zero,true,"liampugh@limelight.tech",2019L));
            //encryptDecryptFiles(true,ENCRYPTED_FILENAMES,UNENCRYPTED_FILENAMES,passwd);
            //deleteFiles(UNENCRYPTED_FILENAMES);
        };
    }

    private String getAccountsFromFile(AccountRepository accountRepository) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            accountRepository.saveAll(Arrays.asList(mapper.readValue(new File(ACCOUNTS_FILENAME), Account[].class)));
            return "Done";
        } catch (IOException e) {
            return "FAILED";
        }
    }

    private String getBucketsFromFile(BucketRepository bucketRepository) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            bucketRepository.saveAll(Arrays.asList(mapper.readValue(new File(BUCKETS_FILENAME), Bucket[].class)));
            return "Done";
        } catch (IOException e) {
            return "FAILED";
        }
    }

    private String getBudgetsFromFile(BudgetRepository budgetRepository) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            budgetRepository.saveAll(Budget2.budget2ListConverter(mapper.readValue(new File(BUDGETS_FILENAME), Budget2[].class)));
            return "Done";
        } catch (IOException e) {
            return "FAILED";
        }
    }

    private String getTransactionsFromFile(TransactionRepository transactionRepository) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ArrayList<Transaction> transactions = new ArrayList<>(Arrays.asList(mapper.readValue(new File(TRANSACTIONS_FILENAME), Transaction[].class)));
            for(Transaction transaction : transactions){
                transaction.setDate(new Date(1560704113000L));
            }
            transactionRepository.saveAll(transactions);
            return "Done";
        } catch (IOException e) {
            return "FAILED";
        }
    }

    private String getUserFromFile(UserRepository userRepository) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            userRepository.saveAll(Arrays.asList(mapper.readValue(new File(USERS_FILENAME), User[].class)));
            return "Done";
        } catch (IOException e) {
            return "FAILED";
        }
    }

}