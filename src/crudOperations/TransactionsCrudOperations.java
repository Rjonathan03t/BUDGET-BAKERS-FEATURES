package crudOperations;

import genericInterface.CrudOperations;
import model.Account;
import model.TransactionCategory;
import model.TransactionType;
import model.Transactions;

import java.sql.*;
import java.time.LocalDateTime;
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
        String sql = "SELECT * FROM transactions";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                String typeString = result.getString("type");
                String categoryString = result.getString("category");
                TransactionCategory transactionCategory = TransactionCategory.valueOf(categoryString);
                TransactionType transactionType = TransactionType.valueOf(typeString);
                allTransactions.add(new Transactions(
                                result.getInt("id_transactions"),
                                result.getString("label"),
                                result.getDouble("amount"),
                                transactionType,
                                result.getTimestamp("date").toLocalDateTime(),
                                transactionCategory
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
        String sql = "INSERT INTO transactions (id_transactions , label , amount , type, date, category) VALUES (? , ? ,? ,?, ?, ?) ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try{
            for (Transactions transactions : toSave) {
                preparedStatement.setInt(1, transactions.getId_transactions());
                preparedStatement.setString(2, transactions.getLabel());
                preparedStatement.setDouble(3, transactions.getAmount());
                preparedStatement.setString(4, transactions.getType().name());
                preparedStatement.setTimestamp(5, Timestamp.valueOf(transactions.getDate()));
                preparedStatement.setString(6,transactions.getCategory().name());
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
        String sql = "INSERT INTO transactions(id_transactions , label, amount, type,date,category) VALUES (?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            preparedStatement.setInt(1, toSave.getId_transactions());
            preparedStatement.setString(2, toSave.getLabel());
            preparedStatement.setDouble(3, toSave.getAmount());
            preparedStatement.setString(4, toSave.getType().name());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(toSave.getDate()));
            preparedStatement.setString(6,toSave.getCategory().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("INSERT 01");
        return toSave;
    }
}
