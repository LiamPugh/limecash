package tech.limelight.limecash.model;

public class DebitsTableRowModel {

    private String budgetName;
    private Double[] months;
    private Long id;

    public DebitsTableRowModel(String budgetName, Double[] months, Long id) {
        this.budgetName = budgetName;
        this.months = months;
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
