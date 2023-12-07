import java.time.LocalDateTime;

public class BalanceHistory {
    private LocalDateTime dateTime;
    private Double balance;

    public BalanceHistory(LocalDateTime dateTime, Double balance) {
        this.dateTime = dateTime;
        this.balance = balance;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Double getBalance() {
        return balance;
    }
}
