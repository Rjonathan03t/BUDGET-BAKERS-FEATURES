package crudOperations;

import genericInterface.CrudOperations;
import model.Account;
import model.BalanceHistory;
import model.Transactions;
import org.w3c.dom.ls.LSOutput;
import java.time.LocalDate;
import java.sql.*;
import java.time.LocalDateTime;
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
                                result.getInt("id_currency")
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
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
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
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("INSERT 01");
        return toSave;
    }
    //======================= METHOD for making transactions (DONE but account without transactions' list) ===============================
    public Account selectOne(int id_account) throws SQLException {
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
        System.out.println(account);
        return account;
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

    public List<Transactions> transactionsDebit(double amount,int id_account,int id_account_transactions,int id_transactions)throws SQLException{
        Transactions transactions = new Transactions(id_transactions,"group",amount,"CREDIT", LocalDateTime.now());
        List<Transactions> Alltransactions = new ArrayList<>();
        String sql = "INSERT  INTO account_transactions (id_account_transactions,id_account,id_transactions) VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try{
            TransactionsCrudOperations transactionsCrudOperations = new TransactionsCrudOperations(connection);
            transactionsCrudOperations.save(transactions);
            preparedStatement.setInt(1,id_account_transactions);
            preparedStatement.setInt(2,id_account);
            preparedStatement.setInt(3,id_transactions);
            preparedStatement.executeUpdate();
            Alltransactions.add(transactions);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return Alltransactions;
    }

    public Account makeCredit(double amount, int id_account,int id_account_transactions, int id_transactions) throws SQLException {
        transactionsDebit(amount,id_account,id_account_transactions,id_transactions);
        credit(amount,id_account);
        return selectOne(id_account);
    }
   //======================================================================================

    public Double getCurrentBalance(int id_account) {
        try {
            // Obtenez la date et l'heure actuelles
            LocalDateTime currentDateTime = LocalDateTime.now();

            // Utilisez la méthode existante pour obtenir le solde à la date et à l'heure actuelles
            return getBalanceAtDateTime(id_account, currentDateTime);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérez l'exception selon les besoins de votre application
            return null;
        }
    }
    //=====function for obtaining the balance of an account at a given date and time=====
    public Double getBalanceAtDateTime(int id_account, LocalDateTime dateTime) throws SQLException {
        Double balance = 0.0;
        String sql = "SELECT SUM(CASE WHEN t.type = 'CREDIT' THEN t.amount ELSE -t.amount END) as total " +
                     "FROM transactions t " +
                     "JOIN account_transactions at ON t.id_transactions = at.id_transactions " +
                     "WHERE at.id_account = ? AND t.date <= ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_account);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(dateTime));

            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    balance = result.getDouble("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return balance;
    }

    // Fonction pour obtenir le solde actuel avec la date et l'heure actuelles
    public Double getCurrentBalance(int id_account, LocalDateTime currentDateTime) throws SQLException {
        return getBalanceAtDateTime(id_account, currentDateTime);
    }



    public List<BalanceHistory> getBalanceHistoryInDateTimeRange(int id_account, LocalDateTime startDateTime, LocalDateTime endDateTime) throws SQLException {
    List<BalanceHistory> balanceHistoryList = new ArrayList<>();
    String sql = "SELECT date, SUM(CASE WHEN t.type = 'CREDIT' THEN t.amount ELSE -t.amount END) as total " +
                 "FROM transactions t " +
                 "JOIN account_transactions at ON t.id_transactions = at.id_transactions " +
                 "WHERE at.id_account = ? AND t.date >= ? AND t.date <= ? " +
                 "GROUP BY t.date ORDER BY t.date";

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setInt(1, id_account);
        preparedStatement.setTimestamp(2, Timestamp.valueOf(startDateTime));
        preparedStatement.setTimestamp(3, Timestamp.valueOf(endDateTime));

        try (ResultSet result = preparedStatement.executeQuery()) {
            while (result.next()) {
                LocalDateTime dateTime = result.getTimestamp("date").toLocalDateTime();
                Double balance = result.getDouble("total");
                balanceHistoryList.add(new BalanceHistory(dateTime, balance));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return balanceHistoryList;
}


    public void transferMoney(int sourceAccountId, int destinationAccountId, double amount) throws SQLException {
       
        Account sourceAccount = selectOne(sourceAccountId);
        Account destinationAccount = selectOne(destinationAccountId);

        // Vérifier si les devises sont différentes et gérer la conversion (Euro vers Ariary)
        if (!sourceAccount.getId_currency().equals(destinationAccount.getId_currency())) {
           
            double exchangeRate = getExchangeRate(sourceAccount.getId_currency(), destinationAccount.getId_currency(), LocalDate.now());

            // Vérifier si le taux de change est disponible
            if (exchangeRate == -1) {
                System.out.println("Exchange rate not available for the specified currencies.");
                return;
            }

            // Convertir le montant de la source vers la destination
            amount *= exchangeRate;

           
            saveExchangeRate(sourceAccount.getId_currency(), destinationAccount.getId_currency(), exchangeRate, LocalDate.now());
        }

        // Effectuer le transfert d'argent
        credit(amount, destinationAccountId);
        debit(amount, sourceAccountId);
    }

    private void saveExchangeRate(int sourceCurrencyId, int destinationCurrencyId, double exchangeRate, LocalDate date) throws SQLException {
       
        String sql = "INSERT INTO currency_exchange_rate (source_currency, destination_currency, exchange_rate, date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, sourceCurrencyId);
            preparedStatement.setInt(2, destinationCurrencyId);
            preparedStatement.setDouble(3, exchangeRate);
            preparedStatement.setDate(4, Date.valueOf(date));
            preparedStatement.executeUpdate();
        }
    }

    private double getExchangeRate(int sourceCurrencyId, int destinationCurrencyId, LocalDate date) throws SQLException {
       
        String sql = "SELECT exchange_rate FROM currency_exchange_rate WHERE source_currency = ? AND destination_currency = ? AND date = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, sourceCurrencyId);
            preparedStatement.setInt(2, destinationCurrencyId);
            preparedStatement.setDate(3, Date.valueOf(date));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("exchange_rate");
                } else {
                    return -1; 
                }
            }
        }
    }


}
