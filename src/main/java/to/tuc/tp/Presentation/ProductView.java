package to.tuc.tp.Presentation;

import to.tuc.tp.BusinessLogic.ProductBLL;
import to.tuc.tp.DataAccess.ProductDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Creaza fereastra pentru Produse a interfetei grafice.
 * Prin intermediul acesteia putem vedea tabelul Product si putem efectua operatii CRUD asupra tabelului.
 */
public class ProductView implements IProductView{
    ProductBLL pdao;
    private JFrame frame = new JFrame();
    private JTable table;
    private JScrollPane sp;
    private JPanel panel = new JPanel();
    private JTextField denumire = new JTextField();
    private JTextField pret = new JTextField();
    private JTextField id = new JTextField();
    private JTextField cantitate = new JTextField();
    private JLabel l1 = new JLabel("Id:");
    private JLabel l2 = new JLabel("Denumire:");
    private JLabel l3 = new JLabel("Pret:");
    private JLabel l4 = new JLabel("Cantitate:");
    private JButton b1 = new JButton("Adaugare");
    private JButton b2 = new JButton("Stergere");
    private JButton b4 = new JButton("Vizualizare");
    private JButton b3 = new JButton("Actualizare");

    /**
     * Furnizeaza informatiile despre un rand selectat din tabel intr-ul sir de String-uri
     * @return sir de String-uri
     */
    public String[] getInformatiiRand() {
        String[] valori = new String[4];
        int randSelectat = table.getSelectedRow();
        for (int i = 0; i < table.getColumnCount(); i++) {
            valori[i] = String.valueOf(table.getValueAt(randSelectat, i));
        }
        return valori;
    }

    /**
     * Creaza un obiect instanta a clasei ProductView
     */
    public ProductView() {
        pdao = new ProductBLL(this);
        frame.setTitle("Product");
        frame.setBounds(300, 90, 1400, 600);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());

        panel.setLayout(new FlowLayout());
        frame.add(panel);

        l1.setFont(new Font("Arial", Font.PLAIN, 16));
        l1.setPreferredSize(new Dimension(325, 20));

        l2.setFont(new Font("Arial", Font.PLAIN, 16));
        l2.setPreferredSize(new Dimension(325, 20));

        l3.setFont(new Font("Arial", Font.PLAIN, 16));
        l3.setPreferredSize(new Dimension(325, 20));

        l4.setFont(new Font("Arial", Font.PLAIN, 16));
        l4.setPreferredSize(new Dimension(325, 20));

        id.setFont(new Font("Arial", Font.PLAIN, 14));
        id.setPreferredSize(new Dimension(325, 20));

        denumire.setFont(new Font("Arial", Font.PLAIN, 14));
        denumire.setPreferredSize(new Dimension(325, 20));

        pret.setFont(new Font("Arial", Font.PLAIN, 14));
        pret.setPreferredSize(new Dimension(325, 20));

        cantitate.setFont(new Font("Arial", Font.PLAIN, 14));
        cantitate.setPreferredSize(new Dimension(325, 20));

        b1.setFont(new Font("Arial", Font.PLAIN, 14));
        b1.setPreferredSize(new Dimension(325, 30));

        b2.setFont(new Font("Arial", Font.PLAIN, 14));
        b2.setPreferredSize(new Dimension(325, 30));

        b3.setFont(new Font("Arial", Font.PLAIN, 14));
        b3.setPreferredSize(new Dimension(325, 30));

        b4.setFont(new Font("Arial", Font.PLAIN, 14));
        b4.setPreferredSize(new Dimension(325, 30));

        frame.add(l1);
        frame.add(l2);
        frame.add(l3);
        frame.add(l4);

        frame.add(id);
        frame.add(denumire);
        frame.add(pret);
        frame.add(cantitate);

        frame.add(b1);
        frame.add(b2);
        frame.add(b3);
        frame.add(b4);

        frame.setVisible(true);

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    pdao.addProduct();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    pdao.deleteProduct();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    pdao.updateProduct();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                frame.setVisible(false);
                frame.dispose();
                ProductView v = new ProductView();
                v.TabelProduse(ProductBLL.vizualizare());
            }
        });

    }

    /**
     * Creaza tabelul "Product" in interfata grafica intr-o lista de obiecte de tip Product
     * @param productList lista de obiecte ce vor fi afisate in tabel
     */
    public void TabelProduse(ProductDAO productList) {
        String[] columnNames = {"Id", "Denumire", "Pret", "Cantitate"};
        table = new JTable(pdao.creareTabel(productList), columnNames);
        table.setFillsViewportHeight(true);
        sp = new JScrollPane(table);
        panel.add(sp);
        sp.setPreferredSize(new Dimension(1300, 300));
        frame.setVisible(true);
    }

    /**
     * Returneaza String-ul din JTextField-ul denumire din interfata grafica
     * @return denumirea produslui
     */
    public String getdenumireV() {
        return denumire.getText();
    }

    /**
     * Returneaza valoarea din JTextField-ul cantiate din interfata grafica
     * @return Cantitatea din stock a produsului
     */
    public Integer getCantitateV() {
        return Integer.parseInt(cantitate.getText());
    }

    /**
     * Returneaza valoarea din JTextField-ul id din interfata grafica
     * @return ID-ul produsului
     */
    public Integer getIdVP() {
        return Integer.parseInt(id.getText());
    }

    /**
     * Returneaza valoarea din JTextField-ul pret din interfata grafica
     * @return Pretul produsului
     */
    public Float getPretV() {
        return Float.parseFloat(pret.getText());
    }

    public static void main( String[] args ) {
        ProductView v = new ProductView();
    }
}
