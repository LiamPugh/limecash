package tech.limelight.limecash.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.limelight.limecash.model.Account;
import tech.limelight.limecash.repository.AccountRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DatabaseSaver {


    public DatabaseSaver() {
    }

    @SuppressWarnings("unchecked")
    public <T> void pushObjectToFile(JpaRepository repository, String filename){
        List<T> accountList = new ArrayList<>(repository.findAll());
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(filename), accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void pushAccountsToFile(AccountRepository accountRepository, String filename){
        List<Account> accountList = new ArrayList<>(accountRepository.findAll());
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(filename), accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
