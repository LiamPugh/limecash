package tech.limelight.limecash.model;


import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;
import tech.limelight.limecash.repository.BudgetRepository;
import tech.limelight.limecash.repository.IncomeRepository;
import tech.limelight.limecash.repository.TransactionRepository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
public class InOutBreakdown {

    private @Id @GeneratedValue Long id;
    @Column(length=100000000)
    private String[] month;
    @Column(length=100000000)
    private Double[] inValue;
    @Column(length=100000000)
    private Double[] outValue;
    @Column(length=100000000)
    private Double[] result;
    private String owner;

    public InOutBreakdown() {
    }

    public InOutBreakdown(String[] month, Double[] inValue, Double[] outValue, Double[] result, String owner) {
        this.month = month;
        this.inValue = inValue;
        this.outValue = outValue;
        this.result = result;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public static InOutBreakdown loadInOutBreakdownFromBudgetSheets(IncomeRepository incomeRepository, BudgetRepository budgetRepository, TransactionRepository transactionRepository, String user){
        String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        Double[][] income = incomeRepository.findAll().stream().filter(a->a.getOwner().equals(user)).collect(Collectors.toList()).get(0).getIncomeValue();
        Double[] incomeTotals = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        for (int i = 0; i < 12; i++) {
            Double[] monthsIncome = income[i];
            for (Double value:monthsIncome) {
                if(value != null) incomeTotals[i]+=value;
            }
        }

        Double[][] outgoing = budgetRepository.findAll().stream().filter(a->a.getOwner().equals(user)).collect(Collectors.toList()).get(0).getAreaAllowance();
        Double[] outGoingTotals = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        for (int i = 0; i < 12; i++) {
            Double[] monthsOutgoing = outgoing[i];
            for (Double value:monthsOutgoing) {
                if(value!=null) outGoingTotals[i]+=value;
            }
        }

        Double[] results = new Double[12];



        Double startingBalance = 0.0;
        List<Transaction> transactions = transactionRepository.findAll();
        for (Transaction trans: transactions) {
            if(trans.getName().equals("MASTER: Starting Position") && (trans.getAccountID() == 1 || trans.getAccountID() == 4)){
                startingBalance+=trans.getValue();
            }
        }
        results[6] = startingBalance-outGoingTotals[6]+incomeTotals[6];
        for (int i = 7; i < 12; i++) {
            results[i] = results[i-1]+incomeTotals[i]-outGoingTotals[i];
        }
        results[0] = results[11]-outGoingTotals[0]+incomeTotals[0];
        for (int i = 1; i < 6; i++) {
            results[i] = results[i-1]+incomeTotals[i]-outGoingTotals[i];
        }

        return new InOutBreakdown(months,incomeTotals,outGoingTotals,results, user);
    }
}
