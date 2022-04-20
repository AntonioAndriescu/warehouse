package to.tuc.tp.Presentation;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import to.tuc.tp.BusinessLogic.OrderBLL;
import to.tuc.tp.DataAccess.ClientDAO;
import to.tuc.tp.DataAccess.OrderDAO;
import to.tuc.tp.DataAccess.ProductDAO;
import to.tuc.tp.Model.Client;
import to.tuc.tp.Model.Order;
import to.tuc.tp.Model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Creaza fereastra pentru comenzi a interfetei grafice.
 * Prin intermediul acesteia putem vedea tabelul "Orders" si putem adauga comenzi noi.
 */
public class OrderView  implements IOrderView {
    OrderBLL odll;
    private JFrame frame = new JFrame();
    private JTable table;
    private JScrollPane sp;
    private JPanel panel = new JPanel();
    private JTextField idClient = new JTextField();
    private JTextField idProdus = new JTextField();
    private JTextField cantitate = new JTextField();
    private JLabel l1 = new JLabel("IdClient:");
    private JLabel l2 = new JLabel("IdProdus:");
    private JLabel l3 = new JLabel("Cantitate:");
    private JButton b1 = new JButton("Adaugare Comanda");
    private JButton b2 = new JButton("Vizualizare");
    private JButton b3 = new JButton("Creare raport");

    /**
     * Furnizeaza informatiile despre un rand selectat din tabel intr-ul sir de String-uri
     * @return sir de String-uri
     */
    public String[] getInformatiiRand() {
        String[] valori = new String[7];
        int randSelectat = table.getSelectedRow();
        for (int i = 0; i < table.getColumnCount(); i++) {
            valori[i] = String.valueOf(table.getValueAt(randSelectat, i));
        }
        return valori;
    }

    /**
     * Creaza un obiect instanta a clasei OrderView
     */
    public OrderView() {
        odll = new OrderBLL(this);
        frame.setTitle("Order");
        frame.setBounds(300, 90, 1400, 600);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());

        panel.setLayout(new FlowLayout());
        frame.add(panel);

        l1.setFont(new Font("Arial", Font.PLAIN, 16));
        l1.setPreferredSize(new Dimension(65, 20));

        l2.setFont(new Font("Arial", Font.PLAIN, 16));
        l2.setPreferredSize(new Dimension(70, 20));

        l3.setFont(new Font("Arial", Font.PLAIN, 16));
        l3.setPreferredSize(new Dimension(70, 20));

        idClient.setFont(new Font("Arial", Font.PLAIN, 14));
        idClient.setPreferredSize(new Dimension(180, 20));

        idProdus.setFont(new Font("Arial", Font.PLAIN, 14));
        idProdus.setPreferredSize(new Dimension(180, 20));

        cantitate.setFont(new Font("Arial", Font.PLAIN, 14));
        cantitate.setPreferredSize(new Dimension(180, 20));

        b1.setFont(new Font("Arial", Font.PLAIN, 14));
        b1.setPreferredSize(new Dimension(290, 30));

        b2.setFont(new Font("Arial", Font.PLAIN, 14));
        b2.setPreferredSize(new Dimension(290, 30));

        b3.setFont(new Font("Arial", Font.PLAIN, 14));
        b3.setPreferredSize(new Dimension(290, 30));

        frame.add(l1);
        frame.add(idClient);
        frame.add(l2);
        frame.add(idProdus);
        frame.add(l3);
        frame.add(cantitate);
        frame.add(b1);
        frame.add(b2);
        frame.add(b3);

        frame.setVisible(true);

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    odll.addOrder(Integer.parseInt(idClient.getText()), Integer.parseInt(idProdus.getText()), Integer.parseInt(cantitate.getText()));

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                frame.setVisible(false);
                frame.dispose();
                OrderView v = new OrderView();
                v.TabelComenzi(OrderBLL.vizualizare());
            }
        });

        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    odll.orderBill();
                } catch (FileNotFoundException | DocumentException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Creaza tabelul "Orders" in interfata grafica intr-o lista de obiecte de tip Order
     * @param orderList lista de obiecte ce vor fi afisate in tabel
     */
    public void TabelComenzi(OrderDAO orderList) {
        String[] columnNames = {"OrderId", "ClientId", "ProductId", "Cantitate", "Pret"};
        table = new JTable(odll.creareTabel(orderList), columnNames);
        table.setFillsViewportHeight(true);
        sp = new JScrollPane(table);
        panel.add(sp);
        sp.setPreferredSize(new Dimension(1300, 300));
        frame.setVisible(true);
    }

    /**
     * Afiseaza un warning atunci cand se incearca adaugarea unei cantitati mai mari de produs, intr-o comanda, decat este in stock
     */
    public void warningCantitate() {
        JOptionPane.showMessageDialog(frame, "Cantitate indisponibila");
    }

    /**
     * Returneaza valoarea din JTextField-ul idClient din interfata grafic
     * @return ID-ul clientului
     */
    public Integer getIdClient() {
        return Integer.parseInt(idClient.getText());
    }

    /**
     * Returneaza valoarea din JTextField-ul idProdus din interfata grafica
     * @return ID-ul produsului
     */
    public Integer getIdProdus() {
        return Integer.parseInt(idProdus.getText());
    }

    public static void main( String[] args ) {
        ProductView v = new ProductView();
    }
}
