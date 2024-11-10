package Views;

import javax.swing.*;


public class HomePage extends JFrame {
    private JButton logoutBtn;
    public HomePage() {
        super();
        logoutBtn = new JButton("Logout");
        JLabel lb=new JLabel("Home page");
        JPanel panel = new JPanel();

        panel.add(lb);
        panel.add(logoutBtn);
        add(panel);
        logoutBtn.addActionListener(event->{
            setVisible(false);
        });
    }
    public void init(){

    }
}
