package tech.limelight.limecash.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

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

    public Transaction() {
    }

    public Transaction(String name, Date date, Double value, Integer quantity, Boolean incoming, Boolean complete, String owner) {
        this.name = name;
        this.date = date;
        this.value = value;
        this.quantity = quantity;
        this.incoming = incoming;
        this.complete = complete;
        this.owner = owner;
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
}
