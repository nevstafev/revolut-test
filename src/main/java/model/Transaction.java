package model;

public class Transaction {
    //Transaction statuses
    public static final String SUBMITTED = "submitted";
    public static final String RUNNING = "running";
    public static final String FINISHED = "finished";
    public static final String FAILED = "failed";

    String id;
    String sourceId;
    String destinationId;
    long amount;
    String status;

    public Transaction(String id, String sourceId, String destinationId, long amount) {
        this.id = id;
        this.sourceId = sourceId;
        this.destinationId = destinationId;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
