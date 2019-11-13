package tech.limelight.limecash.model;


public class UITransfer {

    private String transferType;
    private String from;
    private String to;
    private Double value;

    public UITransfer() {
    }

    public UITransfer(String transferType, String from, String to, Double value) {
        this.transferType = transferType;
        this.from = from;
        this.to = to;
        this.value = value;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}