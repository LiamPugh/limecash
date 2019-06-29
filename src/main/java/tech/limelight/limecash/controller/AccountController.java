package tech.limelight.limecash.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.limelight.limecash.model.Account;
import tech.limelight.limecash.repository.AccountRepository;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class AccountController {

    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/getAllAccounts")
    public List<Account> getAllAccounts(){
        return accountRepository.findAll().stream().collect(Collectors.toList());
    }





}
