package tech.limelight.limecash.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tech.limelight.limecash.model.Account;
import tech.limelight.limecash.model.Transaction;
import tech.limelight.limecash.repository.AccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @GetMapping("/addNewAccount")
    public void addNewAccount(){
        Account account = new Account();
        account.setOwner("liampugh@limelight.tech");
        accountRepository.save(account);
        accountRepository.flush();
    }

    @GetMapping("/deleteAccount/{id}")
    public void deleteTransaction(@PathVariable Long id){
        Optional<Account> account = accountRepository.findById(id);
        if(account.isPresent() && account.get().getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())) accountRepository.deleteById(id);
    }

    @PostMapping("/saveAllAccounts")
    public void saveAccounts(@RequestBody List<Account> accounts){
        for (Account account:accounts) {
            account.setOwner(SecurityContextHolder.getContext().getAuthentication().getName());
            accountRepository.save(account);
        }
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

    @PostMapping("/monthClose/accDifferences")
    public List<Double> getAccDifferences(@RequestBody List<Account> accounts){
        ArrayList<Double> differences = new ArrayList<>();
        for(Account account : accounts){
            differences.add(account.getHolding() - accountRepository.getOne(account.getId()).getHolding());
        }
        return differences;
    }




}