package Models;

import java.time.LocalDate;

public class Notification {
    private LocalDate date;
    private String type;
    private String message;
    private String id;

    public Notification(LocalDate date, String type, String message, String id) {
        this.date = date;
        this.type = type;
        this.message = message;
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
    public String getId() {
        return id;
    }
}
