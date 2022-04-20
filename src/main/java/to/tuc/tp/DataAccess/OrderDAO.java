package to.tuc.tp.DataAccess;

import to.tuc.tp.Connection.ConnectionFactory;
import to.tuc.tp.Model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Furnizeaza metode pentru interactiunea cu tabelul "Orders" din baza de date
 */
public class OrderDAO {
    /**
     * Lista cu comenzile din tabelul "Orders"
     */
    private List<Order> orders;

    /**
     * Returneaza lista cu comenzile din tabelul "Orders"
     * @return lista de comenzi
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Seteaza o lista cu obiecte de tip Order
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    static OrderDAO ordersList = new OrderDAO();
    static {
        ordersList.setOrders(new ArrayList<Order>());
    }

    private final static String findStatementString = "SELECT * FROM orders";
    private static final String insertStatementString = "INSERT INTO orders (orderId, clientId, productId, quantity, pretTotal)"
            + " VALUES (?,?,?,?,?)";
    private static final String deleteStatementString = "DELETE FROM orders where orderId = ?";


    /**
     * Creaza o lista de obiecte de tip Order cu comenzile din baza de date
     * @return lista de comenzi
     * @throws SQLException
     */
    public static OrderDAO unmarshal() throws SQLException {
        ordersList.getOrders().clear();
        try {
            Connection dbConnection = new ConnectionFactory().getConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(findStatementString);
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getFloat(5));
                ordersList.getOrders().add(order);
            }
            dbConnection.close();

        } catch(Exception e) {
            System.out.println(e);
        }
        return ordersList;
    }

    /**
     * Adauga o comanda in tabelul "Orders" din baza de date
     * @param order Obiectul de tip Comanda care urmeaza sa fie adaugat
     * @throws SQLException
     */
    public static void adaugareComanda(Order order) throws SQLException {
        Connection dbConnection = ConnectionFactory.singleInstance.getConnection();
        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, order.getOrderId());
            insertStatement.setInt(2, order.getClientId());
            insertStatement.setInt(3, order.getProductId());
            insertStatement.setInt(4,order.getQuantity() );
            insertStatement.setFloat(5, order.getPretTotal());
            insertStatement.executeUpdate();
            ResultSet rs = insertStatement.getGeneratedKeys();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Sterge o comanda din tabelul "Orders" din baza de date
     * @param order Obiectul de tip Order pe care dorim sa il stergem
     * @throws SQLException
     */
    public static void deleteOrder(Order order) throws SQLException {
        Connection dbConnection = ConnectionFactory.singleInstance.getConnection();
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1, order.getOrderId());
            deleteStatement.execute();
            ResultSet rs = deleteStatement.getGeneratedKeys();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
