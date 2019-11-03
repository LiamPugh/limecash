package tech.limelight.limecash.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class MonthBudget {

    private @Id
    @GeneratedValue
    Long id;

    @Column(length = 100000000)
    private String[] areas;
    @Column(length = 100000000)
    private Double[] areaAllowance;
    @Column(length = 100000000)
    private Double[] monthlySpend;
    @Column(length = 100000000)
    private Double[] remaining;
    private String owner;

    public MonthBudget() {
    }

    public MonthBudget(String[] areas, Double[] areaAllowance, Double[] monthlySpend, Double[] remaining, String owner) {
        this.areas = areas;
        this.areaAllowance = areaAllowance;
        this.monthlySpend = monthlySpend;
        this.remaining = remaining;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String[] getAreas() {
        return areas;
    }

    public void setAreas(String[] areas) {
        this.areas = areas;
    }

    public Double[] getAreaAllowance() {
        return areaAllowance;
    }

    public void setAreaAllowance(Double[] areaAllowance) {
        this.areaAllowance = areaAllowance;
    }

    public Double[] getMonthlySpend() {
        return monthlySpend;
    }

    public void setMonthlySpend(Double[] monthlySpend) {
        this.monthlySpend = monthlySpend;
    }

    public Double[] getRemaining() {
        return remaining;
    }

    public void setRemaining(Double[] remaining) {
        this.remaining = remaining;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}