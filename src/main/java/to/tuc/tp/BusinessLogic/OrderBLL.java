package to.tuc.tp.BusinessLogic;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import to.tuc.tp.DataAccess.ClientDAO;
import to.tuc.tp.DataAccess.OrderDAO;
import to.tuc.tp.DataAccess.ProductDAO;
import to.tuc.tp.Model.Client;
import to.tuc.tp.Model.Order;
import to.tuc.tp.Model.Product;
import to.tuc.tp.Presentation.IOrderView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Furnizeaza metode pentru interactionarea dintre lista de comenzi si interfata grafica a utilizatorului
 */
public class OrderBLL {
    /**
     * Obiect de tipul IOrderView
     */
    private IOrderView ov;

    /**
     * Lista de comenzi
     */
    private static OrderDAO orders = new OrderDAO();

    /**
     * Creaza un obiect instanta a clasei OrderBLL
     * @param i Obiect de tipul IOrderView
     */
    public OrderBLL (IOrderView i) {
        ov = i;
    }

    /**
     * Preia lista de obiecte de tip Order din tabelul "Order" din baza de date
     * @return lista de obiecte de tipul Order
     */
    public static OrderDAO vizualizare() {
        try {
            orders = OrderDAO.unmarshal();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * Adauga in tabelul "Order" comanda creata dupa ID-ul clientului, ID-ul produsului si cantiate
     * @param idClient ID-ul clientului pentru care se face comanda
     * @param idProduct ID-ul produsului din comanda
     * @param quantity Cantitatea de produs din comanda
     * @throws SQLException
     */
    public void addOrder(Integer idClient, Integer idProduct, Integer quantity) throws SQLException {
        Integer max = -1;
        Float price = 0F;
        boolean cantitateDisponibila = false;

        OrderDAO orderList = OrderDAO.unmarshal();
        for(Order o : orderList.getOrders()) {
            if(o.getOrderId() > max) {
                max = o.getOrderId();
            }
        }

        ProductDAO productList = ProductDAO.unmarshal();
        for(Product p : productList.getProduse()) {
            if(Objects.equals(p.getProductId(), idProduct)) {
                price = p.getPret();
                if(p.getCantitate() >= quantity) {
                    ProductDAO.actualizareCantitate(p, quantity);
                    cantitateDisponibila = true;
                }
            }
        }

        if(cantitateDisponibila) {
            Order order = new Order(max + 1, idClient, idProduct, quantity, price * quantity);
            OrderDAO.adaugareComanda(order);
        } else {
            ov.warningCantitate();
        }
    }

    /**
     * Creaza o factura pentru o comanda selectata in interfata grafica
     * @throws FileNotFoundException
     * @throws DocumentException
     * @throws SQLException
     */
    public void orderBill() throws FileNotFoundException, DocumentException, SQLException {
        String[] s = ov.getInformatiiRand();
        Order order = new Order(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]), Float.parseFloat(s[4]));

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(order.getOrderId() + "_Bill.pdf"));

        document.open();
        com.itextpdf.text.Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk orderID = new Chunk(String.valueOf(order.getOrderId()), font);
        Chunk cantitate = new Chunk(String.valueOf(order.getQuantity()), font);
        Chunk pretTotal = new Chunk(String.valueOf(order.getPretTotal()), font);
        Chunk nume = new Chunk("");
        Chunk prenume = new Chunk("");
        Chunk adresa = new Chunk("");
        Chunk produs = new Chunk("");
        Chunk pret = new Chunk("");

        ClientDAO clients = ClientDAO.unmarshal();
        for(Client c : clients.getClienti()) {
            if(Objects.equals(c.getClientId(), order.getClientId())) {
                nume = new Chunk(c.getNume());
                prenume = new Chunk(c.getPrenume());
                adresa = new Chunk(c.getAdresa());
            }
        }

        ProductDAO products = ProductDAO.unmarshal();
        for(Product p : products.getProduse()) {
            if(Objects.equals(p.getProductId(), order.getProductId())) {
                produs = new Chunk(p.getDenumire());
                pret = new Chunk(String.valueOf(p.getPret()));
            }
        }

        document.add(new Paragraph("ID Comanda: " + orderID));
        document.add(new Paragraph("Nume: " + nume));
        document.add(new Paragraph("Prenume: " + prenume));
        document.add(new Paragraph("Adresa: " + adresa));
        document.add(new Paragraph("Produs: " + produs));
        document.add(new Paragraph("Pret: " + pret));
        document.add(new Paragraph("Cantitate: " + cantitate));
        document.add(new Paragraph("Pret toal: " + pretTotal));
        document.close();
    }

    /**
     * Furnizeaza campul "data" penntru JTable in interfata grafica
     * @param orderList Lista de obiecte de tip Order
     * @return campul data
     */
    public Object[][] creareTabel(OrderDAO orderList) {
        int i = 0;
        int size = orderList.getOrders().size();
        Object[][] data = new Object[size][];
        for(Order o : orderList.getOrders()) {
            data[i] = new Object[] {
                    o.getOrderId(),
                    o.getClientId(),
                    o.getProductId(),
                    o.getQuantity(),
                    o.getPretTotal()};
            i++;
        }
        return data;
    }
}
