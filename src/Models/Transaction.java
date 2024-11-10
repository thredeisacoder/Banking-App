package Models;

import java.time.LocalDate;

public class Transaction {
    private String id;
    private String type;
    private Double money;
    private LocalDate date;
    private String source;
    private String destination;
    public Transaction(String id, String type, Double money, LocalDate date, String source, String destination) {
        this.id = id;
        this.type = type;
        this.money = money;
        this.date = date;
        this.source = source;
        this.destination = destination;
    }
    public String getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public Double getMoney() {
        return money;
    }
    public LocalDate getDate() {
        return date;
    }
    public String getSource() {
        return source;
    }
    public String getDestination() {
        return destination;
    }
}
