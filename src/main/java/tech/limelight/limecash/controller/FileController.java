package tech.limelight.limecash.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tech.limelight.limecash.repository.AccountRepository;
import tech.limelight.limecash.repository.BucketRepository;
import tech.limelight.limecash.repository.BudgetRepository;
import tech.limelight.limecash.repository.TransactionRepository;
import tech.limelight.limecash.util.DatabaseSaver;

import static tech.limelight.limecash.util.Constants.*;

@RestController
public class FileController {

    private final AccountRepository accountRepository;
    private final BucketRepository bucketRepository;
    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;


    public FileController(AccountRepository accountRepository, BucketRepository bucketRepository, BudgetRepository budgetRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.bucketRepository = bucketRepository;
        this.budgetRepository = budgetRepository;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/fileController/save")
    public void save(){
        DatabaseSaver databaseSaver = new DatabaseSaver();
        databaseSaver.pushObjectToFile(accountRepository,ACCOUNTS_FILENAME);
        databaseSaver.pushObjectToFile(bucketRepository,BUCKETS_FILENAME);
        databaseSaver.pushObjectToFile(budgetRepository,BUDGETS_FILENAME);
        databaseSaver.pushObjectToFile(transactionRepository,TRANSACTIONS_FILENAME);
    }

    @GetMapping("/fileController/saveAccounts")
    public void saveAccounts(){
        DatabaseSaver databaseSaver = new DatabaseSaver();
        databaseSaver.pushObjectToFile(accountRepository,ACCOUNTS_FILENAME);
    }

    @GetMapping("/fileController/saveAccounts/{filename}")
    public void saveAccounts(@PathVariable String filename){
        DatabaseSaver databaseSaver = new DatabaseSaver();
        databaseSaver.pushObjectToFile(accountRepository,filename);
    }

}
