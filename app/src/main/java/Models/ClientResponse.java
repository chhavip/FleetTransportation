package Models;

import java.util.List;

/**
 * Created by chhavi on 30/8/15.
 */
public class ClientResponse {

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    List<Client> clients;
}
