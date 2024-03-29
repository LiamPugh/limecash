package tech.limelight.limecash.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Account {

    private @Id @GeneratedValue Long id;
    private String name;
    private String provider;
    private Double holding;
    private Double minimumBalance;
    private Double maximumBalance;
    private Double overdraftAmount;
    private Double interestRate;
    private Double interestMinimumBalance;
    private Double interestMaximumBalance;
    private Double depositLimit;
    private Double withdrawalLimit;
    private String owner;

    public Account() {
    }

    public Account(String name, String provider, Double holding, Double minimumBalance, Double maximumBalance, Double overdraftAmount, Double interestRate, Double interestMinimumBalance, Double interestMaximumBalance, Double depositLimit, Double withdrawalLimit, String owner) {
        this.name = name;
        this.provider = provider;
        this.holding = holding;
        this.minimumBalance = minimumBalance;
        this.maximumBalance = maximumBalance;
        this.overdraftAmount = overdraftAmount;
        this.interestRate = interestRate;
        this.interestMinimumBalance = interestMinimumBalance;
        this.interestMaximumBalance = interestMaximumBalance;
        this.depositLimit = depositLimit;
        this.withdrawalLimit = withdrawalLimit;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Double getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Double minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public Double getMaximumBalance() {
        return maximumBalance;
    }

    public void setMaximumBalance(Double maximumBalance) {
        this.maximumBalance = maximumBalance;
    }

    public Double getOverdraftAmount() {
        return overdraftAmount;
    }

    public void setOverdraftAmount(Double overdraftAmount) {
        this.overdraftAmount = overdraftAmount;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getInterestMinimumBalance() {
        return interestMinimumBalance;
    }

    public void setInterestMinimumBalance(Double interestMinimumBalance) {
        this.interestMinimumBalance = interestMinimumBalance;
    }

    public Double getInterestMaximumBalance() {
        return interestMaximumBalance;
    }

    public void setInterestMaximumBalance(Double interestMaximumBalance) {
        this.interestMaximumBalance = interestMaximumBalance;
    }

    public Double getDepositLimit() {
        return depositLimit;
    }

    public void setDepositLimit(Double depositLimit) {
        this.depositLimit = depositLimit;
    }

    public Double getWithdrawalLimit() {
        return withdrawalLimit;
    }

    public void setWithdrawalLimit(Double withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Double getHolding() {
        return holding;
    }

    public void setHolding(Double holding) {
        this.holding = holding;
    }
}
