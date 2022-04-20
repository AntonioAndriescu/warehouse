package to.tuc.tp.Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Creaza fereastra de selectare a interfetei grafice.
 * Prin intermediul acesteia putem selecta de fereastra din ClientView, OrderVie sau OrderView dorim sa deschidem.
 */
public class SelectView {
    private JFrame frame = new JFrame();
    private JButton client = new JButton("Clienti");
    private JButton product = new JButton("Produse");
    private JButton order = new JButton("Comenzi");
    private Container x = new Container();

    /**
     * Creaza un obiect instanta a clasei SelectView
     */
    public SelectView() {
        frame.setTitle("SelectView");
        frame.setBounds(800, 350, 320, 220);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        x = frame.getContentPane();
        x.setLayout(null);

        client.setFont(new Font("Arial", Font.PLAIN, 14));
        client.setSize(150, 30);
        client.setLocation(82, 20);

        product.setFont(new Font("Arial", Font.PLAIN, 14));
        product.setSize(150, 30);
        product.setLocation(82, 70);

        order.setFont(new Font("Arial", Font.PLAIN, 14));
        order.setSize(150, 30);
        order.setLocation(82, 120);

        x.add(client);
        x.add(product);
        x.add(order);

        frame.setVisible(true);

        client.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ClientView v = new ClientView();
            }
        });

        product.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ProductView v = new ProductView();
            }
        });

        order.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                OrderView v = new OrderView();
            }
        });
    }

    public static void main( String[] args )
    {
        SelectView v = new SelectView();
    }
}
