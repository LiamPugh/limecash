package tech.limelight.limecash.model;

public class BudgetTableRowModel {

    private String budgetName;
    private Double[] months;


    public BudgetTableRowModel(String budgetName, Double[] months) {
        this.budgetName = budgetName;
        this.months = months;
    }


    public String getBudgetName() {
        return budgetName;
    }

    public void setBudgetName(String budgetName) {
        this.budgetName = budgetName;
    }

    public Double[] getMonths() {
        return months;
    }

    public void setMonths(Double[] months) {
        this.months = months;
    }
}
