package model;

import java.time.LocalDateTime;

public class Transactions {
    private Integer id_transactions;
    private String label;
    private Double amount;
    private TransactionType type;
    private LocalDateTime date;
    private TransactionCategory category;

    public Transactions(Integer id_transactions, String label, Double amount, TransactionType type, LocalDateTime date, TransactionCategory category) {
        this.id_transactions = id_transactions;
        this.label = label;
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.category = category;
        if(category == TransactionCategory.salary){
            this.type = TransactionType.CREDIT;
        }else {
            this.type = TransactionType.DEBIT;
        }
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

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "id_transactions= " + id_transactions +
                ", label= '" + label + '\'' +
                ", amount= " + amount +
                ", type= " + type +
                ", date= " + date +
                ", category= " + category;
    }
}
