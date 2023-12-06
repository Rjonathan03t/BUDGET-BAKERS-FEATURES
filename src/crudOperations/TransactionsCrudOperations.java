package crudOperations;

import genericInterface.CrudOperations;
import model.Account;
import model.Transactions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionsCrudOperations implements CrudOperations<Transactions> {
    private Connection connection;

    public TransactionsCrudOperations(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Transactions> findAll() throws SQLException {
        List<Transactions> allTransactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions_list";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                allTransactions.add(new Transactions(
                                result.getInt("id_transactions"),
                                result.getString("label"),
                                result.getDouble("amount"),
                                result.getString("type"),
                                result.getDate("date").toLocalDate()
                        )
                );
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println(allTransactions);
        return allTransactions;
    }

    @Override
    public List<Transactions> saveAll(List<Transactions> toSave) throws SQLException {
        List<Transactions> allTransactions = new ArrayList<>();
        String sql = "INSERT INTO transactions_list (id_transactions , label , amount , type, date) VALUES (? , ? ,? ,?, ?) ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try{
            for (Transactions transactions : toSave) {
                preparedStatement.setInt(1, transactions.getId_transactions());
                preparedStatement.setString(2, transactions.getLabel());
                preparedStatement.setDouble(3, transactions.getAmount());
                preparedStatement.setString(4, transactions.getType());
                preparedStatement.setDate(5, Date.valueOf(transactions.getDate()));
                preparedStatement.addBatch();
                preparedStatement.executeUpdate();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("INSERT 01");
        return toSave;
    }

    @Override
    public Transactions save(Transactions toSave) throws SQLException {
        String sql = "INSERT INTO transactions_list(id_transactions , label, amount, type,date) VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try{
            preparedStatement.setInt(1,toSave.getId_transactions());
            preparedStatement.setString(2,toSave.getLabel());
            preparedStatement.setDouble(3,toSave.getAmount());
            preparedStatement.setString(4,toSave.getType());
            preparedStatement.setDate(5, Date.valueOf(toSave.getDate()));
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("INSERT 01");
        return toSave;
    }
}
