import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


public class HTMLRead implements ActionListener {

    private JFrame mainFrame;
    private JPanel topPanel;

    private JPanel bottomPanel;
    private JTextField link;
    private JLabel linkLabel;

    private JTextField searchTerm;
    private JLabel termLabel;

    private JButton enter;

    private JTextArea textArea;

    private JScrollPane scrollPane;
    public String print;

    public static void main(String[] args) {
        HTMLRead html = new HTMLRead();
    }

    public HTMLRead(){
        GUI();

    }

    public String Search() {
        String linklist = "";
        try {
            URL url = new URL(link.getText());
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("<a href")) {
                    int index = line.indexOf("<a href");
                    String temp = line.substring(index);
                    int tIndex = temp.indexOf("<a href");
                    int end = temp.indexOf(">");
                    String newSearchTerm = searchTerm.getText();
                    if (temp.substring(tIndex, end).contains(newSearchTerm)) {
                        System.out.println(temp.substring((tIndex)+8, end));
                        linklist = linklist + temp.substring((tIndex)+8, end)+"\n";
                    }

                }

            }
            reader.close();

        } catch (Exception e) {
            System.out.println(e);

        }
        return linklist;
    }

    public void GUI(){
        mainFrame = new JFrame();

        topPanel = new JPanel();
        topPanel.setBackground(Color.orange);

        linkLabel = new JLabel("Link:");
        linkLabel.setForeground(Color.white);
        link = new JTextField();

        termLabel = new JLabel("Search Term:");
        searchTerm = new JTextField();
        termLabel.setForeground(Color.WHITE);


        enter = new JButton("Enter");
        enter.setForeground(Color.orange);
        enter.addActionListener(this);
        enter.setPreferredSize(new Dimension(60,70));

        bottomPanel = new JPanel();
        textArea = new JTextArea();
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(topPanel,BorderLayout.NORTH);

        topPanel.setLayout(new GridLayout());
        topPanel.add(linkLabel);
        topPanel.add(link);
        topPanel.add(termLabel);
        topPanel.add(searchTerm);
        topPanel.add(enter);

        mainFrame.add(bottomPanel,BorderLayout.CENTER);
        bottomPanel.setLayout(new BorderLayout());

        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        mainFrame.setSize(500,700);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        Object buttonClicked = e.getSource();

        if (buttonClicked == enter){
            textArea.setText(Search());

        }

    }
}
