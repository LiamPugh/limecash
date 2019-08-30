package tech.limelight.limecash.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.limelight.limecash.model.Transaction;
import tech.limelight.limecash.model.UITransaction;
import tech.limelight.limecash.repository.TransactionRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TransactionController {

    private TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/getAllTransactions")
    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll().stream().filter(a->a.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())).collect(Collectors.toList());
    }

    @GetMapping("/getStartingTotal")
    public Transaction getStartingTotal(){
        List<Transaction> transactions = transactionRepository.findAll().stream().filter(a->a.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())).filter(a->a.getName().contains("Starting Position")).collect(Collectors.toList());
        return transactions.get(0);
    }

    @PostMapping("/addTransaction")
    /**
     * TODO: Add checks for transactions here
     */
    public String addTransaction(@RequestBody UITransaction transaction){
        transactionRepository.save(new Transaction(transaction));
        return "";
    }
}
