package to.tuc.tp.BusinessLogic;

import to.tuc.tp.DataAccess.ProductDAO;
import to.tuc.tp.Model.Product;
import to.tuc.tp.Presentation.IProductView;

import java.sql.SQLException;

/**
 * Furnizeaza metode pentru interactionarea dintre lista de produse si interfata grafica a utilizatorului
 */
public class ProductBLL {
    /**
     * Obiect de tipul IProductView
     */
    private IProductView pv;

    /**
     * Lista de produse
     */
    private static ProductDAO products = new ProductDAO();

    /**
     * Creaza un obiect instanta a clasei ProductBLL
     * @param i Obiect de tipul IProductView
     */
    public ProductBLL(IProductView i) {
        pv = i;
    }

    /**
     * Preia lista de obiecte de tip Product din tabelul "Product" din baza de date
     * @return Lista de obiecte de tipul Product
     */
    public static ProductDAO vizualizare() {
        try {
            products = ProductDAO.unmarshal();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    /**
     * Adauga in lista de obiecte de tip Product un produs creat din campurile id, denumire, pret, cantitate din clasa ProductVIew
     * @throws SQLException
     */
    public void addProduct() throws  SQLException {
        Product p = new Product(pv.getIdVP(), pv.getdenumireV(), pv.getPretV(), pv.getCantitateV());
        ProductDAO.adaugareProdus(p);
    }

    /**
     * Sterge un rand selectat din tabel
     * @throws SQLException
     */
    public void deleteProduct() throws SQLException {
        String[] s = pv.getInformatiiRand();
        Product p = new Product(Integer.parseInt(s[0]), s[1], Float.parseFloat(s[2]), Integer.parseInt(s[3]));
        ProductDAO.stergereProdus(p);
    }

    /**
     * Actualizeaza un rand selectat din tabel cu valorile introduse in campurile id, denumire, pret, cantitate din clasa ProductVIew
     * @throws SQLException
     */
    public void updateProduct() throws SQLException {
        String[] s = pv.getInformatiiRand();
        Product p1 = new Product(Integer.parseInt(s[0]), s[1], Float.parseFloat(s[2]), Integer.parseInt(s[3]));
        Product p2 = new Product(pv.getIdVP(), pv.getdenumireV(), pv.getPretV(), pv.getCantitateV());
        ProductDAO.updateProdus(p1, p2);
    }

    /**
     * Furnizeaza campul "data" penntru JTable in interfata grafica
     * @param productList Lista de obiecte de tip Product
     * @return campul data
     */
    public Object[][] creareTabel(ProductDAO productList) {
        int i = 0;
        int size = productList.getProduse().size();
        Object[][] data = new Object[size][];
        for(Product p : productList.getProduse()) {
            data[i] = new Object[] {
                    p.getProductId(),
                    p.getDenumire(),
                    p.getPret(),
                    p.getCantitate()};
            i++;
        }
        return data;
    }
}