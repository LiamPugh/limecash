package tech.limelight.limecash.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Budget {

    private @Id @GeneratedValue Long id;
    private String[] area;
    private Double[] areaAllowance;
    private Double[][] monthlySpend;
    private Double[][] remaining;

    public Budget() {
    }

    public Budget(String[] area, Double[] areaAllowance, Double[][] monthlySpend, Double[][] remaining) {
        this.area = area;
        this.areaAllowance = areaAllowance;
        this.monthlySpend = monthlySpend;
        this.remaining = remaining;
    }

    public String[] getArea() {
        return area;
    }

    public void setArea(String[] area) {
        this.area = area;
    }

    public Double[] getAreaAllowance() {
        return areaAllowance;
    }

    public void setAreaAllowance(Double[] areaAllowance) {
        this.areaAllowance = areaAllowance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double[][] getMonthlySpend() {
        return monthlySpend;
    }

    public void setMonthlySpend(Double[][] monthlySpend) {
        this.monthlySpend = monthlySpend;
    }

    public Double[][] getRemaining() {
        return remaining;
    }

    public void setRemaining(Double[][] remaining) {
        this.remaining = remaining;
    }
}
