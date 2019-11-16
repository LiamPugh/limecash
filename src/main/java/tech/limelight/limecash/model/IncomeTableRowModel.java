package tech.limelight.limecash.model;

public class IncomeTableRowModel {

    private String budgetName;
    private Double[] months;
    private Boolean[] paymentCompleted;


    public IncomeTableRowModel(String budgetName, Double[] months, Boolean[] paymentCompleted) {
        this.budgetName = budgetName;
        this.months = months;
        this.paymentCompleted = paymentCompleted;
    }

    public IncomeTableRowModel(String budgetName, Double[] months, Double[] paymentCompleted) {
        this.budgetName = budgetName;
        this.months = months;
        Boolean[] completed = new Boolean[paymentCompleted.length];
        for(int i = 0; i < paymentCompleted.length; i++){
            completed[i] = months[i].equals(paymentCompleted[i]);
        }
        this.paymentCompleted = completed;
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
}
