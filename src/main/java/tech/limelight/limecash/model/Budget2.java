package tech.limelight.limecash.model;


import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Budget2 {

    private @Id
    @GeneratedValue
    Long id;

    @Column(length = 100000000)
    private String[][] areas;
    @Column(length = 100000000)
    private Double[][] areaAllowance;
    @Column(length = 100000000)
    private Double[][] monthlySpend;
    @Column(length = 100000000)
    private Double[][] remaining;
    private String owner;

    public Budget2() {
    }


    public static List<Budget> budget2ListConverter(Budget2[] budget2) {
        ArrayList<Budget> budgetList = new ArrayList<>();
        for (Budget2 budget21 : budget2){
            budgetList.addAll(budget2Converter(budget21));
        }
        return budgetList;
    }

    private static Double[] colInverter(Double[][] doubles, int column){
        Double[] doubles1 = new Double[doubles[0].length];
        for(int a = 0; a < doubles.length; a++) {
            doubles1[a] = doubles[a][column];
        }
        return doubles1;
    }


    private static List<Budget> budget2Converter(Budget2 budget2) {
        ArrayList<Budget> budgetList = new ArrayList<>();

        for (int i = 0; i < budget2.areas.length; i++) {
            Budget budget = new Budget();

            budget.setMonthlyMovement(colInverter(budget2.getMonthlySpend(),i));
            budget.setArea(budget2.getAreas()[i][i]);
            budget.setRemaining(colInverter(budget2.getRemaining(),i));
            budget.setAreaTotal(colInverter(budget2.getAreaAllowance(),i));
            budget.setCredit(false);
            budget.setOwner(budget2.owner);
            budgetList.add(budget);
        }
        return budgetList;
    }

    public Budget2(String[][] areas, Double[][] areaAllowance, Double[][] monthlySpend, Double[][] remaining, String owner) {
        this.areas = areas;
        this.areaAllowance = areaAllowance;
        this.monthlySpend = monthlySpend;
        this.remaining = remaining;
        this.owner = owner;
    }

    public String[][] getAreas() {
        return areas;
    }

    public void setAreas(String[][] areas) {
        this.areas = areas;
    }

    public Double[][] getAreaAllowance() {
        return areaAllowance;
    }

    public void setAreaAllowance(Double[][] areaAllowance) {
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setValue(int a, int b, Double value) {
        Double existing = this.areaAllowance[b][a];
        this.areaAllowance[b][a] = value;
        Double diff = existing - value;
        remaining[b][a] -= diff;
    }

}
