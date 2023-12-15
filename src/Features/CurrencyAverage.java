package Features;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CurrencyAverage {
    private Connection connection;

    public CurrencyAverage(Connection connection) {
        this.connection = connection;
    }
    //=============== GET THE AVERAGE OF CURRENCY IN A DATE ===================
    public double getAverage(LocalDateTime startDate,LocalDateTime endDate) throws SQLException {
       String sql = "SELECT avg(amount) AS average FROM currency_value WHERE date_effect BETWEEN ? AND ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        double avg = 0;
        try{
            preparedStatement.setTimestamp(1, Timestamp.valueOf(startDate));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(endDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                avg = resultSet.getDouble("average");
                System.out.println("average at this date : "+ avg + " AR");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return avg;
    }
    public double getMinCurrency(LocalDateTime startDate,LocalDateTime endDate)throws  SQLException{
        String sql = "SELECT amount FROM currency_value WHERE date_effect  BETWEEN ? AND ? ORDER BY amount ASC LIMIT 1";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        double minCurrency = 0;
        try{
            preparedStatement.setTimestamp(1,Timestamp.valueOf(startDate));
            preparedStatement.setTimestamp(2,Timestamp.valueOf(endDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                minCurrency=resultSet.getDouble("amount");
                System.out.println("The minimum currency value today is : "+ minCurrency);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return minCurrency;
    }

    public double getMaxCurrency(LocalDateTime startDate,LocalDateTime endDate)throws  SQLException{
        String sql = "SELECT amount FROM currency_value WHERE date_effect  BETWEEN ? AND ? ORDER BY amount DESC LIMIT 1";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        double maxCurrency = 0;
        try{
            preparedStatement.setTimestamp(1,Timestamp.valueOf(startDate));
            preparedStatement.setTimestamp(2,Timestamp.valueOf(endDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                maxCurrency=resultSet.getDouble("amount");
                System.out.println("The maximum currency value today is : "+ maxCurrency);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return maxCurrency;
    }

    public double getMedianCurrency(LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        String sql = "SELECT amount FROM currency_value WHERE date_effect BETWEEN ? AND ? ORDER BY amount";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        List<Double> values = new ArrayList<>();

        try {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(startDate));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(endDate));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                double amount = resultSet.getDouble("amount");
                values.add(amount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(calculateMedian(values));
        return calculateMedian(values);
    }
    private double calculateMedian(List<Double> values) {
        int size = values.size();
        int middle = size / 2;

        if (size % 2 == 1) {
            return values.get(middle);
        } else {
            double value1 = values.get(middle - 1);
            double value2 = values.get(middle);
            return (value1 + value2) / 2;
        }
    }
}
