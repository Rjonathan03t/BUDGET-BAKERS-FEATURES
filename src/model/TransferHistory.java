package model;

import java.time.LocalDateTime;

public class TransferHistory {
    private int id_transfer_history;
    private int id_debit_transaction;
    private int id_credit_transaction;
    private LocalDateTime transfer_date;
    private double transfer_amount;

    public TransferHistory(int id_transfer_history, int id_debit_transaction, int id_credit_transaction, LocalDateTime transfer_date, double transfer_amount) {
        this.id_transfer_history = id_transfer_history;
        this.id_debit_transaction = id_debit_transaction;
        this.id_credit_transaction = id_credit_transaction;
        this.transfer_date = transfer_date;
        this.transfer_amount = transfer_amount;
    }

    public int getId_transfer_history() {
        return id_transfer_history;
    }

    public void setId_transfer_history(int id_transfer_history) {
        this.id_transfer_history = id_transfer_history;
    }

    public int getId_debit_transaction() {
        return id_debit_transaction;
    }

    public void setId_debit_transaction(int id_debit_transaction) {
        this.id_debit_transaction = id_debit_transaction;
    }

    public int getId_credit_transaction() {
        return id_credit_transaction;
    }

    public void setId_credit_transaction(int id_credit_transaction) {
        this.id_credit_transaction = id_credit_transaction;
    }

    public LocalDateTime getTransfer_date() {
        return transfer_date;
    }

    public void setTransfer_date(LocalDateTime transfer_date) {
        this.transfer_date = transfer_date;
    }

    public double getTransfer_amount() {
        return transfer_amount;
    }

    public void setTransfer_amount(double transfer_amount) {
        this.transfer_amount = transfer_amount;
    }

    @Override
    public String toString() {
        return  "id_transfer_history = " + id_transfer_history +
                ", id_debit_transaction = " + id_debit_transaction +
                ", id_credit_transaction = " + id_credit_transaction +
                ", transfer_date = " + transfer_date +
                ", transfer_amount = " + transfer_amount;
    }
}
