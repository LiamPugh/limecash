package tech.limelight.limecash.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Budget {

    private @Id @GeneratedValue Long id;

    @Column(length=100000000)
    private String area;
    @Column(length=100000000)
    private Double[] areaTotal;
    @Column(length=100000000)
    private Double[] monthlyMovement;
    @Column(length=100000000)
    private Double[] remaining;

    private Boolean credit;
    private String owner;

    public Budget() {
    }

    public Budget(String area, Double[] areaTotal, Double[] monthlyMovement, Double[] remaining, Boolean credit, String owner) {
        this.area = area;
        this.areaTotal = areaTotal;
        this.monthlyMovement = monthlyMovement;
        this.remaining = remaining;
        this.credit = credit;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Double[] getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(Double[] areaTotal) {
        this.areaTotal = areaTotal;
    }

    public Double[] getMonthlyMovement() {
        return monthlyMovement;
    }

    public void setMonthlyMovement(Double[] monthlyMovement) {
        this.monthlyMovement = monthlyMovement;
    }

    public Double[] getRemaining() {
        return remaining;
    }

    public void setRemaining(Double[] remaining) {
        this.remaining = remaining;
    }

    public Boolean getCredit() {
        return credit;
    }

    public void setCredit(Boolean credit) {
        this.credit = credit;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
