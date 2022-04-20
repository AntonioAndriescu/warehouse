package to.tuc.tp.DataAccess;

import to.tuc.tp.Connection.ConnectionFactory;
import to.tuc.tp.Model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Furnizeaza metode pentru interactionarea cu tabelul "Product" din baza de date
 */
public class ProductDAO {
    /**
     * Lista cu produse din tabelul "Product"
     */
    private List<Product> produse;

    /**
     * Returneza lista cu clientii din tabelul "Product"
     * @return lista de produse
     */
    public List<Product> getProduse() {
        return produse;
    }

    /**
     * Seteaza o lista cu obiecte de tip Product
     */
    public void setProduse(List<Product> produse) {
        this.produse = produse;
    }

    static ProductDAO productList = new ProductDAO();
    static {
        productList.setProduse(new ArrayList<Product>());
    }

    private final static String findStatementString = "SELECT * FROM product";
    private static final String insertStatementString = "INSERT INTO product (productId, denumire, pret, cantitate)"
            + " VALUES (?,?,?,?)";
    private static final String deleteStatementString = "DELETE FROM product where productId = ?";
    private static final String updateStatementString = "UPDATE product SET productId = ?, denumire = ?, pret = ?, cantitate = ? WHERE productId = ?";
    private static final String updateQuantityStatementString = "UPDATE product SET cantitate = ? WHERE productId = ?";

    /**
     * Creaza o lista de obiecte de tip Product cu produsele din baza de date
     * @return lista de produse
     * @throws SQLException
     */
    public static ProductDAO unmarshal() throws SQLException {
        productList.getProduse().clear();
        try {
            Connection dbConnection = new ConnectionFactory().getConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(findStatementString);
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getFloat(3),
                        rs.getInt(4));
                productList.getProduse().add(product);
            }
            dbConnection.close();

        } catch(Exception e) {
            System.out.println(e);
        }
        return productList;
    }

    /**
     * Adauga un produs in tabelul "Product" din baza de date
     * @param product Obiectul de tip Product care urmeaza sa fie adaugat
     * @throws SQLException
     */
    public static void adaugareProdus(Product product) throws SQLException {
        Connection dbConnection = ConnectionFactory.singleInstance.getConnection();
        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, product.getProductId());
            insertStatement.setString(2, product.getDenumire());
            insertStatement.setFloat(3, product.getPret());
            insertStatement.setInt(4, product.getCantitate());
            insertStatement.executeUpdate();
            ResultSet rs = insertStatement.getGeneratedKeys();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Sterge un produs din tabelul "Product" din baza de date
     * @param product Obiectul de tip Product pe care dorim sa il stergem
     * @throws SQLException
     */
    public static void stergereProdus(Product product)throws SQLException {
        Connection dbConnection = ConnectionFactory.singleInstance.getConnection();
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1, product.getProductId());
            deleteStatement.execute();
            ResultSet rs = deleteStatement.getGeneratedKeys();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Modificam datele despre un produs din tabelul "Product" din baza de date
     * @param product1 Produsul pe care dorim sa il modificam
     * @param product2 Produsul cu datele cu care dori sa facem modificarea
     * @throws SQLException
     */
    public static void updateProdus(Product product1, Product product2) throws SQLException {
        Connection dbConnection = ConnectionFactory.singleInstance.getConnection();
        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(updateStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, product2.getProductId());
            insertStatement.setString(2, product2.getDenumire());
            insertStatement.setFloat(3, product2.getPret());
            insertStatement.setInt(4, product2.getCantitate());
            insertStatement.setInt(5, product1.getProductId());
            insertStatement.executeUpdate();
            ResultSet rs = insertStatement.getGeneratedKeys();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Scadem stock-ul unui produs din tabelul "Product" din baza de date
     * @param p Produsul caruia dorim sa ii modificam stock-ul
     * @param quantity Cantitatea pe care dorim sa o scadem din stock
     */
    public static void actualizareCantitate(Product p, Integer quantity)  {
        Connection dbConnection = ConnectionFactory.singleInstance.getConnection();
        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(updateQuantityStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, p.getCantitate() - quantity);
            insertStatement.setInt(2, p.getProductId());
            insertStatement.executeUpdate();
            ResultSet rs = insertStatement.getGeneratedKeys();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
