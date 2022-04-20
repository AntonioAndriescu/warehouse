package to.tuc.tp.Presentation;

import to.tuc.tp.BusinessLogic.ClientBLL;
import to.tuc.tp.DataAccess.ClientDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Creaza fereastra pentru Clienti a interfetei grafice.
 * Prin intermediul acesteia putem vedea tabelul Client si putem efectua operatii CRUD asupra tabelului.
 */
public class ClientView implements IClientView{
    ClientBLL cdao;
    private JFrame frame = new JFrame();
    private JTable table;
    private JScrollPane sp;
    private JPanel panel = new JPanel();
    private JTextField nume = new JTextField();
    private JTextField prenume = new JTextField();
    private JTextField id = new JTextField();
    private JTextField adresa = new JTextField();
    private JLabel l1 = new JLabel("Id:");
    private JLabel l2 = new JLabel("Nume:");
    private JLabel l3 = new JLabel("Prenume:");
    private JLabel l4 = new JLabel("Adresa:");
    private JButton b1 = new JButton("Adaugare");
    private JButton b2 = new JButton("Stergere");
    private JButton b3 = new JButton("Actualizare");
    private JButton b4 = new JButton("Vizualizare");

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
     * Creaza un obiect instanta a clasei ClientView
     */
    public ClientView() {
        cdao = new ClientBLL(this);
        frame.setTitle("Client");
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

        nume.setFont(new Font("Arial", Font.PLAIN, 14));
        nume.setPreferredSize(new Dimension(325, 20));

        prenume.setFont(new Font("Arial", Font.PLAIN, 14));
        prenume.setPreferredSize(new Dimension(325, 20));

        adresa.setFont(new Font("Arial", Font.PLAIN, 14));
        adresa.setPreferredSize(new Dimension(325, 20));

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
        frame.add(nume);
        frame.add(prenume);
        frame.add(adresa);

        frame.add(b1);
        frame.add(b2);
        frame.add(b3);
        frame.add(b4);

        frame.setVisible(true);

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    cdao.addClient();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    cdao.deleteClient();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    cdao.updateClient();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                frame.setVisible(false);
                frame.dispose();
                ClientView v = new ClientView();
                v.TabelClienti(ClientBLL.vizualizare());
            }
        });

    }

    /**
     * Creaza tabelul "Client" in interfata grafica intr-o lista de obiecte de tip Client
     * @param clientList lista de obiecte ce vor fi afisate in tabel
     */
    public void TabelClienti(ClientDAO clientList) {
        String[] columnNames = {"ClientId", "Nume", "Prenume", "Adresa"};
        table = new JTable(cdao.creareTabel(clientList), columnNames);
        table.setFillsViewportHeight(true);
        sp = new JScrollPane(table);
        panel.add(sp);
        sp.setPreferredSize(new Dimension(1300, 300));
        frame.setVisible(true);
    }

    /**
     * Returneaza String-ul din JTextField-ul nume din interfata grafica
     * @return Numele clientului
     */
    public String getNumeV() {
        return nume.getText();
    }

    /**
     * Returneaza String-ul din JTextField-ul prenume din interfata grafica
     * @return Prenumele clientului
     */
    public String getPrenumeV() {
        return prenume.getText();
    }

    /**
     * Returneaza String-ul din JTextField-ul id din interfata grafica
     * @return ID-ul clientului
     */
    public Integer getIdV() {
        return Integer.parseInt(id.getText());
    }

    /**
     * Returneaza String-ul din JTextField-ul adresa din interfata grafica
     * @return Adresa clientului
     */
    public String getAdresaV() {
        return adresa.getText();
    }

    public static void main(String[] args ) {
        ClientView v = new ClientView();
    }
}
