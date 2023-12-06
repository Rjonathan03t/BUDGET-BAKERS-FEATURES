package model;

public class Account {
    private Integer id_account;
    private String username;
    private Integer id_currency;
    private Integer balance;
    private String type ;

    public Account(Integer id_account, String username, Integer id_currency, Integer balance, String type) {
        this.id_account = id_account;
        this.username = username;
        this.id_currency = id_currency;
        this.balance = balance;
        this.type = type;
    }

    public Integer getId_account() {
        return id_account;
    }

    public void setId_account(Integer id_account) {
        this.id_account = id_account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId_currency() {
        return id_currency;
    }

    public void setId_currency(Integer id_currency) {
        this.id_currency = id_currency;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "id_account= " + id_account +
                ", username= '" + username + '\'' +
                ", id_currency= " + id_currency +
                ", balance= " + balance +
                ", type= '" + type + '\'';
    }
}
