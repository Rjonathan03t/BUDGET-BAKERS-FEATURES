package model;

public class Transactions {
    private Integer id_transactions;
    private String label;
    private Double amount;
    private String type;

    public Transactions(Integer id_transactions, String label, Double amount, String type) {
        this.id_transactions = id_transactions;
        this.label = label;
        this.amount = amount;
        this.type = type;
    }

    public Integer getId_transactions() {
        return id_transactions;
    }

    public void setId_transactions(Integer id_transactions) {
        this.id_transactions = id_transactions;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return  "id_transactions= " + id_transactions +
                ", label= '" + label + '\'' +
                ", amount= " + amount +
                ", type= '" + type + '\'';
    }
}
