package crudOperations;

import genericInterface.CrudOperations;
import model.*;
import org.w3c.dom.ls.LSOutput;
import java.time.LocalDate;
import java.sql.*;
import java.time.LocalDate;
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

    public List<Transactions> transactionsCredit(double amount, int id_account, int id_account_transactions, int id_transactions , String label,TransactionCategory category) throws SQLException {
        Transactions transactions = new Transactions(id_transactions, label, amount,TransactionType.CREDIT ,LocalDateTime.now(), category);
        List<Transactions> Alltransactions = new ArrayList<>();
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
    public List<Transactions> transactionsDebit(double amount, int id_account, int id_account_transactions, int id_transactions,String label,TransactionCategory category) throws SQLException {
        Transactions transactions = new Transactions(id_transactions, label, amount,TransactionType.DEBIT ,LocalDateTime.now(),category);
        List<Transactions> Alltransactions = new ArrayList<>();
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
    public Account makeTransaction(double amount, int id_account, int id_account_transactions, int id_transactions,String label,TransactionCategory category)throws  SQLException{
        Transactions transactions = new Transactions(id_transactions, label, amount,TransactionType.CREDIT ,LocalDateTime.now(), category);
        if( transactions.getType() == TransactionType.CREDIT) {
            return makeCredit(amount, id_account, id_account_transactions, id_transactions,label, category);
        }else return makeDebit(amount,id_account,id_account_transactions,id_transactions,label,category);
    }

    public Account makeCredit(double amount, int id_account, int id_account_transactions, int id_transactions,String label,TransactionCategory category) throws SQLException {
        transactionsCredit(amount, id_account, id_account_transactions, id_transactions,label, category);
        credit(amount, id_account);
        return selectOne(id_account);
    }

    public Account makeDebit(double amount, int id_account, int id_account_transactions, int id_transactions,String label,TransactionCategory category) throws SQLException {
        transactionsDebit(amount, id_account, id_account_transactions, id_transactions,label,category);
        debit(amount, id_account);
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
    //============================== METHOD TO DO TRANSFER =================================================
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
               TransactionCategory category2
       ) throws SQLException {
           makeCredit( amount,  id_account1,  id_account_transactions1,  id_transactions1,label1,category1);
           makeDebit( amount,  id_account2,  id_account_transactions2,  id_transactions2,label2,category2);
       }
    //======================================================================================================
    //============================= METHOD TO SHOW TRANSFER HISTORY WITH =======================================================


    public void showAccountTransfer(LocalDateTime startDate , LocalDateTime endDate) throws SQLException {
        String sql = "SELECT id_debit_transaction , id_credit_transaction ,transfer_date FROM transfer_history WHERE transfer_date BETWEEN ? AND ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try{
            preparedStatement.setTimestamp(1,Timestamp.valueOf(startDate));
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

    public void transferHistory(LocalDateTime startDate , LocalDateTime endDate) throws SQLException {
           showAccountTransfer(startDate,endDate);
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
    //====================================================================================================================

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

    public double getMoneyFlowSum(int id_account, LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        double totalAmount = 0;

        // Somme des entrées (crédits)
        String creditSql = "SELECT COALESCE(SUM(amount), 0) AS total_amount FROM transactions " +
                           "WHERE id_account_destination = ? AND type = 'CREDIT' AND date BETWEEN ? AND ?";
        try (PreparedStatement creditStatement = connection.prepareStatement(creditSql)) {
            creditStatement.setInt(1, id_account);
            creditStatement.setTimestamp(2, Timestamp.valueOf(startDate));
            creditStatement.setTimestamp(3, Timestamp.valueOf(endDate));

            try (ResultSet creditResult = creditStatement.executeQuery()) {
                if (creditResult.next()) {
                    totalAmount += creditResult.getDouble("total_amount");
                }
            }
        }

        // Soustraire la somme des sorties (débits)
        String debitSql = "SELECT COALESCE(SUM(amount), 0) AS total_amount FROM transactions " +
                          "WHERE id_account_source = ? AND type = 'DEBIT' AND date BETWEEN ? AND ?";
        try (PreparedStatement debitStatement = connection.prepareStatement(debitSql)) {
            debitStatement.setInt(1, id_account);
            debitStatement.setTimestamp(2, Timestamp.valueOf(startDate));
            debitStatement.setTimestamp(3, Timestamp.valueOf(endDate));

            try (ResultSet debitResult = debitStatement.executeQuery()) {
                if (debitResult.next()) {
                    totalAmount -= debitResult.getDouble("total_amount");
                }
            }
        }

        return totalAmount;
    }


    public double getCategorySum(int id_account, LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        double restaurantTotal = 0;
        double salaireTotal = 0;

        String sql = "SELECT COALESCE(SUM(CASE WHEN t.category = 'restaurant' THEN t.amount ELSE 0 END), 0) AS restaurant_total, " +
                     "COALESCE(SUM(CASE WHEN t.category = 'salaire' THEN t.amount ELSE 0 END), 0) AS salaire_total " +
                     "FROM transactions t " +
                     "LEFT JOIN account_transactions at ON t.id_transactions = at.id_transactions " +
                     "WHERE at.id_account = ? AND t.date BETWEEN ? AND ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_account);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(startDate));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(endDate));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    restaurantTotal = resultSet.getDouble("restaurant_total");
                    salaireTotal = resultSet.getDouble("salaire_total");
                }
            }
        }

        System.out.println("Restaurant Total: " + restaurantTotal);
        System.out.println("Salaire Total: " + salaireTotal);

        return restaurantTotal + salaireTotal;
    }


}
