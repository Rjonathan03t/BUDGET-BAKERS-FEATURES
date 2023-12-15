package Features;

import crudOperations.CurrencyCrudOperations;
import model.TransactionCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transfer {
    private Connection connection;

    public Transfer(Connection connection) {
        this.connection = connection;
    }
    //============================== METHOD TO DO TRANSFER =================================================
    DoTransactions transactions = new DoTransactions(connection);

    public void addToTransferHistory(
            int id_transfer_history,
            int id_debit_transaction,
            int id_credit_transaction,
            LocalDateTime transfer_date,
            double transfer_amount
    ) throws SQLException {
        String sql = "INSERT INTO transfer_history (id_transfer_history,id_debit_transaction,id_credit_transaction, transfer_date, transfer_amount )VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try{
            preparedStatement.setInt(1,id_transfer_history);
            preparedStatement.setInt(2,id_debit_transaction);
            preparedStatement.setInt(3,id_credit_transaction);
            preparedStatement.setTimestamp(4, Timestamp.valueOf(transfer_date));
            preparedStatement.setDouble(5,transfer_amount);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("INSERT 01 IN TRANSFER HISTORY");
    }

    public void doTransfer (
            double amount,
            int id_account1,
            int id_account2,
            int id_account_transactions1,
            int id_account_transactions2,
            int id_transactions1,
            int id_transactions2,
            String label1,
            String label2,
            TransactionCategory category1,
            TransactionCategory category2,
            int id_transfer_history,
            LocalDateTime transfer_date
    ) throws SQLException {
        CurrencyCrudOperations currencyCrudOperations = new CurrencyCrudOperations(connection);
        if(id_account1 == id_account2){
            System.out.println("can not do the transfer because this is the same account");
        }else if(Objects.equals(currencyCrudOperations.selectOne(id_account1), currencyCrudOperations.selectOne(id_account2))){
            addToTransferHistory(id_transfer_history,id_account2,id_account1,transfer_date,amount);
            transactions.makeCredit( amount,  id_account1,  id_account_transactions1,  id_transactions1,label1,category1);
            transactions.makeDebit( amount,  id_account2,  id_account_transactions2,  id_transactions2,label2,category2);
        }else{
            System.out.println("can not do the transfer because this is the same account");
        }
    }
    //======================================================================================================
}
