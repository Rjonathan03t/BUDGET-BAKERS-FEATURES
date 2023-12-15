package Features;

import crudOperations.AccountCrudOperations;
import crudOperations.TransactionsCrudOperations;
import model.Account;
import model.TransactionCategory;
import model.TransactionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class    DoTransactions {
    private Connection connection;

    public DoTransactions(Connection connection) {
        this.connection = connection;
    }



    public void credit(double amount, int id_account) throws SQLException {
        String sql = "UPDATE account SET balance = balance + ? WHERE id_account = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, id_account);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("UPDATE 01");
    }
    public void debit(double amount, int id_account) throws SQLException {
        String sql = "UPDATE account SET balance = balance - ? WHERE id_account = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, id_account);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("UPDATE 01");
    }

    public List<model.Transactions> transactionsCredit(double amount, int id_account, int id_account_transactions, int id_transactions , String label, TransactionCategory category) throws SQLException {
        model.Transactions transactions = new model.Transactions(id_transactions, label, amount, TransactionType.CREDIT , LocalDateTime.now(), category);
        List<model.Transactions> Alltransactions = new ArrayList<>();
        String sql1 = "INSERT  INTO account_transactions (id_account_transactions,id_account,id_transactions) VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql1);
        TransactionsCrudOperations transactionsCrudOperations = new TransactionsCrudOperations(connection);
        transactionsCrudOperations.save(transactions);
        try {
            preparedStatement.setInt(1, id_account_transactions);
            preparedStatement.setInt(2, id_account);
            preparedStatement.setInt(3, id_transactions);
            preparedStatement.executeUpdate();
            Alltransactions.add(transactions);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Alltransactions;
    }
    public List<model.Transactions> transactionsDebit(double amount, int id_account, int id_account_transactions, int id_transactions, String label, TransactionCategory category) throws SQLException {
        model.Transactions transactions = new model.Transactions(id_transactions, label, amount,TransactionType.DEBIT ,LocalDateTime.now(),category);
        List<model.Transactions> Alltransactions = new ArrayList<>();
        String sql = "INSERT  INTO account_transactions (id_account_transactions,id_account,id_transactions) VALUES (?,?,?) ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            TransactionsCrudOperations transactionsCrudOperations = new TransactionsCrudOperations(connection);
            transactionsCrudOperations.save(transactions);
            preparedStatement.setInt(1, id_account_transactions);
            preparedStatement.setInt(2, id_account);
            preparedStatement.setInt(3, id_transactions);
            preparedStatement.executeUpdate();
            Alltransactions.add(transactions);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Alltransactions;
    }
    //-------- METHOD FOR DOING TRANSACTION WITH CATEGORY ----------------
    public Account makeTransaction(double amount, int id_account, int id_account_transactions, int id_transactions, String label, TransactionCategory category)throws  SQLException{
        model.Transactions transactions = new model.Transactions(id_transactions, label, amount,TransactionType.CREDIT ,LocalDateTime.now(), category);
        if( transactions.getType() == TransactionType.CREDIT) {
            return makeCredit(amount, id_account, id_account_transactions, id_transactions,label, category);
        }else return makeDebit(amount,id_account,id_account_transactions,id_transactions,label,category);
    }

    public Account makeCredit(double amount, int id_account, int id_account_transactions, int id_transactions,String label,TransactionCategory category) throws SQLException {
        AccountCrudOperations accountCrudOperations = new AccountCrudOperations(connection);
        transactionsCredit(amount, id_account, id_account_transactions, id_transactions,label, category);
        credit(amount, id_account);
        return accountCrudOperations.selectOne(id_account);
    }

    public Account makeDebit(double amount, int id_account, int id_account_transactions, int id_transactions,String label,TransactionCategory category) throws SQLException {
        AccountCrudOperations accountCrudOperations = new AccountCrudOperations(connection);
        transactionsDebit(amount, id_account, id_account_transactions, id_transactions,label,category);
        debit(amount, id_account);
        return accountCrudOperations.selectOne(id_account);
    }
    //======================================================================================
}
