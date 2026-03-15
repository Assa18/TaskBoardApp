package edu.bbte.idde.mtim2378.model;

import java.time.LocalDateTime;

public class Todo extends BaseEntity {

    private String title;

    private String description;

    private boolean done;

    private LocalDateTime deadLine;

    private Severity severity;

    public Todo() {
        super();
    }

    public Todo(String title, String description, LocalDateTime deadLine, Severity severity) {
        super();
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.severity = severity;
        done = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public LocalDateTime getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDateTime deadLine) {
        this.deadLine = deadLine;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }
}
