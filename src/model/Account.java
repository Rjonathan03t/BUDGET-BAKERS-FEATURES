package model;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Account {
    private Integer id_account;
    private String name;
    private Double balance;
    private String type;
    private Integer id_currency;

    public Account(Integer id_account, String name, Double balance, String type, Integer id_currency) {
        this.id_account = id_account;
        this.name = name;
        this.balance = balance;
        this.type = type;
        this.id_currency = id_currency;
    }

    public Integer getId_account() {
        return id_account;
    }

    public void setId_account(Integer id_account) {
        this.id_account = id_account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId_currency() {
        return id_currency;
    }

    public void setId_currency(Integer id_currency) {
        this.id_currency = id_currency;
    }
    @Override
    public String toString() {
        return "id_account= " + id_account +
                ", name= '" + name + '\'' +
                ", balance= " + balance +
                ", type= '" + type + '\'' +
                ", id_currency= " + id_currency ;
    }
}
