package hangman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.*;


public class PlayersScore extends JFrame {
    private JList list;
    private DefaultListModel<String> model;
    private String str;
    public PlayersScore() throws Exception {
        
        
        //establish form 
        this.setTitle("Hang Man");
        this.setSize(500, 600);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
        
        list=new JList();
        model=new DefaultListModel<>();
       
        list.setBounds(42, 50, 400, 455);
       
        write();
       
        this.add(list);
}
     public  void write() throws Exception {
        
        File file = new File("twoPlayers.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader reader=new BufferedReader(fileReader);
        while(reader.ready())
        {
            str=reader.readLine();
            model.addElement(str);
        }
        list.setModel(model);
    }
}