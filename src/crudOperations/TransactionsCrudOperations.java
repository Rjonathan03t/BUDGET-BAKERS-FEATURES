package crudOperations;

import genericInterface.CrudOperations;
import model.Transactions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return null;
    }

    @Override
    public Transactions save(Transactions toSave) throws SQLException {
        return null;
    }
}
