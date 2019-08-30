package tech.limelight.limecash.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import tech.limelight.limecash.controller.AccountController;
import tech.limelight.limecash.repository.AccountRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

import static tech.limelight.limecash.controller.AccountController.getAccIdFromName;

@Data
@Entity
public class Transaction {

    private @Id @GeneratedValue Long id;
    private String name;
    private Date date;
    private Double value;
    private Integer quantity;
    private Boolean incoming;
    private Boolean complete;
    private String owner;
    private Long accountID;

    public Transaction() {
    }

    public Transaction(UITransaction transaction){
        this.name = transaction.getName();
        this.date = transaction.getDate();
        this.value = transaction.getValue();
        this.quantity = transaction.getQuantity();
        this.incoming = transaction.getIncoming();
        this.complete = transaction.getComplete();
        this.owner = SecurityContextHolder.getContext().getAuthentication().getName();
        this.accountID = getAccIdFromName(transaction.getAccountImpacted());
    }

    public Transaction(String name, Date date, Double value, Integer quantity, Boolean incoming, Boolean complete, String owner, Long accountID) {
        this.name = name;
        this.date = date;
        this.value = value;
        this.quantity = quantity;
        this.incoming = incoming;
        this.complete = complete;
        this.owner = owner;
        this.accountID = accountID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getAccountID() {
        return accountID;
    }

    public void setAccountID(Long accountID) {
        this.accountID = accountID;
    }
}
