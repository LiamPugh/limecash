package tech.limelight.limecash.model;

public class IncomeTableRowModel {

    private String budgetName;
    private Double[] months;
    private Boolean[] paymentCompleted;
    private Long id;


    public IncomeTableRowModel(String budgetName, Double[] months, Boolean[] paymentCompleted, Long id) {
        this.budgetName = budgetName;
        this.months = months;
        this.paymentCompleted = paymentCompleted;
        this.id = id;
    }

    public IncomeTableRowModel(String budgetName, Double[] months, Double[] paymentCompleted, Long id) {
        this.budgetName = budgetName;
        this.months = months;
        Boolean[] completed = new Boolean[paymentCompleted.length];
        for(int i = 0; i < paymentCompleted.length; i++){
            completed[i] = months[i].equals(paymentCompleted[i]);
        }
        this.paymentCompleted = completed;
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

    public Boolean[] getPaymentCompleted() {
        return paymentCompleted;
    }

    public void setPaymentCompleted(Boolean[] paymentCompleted) {
        this.paymentCompleted = paymentCompleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
