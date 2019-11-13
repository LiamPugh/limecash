package tech.limelight.limecash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tech.limelight.limecash.model.*;
import tech.limelight.limecash.repository.TransactionRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class TransactionController {

    private TransactionRepository transactionRepository;

    @Autowired
    private AccountController accountController;

    @Autowired
    private BucketController bucketController;

    @Autowired
    private BudgetController budgetController;

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/getAllTransactions")
    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll().stream().filter(a->a.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())).collect(Collectors.toList());
    }

    @GetMapping("/getAllTransactionsForMonth/{month}")
    public List<Transaction> getTransactionsForMonth(@PathVariable String month){
        List<Transaction> transactions = transactionRepository.findAll().stream().filter(a->a.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())).collect(Collectors.toList());
        String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        ArrayList<Transaction> monthsTransactions = new ArrayList<>();
        for(Transaction transaction: transactions){
            if(!transaction.getName().equals("MASTER: Starting Position")) {
                Date date = transaction.getDate();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int monthNumber = localDate.getMonthValue();
                if (months[monthNumber-1].equals(month)) {
                    monthsTransactions.add(transaction);
                }
            }
        }
        return monthsTransactions;
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
    public void addTransaction(@RequestBody UITransaction transaction){
        Transaction trans = new Transaction(transaction);
        transactionRepository.save(trans);
        if(transaction.getIncoming()){
            bucketController.takeFromBucket(trans.getBucketImpacted(), -trans.getValue());
            accountController.takeFromAccount(trans.getAccountID(), -trans.getValue());
        }else {
            bucketController.takeFromBucket(trans.getBucketImpacted(), trans.getValue());
            accountController.takeFromAccount(trans.getAccountID(), trans.getValue());
        }
        budgetController.addTransToBudget(trans);
    }

    @PostMapping("/addTransfer")
    /**
     * TODO: Add checks for transactions here
     */
    public void addTransaction(@RequestBody UITransfer transfer){
        switch(transfer.getTransferType()){
            case "Account":
                accountController.transferBetweenAccounts(transfer.getFrom(),transfer.getTo(),transfer.getValue());
                break;
            case "Bucket":
                bucketController.transferBetweenBuckets(transfer.getFrom(),transfer.getTo(),transfer.getValue());
                break;
            case "Area":
                throw new RuntimeException("Area transfers are not currently supported");
        }
    }


    @GetMapping("/getFinishingTotal")
    public double getFinTotal() {
        return getVals("Fin");
    }

    @GetMapping("/getMonthlyCost")
    public double getMonthlyCost() {
        return getVals("monthlyCosts");
    }


    public double getVals(String valueDesired) {
        List<Budget> budgets = budgetController.getAllBudgets();
        Double start = getStartingTotal().getValue();
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue() + 6;
        if (month > 12) month -= 12;
        Double valueOfLowest = Double.MAX_VALUE;
        Double totalIncome = start;
        Double totalCosts = 0.0;
        double monthlyCosts;

        for(Budget budget : budgets){
            if(budget.getCredit()){
                Double sum = Arrays.stream(budget.getMonthlyMovement()).reduce(0.0, Double::sum);
                start += sum;
                totalIncome += sum;
            }else{
                Double sum = Arrays.stream(budget.getMonthlyMovement()).limit(month).reduce(0.0, Double::sum)
                        + Arrays.stream(budget.getAreaTotal()).filter(Objects::nonNull).skip(month).reduce(0.0, Double::sum);
                start -= sum;
                totalCosts += sum;
            }
            if(start < valueOfLowest){
                valueOfLowest = start;
            }
        }
        monthlyCosts = totalCosts / 12.0;
        switch (valueDesired){
            case "Fin":
                return start;
            default:
                return 0.0;
            case "low":
                return valueOfLowest;
            case "totalIncome":
                return totalIncome;
            case "totalCosts":
                return totalCosts;
            case "monthlyCosts":
                return monthlyCosts;
        }
    }

    @GetMapping("/getLowestPoint")
    public double getLowestPoint() {
        return getVals("low");
    }

    @GetMapping("/getIncomeTotal")
    public double getTotalIncome() {
        return getVals("totalIncome");
    }

    @GetMapping("/getCostsTotal")
    public double getTotalCosts() {
        return getVals("totalCosts");
    }


}
