package crudOperations;

import genericInterface.CrudOperations;
import model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountCrudOperations implements CrudOperations<Account> {
    private Connection connection;

    public AccountCrudOperations(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() throws SQLException {
        List<Account> allAccount = new ArrayList<>();
        String sql = "SELECT * FROM account";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                allAccount.add(new Account(
                                result.getInt("id_account"),
                                result.getString("name"),
                                result.getDouble("balance"),
                                result.getString("type"),
                                result.getInt("id_currency"),
                                result.getInt("id_transactions")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(allAccount);
        return allAccount;
    }

    @Override
    public List<Account> saveAll(List<Account> toSave) throws SQLException {
        List<Account> allAccount = new ArrayList<>();
        String sql = "INSERT INTO account (id_account, name, id_currency, balance, type) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            for (Account account : toSave) {
                preparedStatement.setInt(1, account.getId_account());
                preparedStatement.setString(2, account.getName());
                preparedStatement.setDouble(3, account.getBalance());
                preparedStatement.setString(4, account.getType());
                preparedStatement.setInt(5, account.getId_currency());
                preparedStatement.setInt(6, account.getId_transactions());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            ;
        }
        System.out.println("INSERT 01");
        return allAccount;
    }

    @Override
    public Account save(Account toSave) throws SQLException {
        String sql = "INSERT INTO account (id_account, name, id_currency, balance, type) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            preparedStatement.setInt(1, toSave.getId_account());
            preparedStatement.setString(2, toSave.getName());
            preparedStatement.setDouble(3, toSave.getBalance());
            preparedStatement.setString(4, toSave.getType());
            preparedStatement.setInt(5, toSave.getId_currency());
            preparedStatement.setInt(6, toSave.getId_transactions());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("INSERT 01");
        return toSave;
    }
}
