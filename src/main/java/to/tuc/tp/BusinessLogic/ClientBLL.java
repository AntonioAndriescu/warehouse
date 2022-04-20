package to.tuc.tp.BusinessLogic;

import to.tuc.tp.DataAccess.ClientDAO;
import to.tuc.tp.DataAccess.OrderDAO;
import to.tuc.tp.Model.Client;
import to.tuc.tp.Model.Order;
import to.tuc.tp.Presentation.IClientView;

import java.sql.SQLException;
import java.util.Objects;

/**
 * Furnizeaza metode pentru interactionarea dintre lista de clienti si interfata grafica a utilizatorului
 */
public class ClientBLL {
    /**
     * Obiect de tipul IClientView
     */
    private IClientView cw;

    /**
     * Lista de clienti
     */
    private static ClientDAO clients = new ClientDAO();

    /**
     * Creaza un obiect instanta a clasei ClientBLL
     * @param i Obiect de tipul IClientView
     */
    public ClientBLL(IClientView i) {
        cw = i;
    }

    /**
     * Preia lista de obiecte de tip Client din tabelul "Client" din baza de date
     * @return lista de obiecte de tipul Product
     */
    public static ClientDAO vizualizare() {
        try {
            clients = ClientDAO.unmarshal();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    /**
     * Adauga in tabelul "Client" un obiect de tipul Client creat din campurile id, nume, prenume, adresa din clasa ClientView
     * @throws SQLException
     */
    public void addClient() throws SQLException {
        Client c = new Client(cw.getIdV(), cw.getNumeV(), cw.getPrenumeV(), cw.getAdresaV());
        ClientDAO.adaugareClient(c);
    }

    /**
     * Sterge un rand selectat din tabel
     * @throws SQLException
     */
    public void deleteClient() throws SQLException {
        String[] s = cw.getInformatiiRand();
        Client c = new Client(Integer.parseInt(s[0]), s[1], s[2], s[3]);
        ClientDAO.stergereClient(c);
        OrderDAO orders = OrderDAO.unmarshal();
        for(Order o : orders.getOrders()) {
            if(Objects.equals(o.getClientId(), c.getClientId())) {
                OrderDAO.deleteOrder(o);
            }
        }
    }

    /**
     * Actualizeaza un rand selectat din tabel cu valorile introduse in campurile id, nume, prenume, adresa din clasa ClientView
     * @throws SQLException
     */
    public void updateClient() throws SQLException {
        String[] s = cw.getInformatiiRand();
        Client c1 = new Client(Integer.parseInt(s[0]), s[1], s[2], s[3]);
        Client c2 = new Client(cw.getIdV(), cw.getNumeV(), cw.getPrenumeV(), cw.getAdresaV());
        ClientDAO.actualizareClient(c1, c2);
    }

    /**
     * Furnizeaza campul "data" penntru JTable in interfata grafica
     * @param clientList Lista de obiecte de tip Product
     * @return campul data
     */
    public Object[][] creareTabel(ClientDAO clientList) {
        int i = 0;
        int size = clientList.getClienti().size();
        Object[][] data = new Object[size][];
        for(Client c : clientList.getClienti()) {
            data[i] = new Object[] {
                    c.getClientId(),
                    c.getNume(),
                    c.getPrenume(),
                    c.getAdresa()};
            i++;
        }
        return data;
    }
}
