import java.awt.*;
import javax.swing.*;

public class Menu extends JFrame{
    private JFrame mainWindow;
    private JPanel mainLayout;
    private JLabel rubrik;
    private JTextArea txtDisp;
    private JScrollPane txtScroll;
    public static void main(String[] args) {
        Menu menuObject = new Menu();
        menuObject.loadMenu();
    }
    public void loadMenu() {
        mainWindow = new JFrame("Sakregister");
        mainWindow.setSize(800, 800);
        mainWindow.setLayout(new BorderLayout());
        
        //rubrik
        rubrik = new JLabel("Värdesaker");
        mainWindow.add(rubrik, BorderLayout.PAGE_START);
        
        //mainText area.
        txtDisp = new JTextArea();
        txtDisp.setEditable(false);
        txtDisp.setText("some text in here..\nIs this on a new line?");
        txtScroll = new JScrollPane(txtDisp);
        mainWindow.add(txtScroll, BorderLayout.CENTER);
       
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        mainWindow.add(right, BorderLayout.EAST);
        right.add(Box.createVerticalGlue());
        right.add(new JLabel("Sortering"));
        JRadioButton allaKnapp = new JRadioButton("Namn", true);
        right.add(allaKnapp);
        JRadioButton ivrigaKnapp = new JRadioButton("Värde");
        right.add(ivrigaKnapp);
        ButtonGroup bg = new ButtonGroup();
        bg.add(allaKnapp);
        bg.add(ivrigaKnapp);
        
        JPanel down = new JPanel();
        down.setLayout(new BoxLayout(down, BoxLayout.X_AXIS));
        mainWindow.add(down, BorderLayout.SOUTH);
        String[] thePossibilities = { "Smycke", "Apparat", "Aktie" };
        JComboBox myComboBox = new JComboBox(thePossibilities);
        myComboBox.setSelectedIndex(0);
        myComboBox.addActionListener(e-> {
            if(myComboBox.getSelectedItem().equals("Smycke")) nyttSmycke();
            else System.out.println(myComboBox.getSelectedItem());
        });
        down.add(new JLabel("Nytt: "));
        down.add(myComboBox);
        JButton visaKnapp = new JButton("Visa");  // need to add listeners
        JButton borskrashKnapp = new JButton("Börskrash"); // need to add listeners
        down.add(visaKnapp);
        down.add(borskrashKnapp);
        
        mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
    }
    public void nyttSmycke() {
        
    }
}
