package tech.limelight.limecash.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Income {

    private @Id
    @GeneratedValue
    Long id;

    @Column(length=100000000)
    private String[][] incomeName;
    @Column(length=100000000)
    private Double[][] incomeValue;
    private String owner;

    public Income() {
    }

    public Income(String[][] incomeName, Double[][] incomeValue, String owner) {
        this.incomeName = incomeName;
        this.incomeValue = incomeValue;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String[][] getIncomeName() {
        return incomeName;
    }

    public void setIncomeName(String[][] incomeName) {
        this.incomeName = incomeName;
    }

    public Double[][] getIncomeValue() {
        return incomeValue;
    }

    public void setIncomeValue(Double[][] incomeValue) {
        this.incomeValue = incomeValue;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
