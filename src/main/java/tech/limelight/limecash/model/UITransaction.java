package tech.limelight.limecash.model;

import java.util.Date;

public class UITransaction {

    private String name;
    private Date date;
    private Double value;
    private Integer quantity;
    private Boolean incoming;
    private Boolean complete;
    private String accountImpacted;

    public UITransaction() {
    }

    public UITransaction(String name, Date date, Double value, Integer quantity, Boolean incoming, Boolean complete, String accountImpacted) {
        this.name = name;
        this.date = date;
        this.value = value;
        this.quantity = quantity;
        this.incoming = incoming;
        this.complete = complete;
        this.accountImpacted = accountImpacted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getIncoming() {
        return incoming;
    }

    public void setIncoming(Boolean incoming) {
        this.incoming = incoming;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public String getAccountImpacted() {
        return accountImpacted;
    }

    public void setAccountImpacted(String accountImpacted) {
        this.accountImpacted = accountImpacted;
    }
}