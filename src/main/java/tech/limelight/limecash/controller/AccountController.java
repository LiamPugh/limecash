package tech.limelight.limecash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.limelight.limecash.model.Account;
import tech.limelight.limecash.repository.AccountRepository;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class AccountController {

    private static AccountRepository accountRepository = null;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/getAllAccounts")
    public List<Account> getAllAccounts(){
        return accountRepository.findAll().stream().filter(a->a.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())).collect(Collectors.toList());
    }

    public static Long getAccIdFromName(String name){
        List<Account> accounts = accountRepository.findAll();
        for(Account account : accounts){
            if(account.getName().equals(name)){
                return account.getId();
            }
        }
        throw new RuntimeException("Could not find account");
    }
}