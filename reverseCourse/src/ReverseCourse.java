import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * @author Radosław Mieckowski
 */

public class ReverseCourse extends JFrame {
    private final static int FRAME_WIDTH = 400;
    private final static int FRAME_HEIGHT = 200;
    private int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    private JLabel title;
    private Font font;
    private JPanel titlePanel;
    private JPanel currencyPanel;
    private JPanel checkClearPanel;
    private JTextField currency1;
    private JTextField currency2;
    private JTextField quotation;
    private JButton checkButton;
    private JButton clearButon;
    private JButton loadButton;
    private String linkMatrix= "https://stooq.pl/q/?s=";

    public ReverseCourse() {
        setTitle("Reverse course");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setBounds(screenWidth / 2 - FRAME_WIDTH / 2, screenHeight / 2 - FRAME_HEIGHT / 2, FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        title = new JLabel("Reverse the course", SwingConstants.CENTER);
        font = new Font("SansSerif", Font.BOLD + Font.ITALIC, 16);
        title.setFont(font);
        titlePanel = new JPanel(new GridLayout(2, 1, 5, 10));
        titlePanel.add(title);
        titlePanel.add(new JLabel(""));
        add(titlePanel, BorderLayout.NORTH);
        currencyPanel = new JPanel(new GridLayout(2, 4, 5, 2));
        currencyPanel.add(new JLabel(""));
        currencyPanel.add(currency1 = new JTextField("", 4));
        currency1.setToolTipText("Enter first currency");
        currencyPanel.add(currency2 = new JTextField("", 4));
        currency2.setToolTipText("Enter second currency");
        currencyPanel.add(loadButton=new JButton("Load"));
        loadButton.setToolTipText("If you like,You can load quotations from the Internet");

        loadButton.addActionListener(event->{
            String link=(linkMatrix+currency1.getText()+currency2.getText()).toLowerCase();
            if (currency1.getText().equals("") || currency2.getText().equals(""))
                JOptionPane.showMessageDialog(null, "Currencies' input fields must be filled!", "Error Message 3", JOptionPane.ERROR_MESSAGE);
            else{
                try {
                    String id =("aq_"+currency1.getText()+currency2.getText()+"_c5").toLowerCase();
                    System.out.println(link);
                    System.out.println(id);
                    Document doc = Jsoup.connect(link).get();
                    Element el= doc.getElementById(id);
                    System.out.println(el);
                    quotation.setText(el.text());

                }catch (IOException | NullPointerException e){
                    JOptionPane.showMessageDialog(null,"Either connection failed, or can't find value, or incorrect data in the input fields!","Error message nr. 4",JOptionPane.ERROR_MESSAGE);
                }
            }


        });
        currencyPanel.add(new JLabel(""));
        currencyPanel.add(quotation = new JTextField("", 7));
        quotation.setToolTipText("Enter quotation");
        currencyPanel.add(new JLabel(""));
        add(currencyPanel, BorderLayout.CENTER);
        checkClearPanel = new JPanel(new GridLayout(2,2,10,0));
        checkClearPanel.add(new JLabel(""));
        checkClearPanel.add(new JLabel(""));
        checkClearPanel.add(checkButton = new JButton("Reverse the course"));
        checkButton.addActionListener(event -> {
            if (currency1.getText().equals("") || currency2.getText().equals("") || quotation.getText().equals(""))
                JOptionPane.showMessageDialog(null, "All input fields must be filled!", "Error Message 1", JOptionPane.ERROR_MESSAGE);
            else {
                reverse();
            }
        });
        checkClearPanel.add(clearButon=new JButton("clear"));
        clearButon.addActionListener(event->{
            currency1.setText("");
            currency2.setText("");
            quotation.setText("");
        });
        add(checkClearPanel, BorderLayout.SOUTH);

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ReverseCourse rc = new ReverseCourse();
        });
    }
    public void reverse(){
       try{
               String c1=currency1.getText();
               String c2=currency2.getText();
               currency1.setText(c2);
               currency2.setText(c1);

               double q1=Double.parseDouble(quotation.getText().trim().replace(",","."));
               q1=Math.round(1/q1*100000);
               q1=q1/100000;

               System.out.println(q1);

               quotation.setText(String.valueOf(q1));

       }catch (NumberFormatException e){
           JOptionPane.showMessageDialog(null, "Incorrect input data!", "Error Message 2", JOptionPane.ERROR_MESSAGE);
       }

    }
}
