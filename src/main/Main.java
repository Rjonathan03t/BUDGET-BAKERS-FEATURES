package Main;

import crudOperations.AccountCrudOperations;
import crudOperations.TransactionsCrudOperations;
import model.Account;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
            //accountCrudOperations.findAll();

            //====SAVE ALL
            List<Account> accountToSave = new ArrayList<>();
            Account account1 = new Account(6,"saving",1,90000.0,"BANK");
            Account account2 = new Account(7,"saving",2,40000.0,"MOBILE MONEY");
            accountToSave.add(account1);
            accountToSave.add(account2);
            //accountCrudOperations.saveAll(accountToSave);

            //====SAVE
            Account account = new Account(8,"saving",1,100000.0,"CASH");
            //accountCrudOperations.save(account);


            // TRANSACTIONS CRUD
            TransactionsCrudOperations transactionsCrudOperations = new TransactionsCrudOperations(connection);
            //====FIND ALL
            transactionsCrudOperations.findAll();

            // Currency CRUD
            //CurrencyCrudOperations currencyCrudOperations = new CurrencyCrudOperations(connection);
            //====FIND ALL
            //currencyCrudOperations.findAll();

            //====SAVE
            //Currency currency = new Currency(1, "USD", "US Dollar");
            //currencyCrudOperations.save(currency);

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