package model;

public enum TransactionType {
    CREDIT,
    DEBIT;

    public static TransactionType fromString(String type) {
        return TransactionType.valueOf(type.toUpperCase());
    }
}
