package org.project;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


class Panel extends JPanel{

    GridBagLayout layout = new GridBagLayout();
    String welcomeText = "This program lets the user select a .txt file, edit it, and save it to its current location";
    JLabel welcomeLabel = new JLabel(welcomeText);
    JButton selectButton = new JButton("select txt file");
    JLabel fileName = new JLabel("filename.txt");
    JTextArea textArea = new JTextArea("");
    JScrollPane scroll = new JScrollPane(textArea);
    JButton saveButton = new JButton("save");
    File file;

    public Panel(){

        this.setLayout(layout);
        this.textArea.setEditable(false);

        GridBagConstraints wc = new GridBagConstraints();
        wc.gridx = 0;
        wc.gridy = 0;
        wc.gridwidth = 1;
        wc.gridheight = 1;
        wc.anchor = GridBagConstraints.FIRST_LINE_START;
        wc.ipadx = 20;
        wc.ipady = 20;
        this.add(welcomeLabel, wc);

        GridBagConstraints sbc = new GridBagConstraints();
        sbc.gridx = 0;
        sbc.gridy = 1;
        sbc.gridwidth = 1;
        sbc.gridheight = 1;
//        sbc.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(selectButton, sbc);

        GridBagConstraints fnc = new GridBagConstraints();
        fnc.gridx = 1;
        fnc.gridy = 1;
        fnc.gridwidth = 1;
        fnc.gridheight = 1;
        this.add(fileName, fnc);

        GridBagConstraints sc = new GridBagConstraints();
        sc.gridx = 0;
        sc.gridy = 2;
        sc.weightx = 0.7;
        sc.weighty = 0.7;
        sc.fill = GridBagConstraints.BOTH;
        this.add(scroll, sc);

        GridBagConstraints bc = new GridBagConstraints();
        bc.gridx = 0;
        bc.gridy = 3;
        bc.gridwidth = 1;
        bc.gridheight = 1;
        bc.anchor = GridBagConstraints.LAST_LINE_START;
        this.add(saveButton, bc);

        logic();

    }

    public void logic(){
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
                fileChooser.setFileFilter(filter);
                int returnCode = fileChooser.showOpenDialog(selectButton);
                if (returnCode == JFileChooser.APPROVE_OPTION){
                    file = fileChooser.getSelectedFile();
                    fileName.setText(file.getName());
                    String text = "";
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        StringBuilder content = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null){
                            content.append(line).append('\n');
                        }
                        reader.close();
                        text = content.toString();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    textArea.setText(text);
                    textArea.setEditable(true);
                }

            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String text = textArea.getText();
                if (text.isEmpty()){
                    return;
                }
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write(text);
                    writer.flush();
                    writer.close();
                    String title = "Saved";
                    String message = "File "+ file.getName() +" correctly saved";
                    JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}


public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Text Editor");
//        frame.setSize(800, 500);
        frame.setPreferredSize(new Dimension(800, 500));
        frame.setLocation(400, 300);
        Panel panel = new Panel();
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
