package tech.limelight.limecash.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tech.limelight.limecash.model.Income;
import tech.limelight.limecash.repository.*;
import tech.limelight.limecash.util.FileDatabaseLoader;
import tech.limelight.limecash.util.FileDatabaseSaver;

import static tech.limelight.limecash.util.Constants.*;
import static tech.limelight.limecash.util.CryptoUtils.deleteFiles;
import static tech.limelight.limecash.util.CryptoUtils.encryptDecryptFiles;

@RestController
public class FileController {

    private final AccountRepository accountRepository;
    private final BucketRepository bucketRepository;
    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final IncomeRepository incomeRepository;


    public FileController(AccountRepository accountRepository, BucketRepository bucketRepository, BudgetRepository budgetRepository, TransactionRepository transactionRepository, UserRepository userRepository, IncomeRepository incomeRepository) {
        this.accountRepository = accountRepository;
        this.bucketRepository = bucketRepository;
        this.budgetRepository = budgetRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.incomeRepository = incomeRepository;
    }

    @GetMapping("/fileController/save")
    public void save(){
        FileDatabaseSaver fileDatabaseSaver = new FileDatabaseSaver();
        encryptDecryptFiles(false, ENCRYPTED_FILENAMES,UNENCRYPTED_FILENAMES, FileDatabaseLoader.passwd);
        fileDatabaseSaver.pushObjectToFile(accountRepository,ACCOUNTS_FILENAME);
        fileDatabaseSaver.pushObjectToFile(bucketRepository,BUCKETS_FILENAME);
        fileDatabaseSaver.pushObjectToFile(budgetRepository,BUDGETS_FILENAME);
        fileDatabaseSaver.pushObjectToFile(transactionRepository,TRANSACTIONS_FILENAME);
        fileDatabaseSaver.pushObjectToFile(transactionRepository,TRANSACTIONS_FILENAME);
        fileDatabaseSaver.pushObjectToFile(userRepository,USERS_FILENAME);
        fileDatabaseSaver.pushObjectToFile(incomeRepository,INCOMES_FILENAME);
        encryptDecryptFiles(true, ENCRYPTED_FILENAMES,UNENCRYPTED_FILENAMES, FileDatabaseLoader.passwd);
        deleteFiles(UNENCRYPTED_FILENAMES);
    }

    @GetMapping("/fileController/saveAccounts")
    public void saveAccounts(){
        FileDatabaseSaver fileDatabaseSaver = new FileDatabaseSaver();
        fileDatabaseSaver.pushObjectToFile(accountRepository,ACCOUNTS_FILENAME);
    }

    @GetMapping("/fileController/saveAccounts/{filename}")
    public void saveAccounts(@PathVariable String filename){
        FileDatabaseSaver fileDatabaseSaver = new FileDatabaseSaver();
        fileDatabaseSaver.pushObjectToFile(accountRepository,filename);
    }

}
