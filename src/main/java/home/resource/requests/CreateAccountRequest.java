package home.resource.requests;

public class CreateAccountRequest {
    private long initialAmount;

    public CreateAccountRequest() {
    }

    public CreateAccountRequest(long initialAmount) {
        this.initialAmount = initialAmount;
    }

    public long getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(long initialAmount) {
        this.initialAmount = initialAmount;
    }
}
