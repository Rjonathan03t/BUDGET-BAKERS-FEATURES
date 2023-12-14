package crudOperations;

import genericInterface.CrudOperations;
import model.Account;
import model.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyCrudOperations implements CrudOperations<Currency> {
    private Connection connection;

    public CurrencyCrudOperations(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Currency> findAll() throws SQLException {
        List<Currency> allCurrencies = new ArrayList<>();
        String sql = "SELECT * FROM currency";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                allCurrencies.add(new Currency(
                        result.getInt("id_currency"),
                        result.getString("name"),
                        result.getString("code")
                        
                    )
                );
            }
        }
        System.out.println(allCurrencies);
        return allCurrencies;
    }

    @Override
    public List<Currency> saveAll(List<Currency> toSave) throws SQLException {
        List<Currency> allCurrencies = new ArrayList<>();
        String sql = "INSERT INTO currency (id_currency, name, code) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            for (Currency currency : toSave) {
                preparedStatement.setInt(1, currency.getId_currency());
                preparedStatement.setString(3, currency.getName());
                preparedStatement.setString(2, currency.getCode());
                
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("INSERT currencies");
        return allCurrencies;
    }

    @Override
    public Currency save(Currency toSave) throws SQLException {
        String sql = "INSERT INTO currency (id_currency, name, code) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            preparedStatement.setInt(1, toSave.getId_currency());
            preparedStatement.setString(3, toSave.getName());
            preparedStatement.setString(2, toSave.getCode());
            
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("INSERT currency");
        return toSave;
    }
    public String selectOne(int id_account)throws SQLException{
        String sql = "SELECT currency.name FROM account INNER JOIN currency ON account.id_currency=currency.id_currency WHERE id_account = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        String q=null;
        try{
            preparedStatement.setInt(1,id_account);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
               q= resultSet.getString("name");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return q;
    }
}
