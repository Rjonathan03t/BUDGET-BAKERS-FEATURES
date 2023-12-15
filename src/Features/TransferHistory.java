package Features;

import model.Account;

import java.sql.*;
import java.time.LocalDateTime;

public class TransferHistory {
    private Connection connection;

    public TransferHistory(Connection connection) {
        this.connection = connection;
    }

    //============================= METHOD TO SHOW TRANSFER HISTORY WITH =======================================================

    public void showAccountTransfer(LocalDateTime startDate , LocalDateTime endDate) throws SQLException {
        String sql = "SELECT id_debit_transaction , id_credit_transaction ,transfer_date FROM transfer_history WHERE transfer_date BETWEEN ? AND ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try{
            preparedStatement.setTimestamp(1, Timestamp.valueOf(startDate));
            preparedStatement.setTimestamp(2,Timestamp.valueOf(endDate));
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                int debit = result.getInt("id_debit_transaction");
                int credit = result.getInt("id_credit_transaction");
                LocalDateTime date = result.getTimestamp("transfer_date").toLocalDateTime();

                Account creditAccount = getAccountDetails(credit);
                Account debitAccount = getAccountDetails(debit);
                System.out.println("Debit Account Details: " + debitAccount);
                System.out.println("Credit Account Details: " + creditAccount);
                System.out.println("Transfer Date: " + date);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }


    }

    public Account getAccountDetails(int id_account) throws SQLException {
        Account account = null;
        String sql = "SELECT * FROM account WHERE id_account = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_account);
            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    account = new Account(
                            result.getInt("id_account"),
                            result.getString("name"),
                            result.getDouble("balance"),
                            result.getString("type"),
                            result.getInt("id_currency")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    public void transferHistory(LocalDateTime startDate , LocalDateTime endDate) throws SQLException {
        showAccountTransfer(startDate,endDate);
    }
    //====================================================================================================================
}
