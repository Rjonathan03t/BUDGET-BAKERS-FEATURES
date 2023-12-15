package Features;

import model.Transactions;

import java.sql.*;
import java.time.LocalDateTime;

public class SumOfAmountCategory {
    private Connection connection;

    public SumOfAmountCategory(Connection connection) {
        this.connection = connection;
    }

    public double getSum(LocalDateTime startDate , LocalDateTime endDate) throws SQLException {
        String sql = "SELECT a.id_account,SUM(amount) AS amount, t.category  FROM account_transactions  at " +
                "INNER JOIN account a ON a.id_account = at.id_account " +
                "INNER JOIN transactions t ON t.id_transactions = at.id_transactions " +
                "WHERE t.date  BETWEEN ? AND ? GROUP BY  a.id_account ,t.category";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        double sum = 0.0;
        String category = null;
        int id_account = 0;
        try{
            preparedStatement.setTimestamp(1, Timestamp.valueOf(startDate));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(endDate));
           ResultSet resultSet = preparedStatement.executeQuery();
           while(resultSet.next()){
                sum = resultSet.getDouble("amount");
                category = resultSet.getString("category");
                id_account = resultSet.getInt("id_account");
               System.out.println("id_account = " + id_account + ", total amount = " + sum + ", category = "+ category);
           }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return sum;
    }
}
