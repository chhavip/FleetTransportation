package Models;

/**
 * Created by chhavi on 30/8/15.
 */
public class Client  {
    private String ClientName;
    private int ClientId;
    private String OrderBookedFor;

    public String getOrderSegment() {
        return OrderSegment;
    }

    public void setOrderSegment(String orderSegment) {
        OrderSegment = orderSegment;
    }

    public String getOrderBookedFor() {
        return OrderBookedFor;
    }

    public void setOrderBookedFor(String orderBookedFor) {
        OrderBookedFor = orderBookedFor;
    }

    public int getClientId() {
        return ClientId;
    }

    public void setClientId(int clientId) {
        ClientId = clientId;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    private String OrderSegment;
}
