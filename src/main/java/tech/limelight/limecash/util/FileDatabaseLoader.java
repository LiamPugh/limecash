package tech.limelight.limecash.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.limelight.limecash.model.*;
import tech.limelight.limecash.repository.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import static tech.limelight.limecash.util.Constants.*;
import static tech.limelight.limecash.util.CryptoUtils.deleteFiles;
import static tech.limelight.limecash.util.CryptoUtils.encryptDecryptFiles;


@SuppressWarnings("SameReturnValue")
@Configuration
@Slf4j
public class FileDatabaseLoader {

    public static String passwd;

    @Bean
    CommandLineRunner initDatabase(AccountRepository accountRepository, BucketRepository bucketRepository, BudgetRepository budgetRepository, TransactionRepository transactionRepository, UserRepository userRepository) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter File Encryption Password: ");
            FileDatabaseLoader.passwd = scanner.nextLine();
            encryptDecryptFiles(false, ENCRYPTED_FILENAMES,UNENCRYPTED_FILENAMES, passwd);
            log.info("Loading accounts: " + getAccountsFromFile(accountRepository));
            log.info("Loading budgets: " + getBudgetsFromFile(budgetRepository));
            log.info("Loading transactions: " + getTransactionsFromFile(transactionRepository));
            log.info("Loading buckets: " + getBucketsFromFile(bucketRepository));
            log.info("Loading users: " + getUserFromFile(userRepository));
            encryptDecryptFiles(true,ENCRYPTED_FILENAMES,UNENCRYPTED_FILENAMES,passwd);
            deleteFiles(UNENCRYPTED_FILENAMES);
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
            budgetRepository.saveAll(Arrays.asList(mapper.readValue(new File(BUDGETS_FILENAME), Budget[].class)));
            return "Done";
        } catch (IOException e) {
            return "FAILED";
        }
    }

    private String getTransactionsFromFile(TransactionRepository transactionRepository) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            transactionRepository.saveAll(Arrays.asList(mapper.readValue(new File(TRANSACTIONS_FILENAME), Transaction[].class)));
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