package tech.limelight.limecash.controller;

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
        AccountController.accountRepository = accountRepository;
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

    public void takeFromAccount(Long id, Double value){
        Account account = accountRepository.getOne(id);
        account.setHolding(account.getHolding()-value);
        accountRepository.save(account);
    }

    private void transferBetweenAccounts(Long idFrom, Long idTo, Double value){
        takeFromAccount(idFrom,value);
        takeFromAccount(idTo,-value);
    }

    public void transferBetweenAccounts(String from, String to, Double value) {
        List<Account> accounts = getAllAccounts();
        Long idFrom = accounts.stream().filter(a -> a.getName().equals(from)).collect(Collectors.toList()).get(0).getId();
        Long idTo = accounts.stream().filter(a -> a.getName().equals(to)).collect(Collectors.toList()).get(0).getId();
        transferBetweenAccounts(idFrom,idTo,value);
    }
}