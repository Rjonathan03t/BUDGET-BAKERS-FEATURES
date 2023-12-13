package main;

import crudOperations.AccountCrudOperations;
import crudOperations.CurrencyCrudOperations;
import crudOperations.TransactionsCrudOperations;
import model.*;

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
            //accountCrudOperations.findAll();
            //accountCrudOperations.selectOne(1);
            //====SAVE ALL
            List<Account> accountToSave = new ArrayList<>();
            Account account1 = new Account(6, "saving", 1000.0, "BANK", 1);
            Account account2 = new Account(7, "saving", 20000.0, "MOBILE MONEY", 1);
            accountToSave.add(account1);
            accountToSave.add(account2);
            //accountCrudOperations.saveAll(accountToSave);

            //====SAVE
            Account account = new Account(8, "saving", 50000.0, "CASH", 2);
            //accountCrudOperations.save(account);


            // TRANSACTIONS CRUD
            TransactionsCrudOperations transactionsCrudOperations = new TransactionsCrudOperations(connection);
            //====FIND ALL
            //transactionsCrudOperations.findAll();

            //====SAVE ALL
            List<Transactions> transactionsToSave = new ArrayList<>();
            //Transactions transactions1 = new Transactions(4, "game", 90000.0, "DEBIT", LocalDateTime.of(2023, 12, 06, 05, 00));
            //Transactions transactions2 = new Transactions(5, "food", 50000.0, "DEBIT", LocalDateTime.of(2023, 11, 06, 12, 40));
            //transactionsToSave.add(transactions1);
            //transactionsToSave.add(transactions2);
            //transactionsCrudOperations.saveAll(transactionsToSave);

            //==== SAVE
            Transactions transactions = new Transactions(4, "got my salary of this mounth", 20000.0,TransactionType.DEBIT, LocalDateTime.of(2023, 11, 05, 16, 58), TransactionCategory.salary);
            //transactionsCrudOperations.save(transactions);

            //==== TRANSACTION (CREDIT)
            accountCrudOperations.makeTransaction(
                    10000.0,
                    3,
                    2,
                    2,
                    "got my first billion with this",
                    TransactionCategory.salary
            );

            /*accountCrudOperations.doTransfer(
                    10000.0,
                    1,
                    3,
                    3,
                    4,
                    3,
                    4,
                    "make credit from her account",
                    "make debit from his account",
                    TransactionCategory.salary,
                    TransactionCategory.restaurant
            );
             */

            // Currency CRUD
            CurrencyCrudOperations currencyCrudOperations = new CurrencyCrudOperations(connection);
            //====FIND ALL
            //currencyCrudOperations.findAll();

            //====SAVE
            Currency currency = new Currency(1, "Euro", "EUR");
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

            // Appel de la fonction getBalanceAtDateTime
            int id_account = 1;
            LocalDateTime dateTime = LocalDateTime.of(2023, 12, 6, 4, 0);
            //Double balance = accountCrudOperations.getBalanceAtDateTime(id_account, dateTime);

            //System.out.println("Balance at " + dateTime + ": " + balance);


            //AccountCrudOperations accountCrudOperations = new AccountCrudOperations(connection);
            // Obtenez la date et l'heure actuelles
            LocalDateTime currentDateTime = LocalDateTime.now();

            // Obtenez l'ID du compte pour lequel vous souhaitez obtenir le solde actuel
            // Remplacez par l'ID du compte souhaité

            // Obtenez le solde actuel en utilisant la méthode getCurrentBalance
            //Double currentBalance = accountCrudOperations.getCurrentBalance(id_account, currentDateTime);

            // Affichez le solde actuel suivi de la date et de l'heure actuelles à droite
            //System.out.println("Solde actuel du compte : " + currentBalance + "   Date et heure actuelles : " + currentDateTime);


             // Effectuer un transfert d'argent du compte 1 au compte 2
            int sourceAccountId = 1;
            int destinationAccountId = 2;
            double amountToTransfer = 100.0;

            // Appel de la méthode transferMoney
            //accountCrudOperations.transferMoney(sourceAccountId, destinationAccountId, amountToTransfer);

            // Afficher les soldes après le transfert
            //System.out.println("Solde du compte source après le transfert : " + accountCrudOperations.selectOne(sourceAccountId).getBalance());
            //System.out.println("Solde du compte destination après le transfert : " + accountCrudOperations.selectOne(destinationAccountId).getBalance());


            // Exemple d'utilisation : balanceHistoru

            LocalDateTime startDateTime = LocalDateTime.of(2023, 12, 1, 0, 0);
            LocalDateTime endDateTime = LocalDateTime.of(2023, 12, 6, 23, 59);

            //List<BalanceHistory> balanceHistoryList = accountCrudOperations.getBalanceHistoryInDateTimeRange(id_account, startDateTime, endDateTime);

            // Affiche l'historique du solde
            //for (BalanceHistory balanceHistory : balanceHistoryList) {
                //System.out.println("Date: " + balanceHistory.getDateTime() + ", Solde: " + balanceHistory.getBalance());


            //}
//TRANSFER HISTORY
            //accountCrudOperations.transferHistory(LocalDateTime.of(2023,11,10,1,0),LocalDateTime.of(2023,12,30,1,0));
            //accountCrudOperations.showAmountWithDate(1);


        } catch (Exception e) {
            System.out.println("Error about DB connection!");
            e.printStackTrace();
        }

    }
}