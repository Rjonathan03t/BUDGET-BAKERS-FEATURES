package Main;

import crudOperations.AccountCrudOperations;
import crudOperations.TransactionsCrudOperations;
import model.Account;
import model.Transactions;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        String jdbcUrl = System.getenv("url");
        String user = System.getenv("user");
        String password = System.getenv("password");

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);

            // ACCOUNT CRUD
            AccountCrudOperations accountCrudOperations = new AccountCrudOperations(connection);
            //====FIND ALL
            accountCrudOperations.findAll();

            //====SAVE ALL
            List<Account> accountToSave = new ArrayList<>();
            Account account1 = new Account(6,"saving",1000.0,"BANK",1,1);
            Account account2 = new Account(7,"saving",20000.0,"MOBILE MONEY",1, 2);
            accountToSave.add(account1);
            accountToSave.add(account2);
            //accountCrudOperations.saveAll(accountToSave);

            //====SAVE
            Account account = new Account(8,"saving",50000.0,"CASH",2,3);
            //accountCrudOperations.save(account);


            // TRANSACTIONS CRUD
            TransactionsCrudOperations transactionsCrudOperations = new TransactionsCrudOperations(connection);
            //====FIND ALL
            //transactionsCrudOperations.findAll();

            //====SAVE ALL
            List<Transactions> transactionsToSave = new ArrayList<>();
            Transactions transactions1 = new Transactions(4,"game",90000.0,"DEBIT", LocalDateTime.of(2023,12,06, 05, 00));
            Transactions transactions2 = new Transactions(5,"food",50000.0,"DEBIT", LocalDateTime.of(2023,11,06,12,40));
            transactionsToSave.add(transactions1);
            transactionsToSave.add(transactions2);
           //transactionsCrudOperations.saveAll(transactionsToSave);

            //==== SAVE
            Transactions transactions = new Transactions(6, "benevola" , 20000.0,"CREDIT", LocalDateTime.of(2023,11,05,16,58));
            //transactionsCrudOperations.save(transactions);

          // Currency CRUD
            CurrencyCrudOperations currencyCrudOperations = new CurrencyCrudOperations(connection);
            //====FIND ALL
            currencyCrudOperations.findAll();

            //====SAVE
            Currency currency = new Currency(1, "USD", "US Dollar");
            currencyCrudOperations.save(currency);

            //====DELETE
            currencyCrudOperations.delete(currency);

            // Operations on currency associations
            //CurrencyAssociationCrudOperations associationCrudOperations = new CurrencyAssociationCrudOperations(connection);

            // ==== FIND ALL CURRENCY ASSOCIATIONS
            //associationCrudOperations.findAll();

            // ==== SAVE CURRENCY ASSOCIATIONS
            /*List<CurrencyAssociation> associationsToSave = new ArrayList<>();
            CurrencyAssociation association1 = new CurrencyAssociation(1, 1, 6);  // Currency 1 is associated with Account 6
            CurrencyAssociation association2 = new CurrencyAssociation(2, 2, 7);  // Currency 2 is associated with Account 7
            associationsToSave.add(association1);
            associationsToSave.add(association2);
             */
            // associationCrudOperations.saveAll(associationsToSave);

        } catch (Exception e) {
            System.out.println("Error about DB connection!");
            e.printStackTrace();
        }

    }
}