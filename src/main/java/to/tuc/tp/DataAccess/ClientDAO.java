package to.tuc.tp.DataAccess;

import to.tuc.tp.Connection.ConnectionFactory;
import to.tuc.tp.Model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Furnizeaza metode pentru interactionarea cu tabelul "Client" din baza de date
 */
public class ClientDAO {
    /**
     * Lista cu clientii din tabelul "Client"
     */
    private List<Client> clienti;

    /**
     * Returneaza lista cu clientii din tabelul "Client"
     * @return lista de clienti
     */
    public List<Client> getClienti() {
        return clienti;
    }

    /**
     * Seteaza o lista cu obiecte de tip Client
     */
    public void setClienti(List<Client> clienti) {
        this.clienti = clienti;
    }

    static ClientDAO clientList = new ClientDAO();
    static {
        clientList.setClienti(new ArrayList<Client>());
    }

    private final static String findStatementString = "SELECT * FROM client";
    private static final String insertStatementString = "INSERT INTO client (clientId, nume, prenume, adresa)"
            + " VALUES (?,?,?,?)";
    private static final String deleteStatementString = "DELETE FROM client where clientId = ?";
    private static final String updateStatementString = "UPDATE client SET clientId = ?, nume = ?, prenume = ?, adresa = ? WHERE clientId = ?";

    /**
     * Creaza o lista de obiecte de tip Client cu clientii din baza de date
     * @return lista de clienti
     * @throws SQLException
     */
    public static ClientDAO unmarshal() throws SQLException {
        clientList.getClienti().clear();
        try {
            Connection dbConnection = new ConnectionFactory().getConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(findStatementString);
            while (rs.next()) {
                Client client = new Client(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
                clientList.getClienti().add(client);
            }
            dbConnection.close();

        } catch(Exception e) {
            System.out.println(e);
        }
        return clientList;
    }

    /**
     * Adauga un client in tabelul "Client" din baza de date
     * @param client Obiectul de tip Client care urmeaza sa fie adaugat
     * @throws SQLException
     */
    public static void adaugareClient(Client client) throws SQLException {
        Connection dbConnection = ConnectionFactory.singleInstance.getConnection();
        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, client.getClientId());
            insertStatement.setString(2, client.getNume());
            insertStatement.setString(3, client.getPrenume());
            insertStatement.setString(4, client.getAdresa());
            insertStatement.executeUpdate();
            ResultSet rs = insertStatement.getGeneratedKeys();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Sterge un client din tabelul "Client" din baza de date
     * @param client Obiectul de tip Client pe care dorim sa il stergem
     * @throws SQLException
     */
    public static void stergereClient(Client client) throws SQLException {
        Connection dbConnection = ConnectionFactory.singleInstance.getConnection();
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1, client.getClientId());
            deleteStatement.execute();
            ResultSet rs = deleteStatement.getGeneratedKeys();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Modificam datele despre un client din tabelul "Client" din baza de date
     * @param client1 Clientul pe care dorim sa il modificam
     * @param client2 Clientul cu datele cu care dorim sa se faca modificarea
     * @throws SQLException
     */
    public static void actualizareClient(Client client1, Client client2) throws SQLException {
        Connection dbConnection = ConnectionFactory.singleInstance.getConnection();
        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(updateStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, client2.getClientId());
            insertStatement.setString(2, client2.getNume());
            insertStatement.setString(3, client2.getPrenume());
            insertStatement.setString(4, client2.getAdresa());
            insertStatement.setInt(5, client1.getClientId());
            insertStatement.executeUpdate();
            ResultSet rs = insertStatement.getGeneratedKeys();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
