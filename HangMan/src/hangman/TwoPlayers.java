/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman;

import static hangman.HomeScreen.PlayMusic;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import sun.security.util.Password;

public class TwoPlayers extends JFrame implements ActionListener {

    private boolean p1Check = false, p2Check = false;
    private JButton scores;
    private JButton save;
    private JPanel twoPlayers;
    private JButton home;
    private boolean[] check;
    private int count1, count2;
    private JPanel players;
    private JRadioButton p1, p2;
    private ButtonGroup p;
    private JButton exitBtn;
    private JButton nextBtn;
    private boolean end;
    private int successNum;
    private int failNum;
    private char tempChar[];
    private String tempStr;
    private char y[];
    private JLabel word;
    private String checkWord;
    private char x = 65;
    private JLabel lbl1, lbl2, p1_score, p2_score, p1_name, p2_name, head;
    private TextField word_txtf, hint_txtf, name1_txtf, name2_txtf;
    private JButton btn1;
    private Font tfnt, lfnt, headFont;
    private JPanel buttons;
    private JButton[] letters = new JButton[26];

    //connection vars
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;

    public TwoPlayers() {

        //Establisih conection
        // con = SQliteConnection.connect();
        //Establisih Form
        this.setTitle("Hang Man");
        this.setSize(1000, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        //Constructing Components
        headFont = new Font("Font", Font.BOLD, 40);
        head = new JLabel("Hang Man");
        head.setFont(headFont);

        scores = new JButton("Scores");
        save = new JButton("Save Score");
        p1_name = new JLabel("Player 1 Name");
        p2_name = new JLabel("Player 2 Name");
        name1_txtf = new TextField();
        name2_txtf = new TextField();
        name1_txtf.setText("");
        name2_txtf.setText("");
        twoPlayers = new JPanel();
        home = new JButton("Home");
        count1 = 0;
        count2 = 0;
        check = new boolean[2];
        check[0] = false;
        check[1] = false;
        p1_score = new JLabel("Player 1 score :  " + 0);
        p2_score = new JLabel("Player 2 score :  " + 0);
        players = new JPanel();
        p = new ButtonGroup();
        p1 = new JRadioButton("Player 1");
        p2 = new JRadioButton("Player 2");
        tempChar = new char[1];
        lbl1 = new JLabel("Enter The Word");
        word_txtf = new TextField();
        lbl2 = new JLabel("Add Hint *");
        hint_txtf = new TextField();
        btn1 = new JButton("Done");
        tfnt = new Font("Font", NORMAL, 20);
        lfnt = new Font("Font", 100, 13);
        word = new JLabel();
        buttons = new JPanel();
        nextBtn = new JButton("Play Again");
        exitBtn = new JButton("Exit");
        successNum = 0;
        failNum = 0;
        end = false;
        //establish buttons

        //Establish Components
        head.setBounds(375, 30, 250, 50);
        scores.setBounds(400, 560, 150, 50);
        save.setBounds(800, 500, 150, 50);
        save.show(false);
        p1_name.setBounds(50, 120, 100, 20);
        p2_name.setBounds(50, 170, 100, 20);
        name1_txtf.setBounds(150, 120, 100, 20);
        name2_txtf.setBounds(150, 170, 100, 20);
        twoPlayers.setBounds(0, 0, 1000, 670);
        home.setBounds(40, 500, 150, 50);
        home.setBackground(Color.white);
        p1_score.setBounds(100, 300, 150, 20);
        p2_score.setBounds(440, 300, 150, 20);
        players.setBounds(50, 220, 205, 60);
        p1.setBounds(130, 120, 100, 20);
        p2.setBounds(230, 120, 100, 20);
        word.setBounds(290, 390, 200, 20);
        lbl1.setBounds(755, 100, 100, 10);
        lbl1.setFont(lfnt);
        word_txtf.setBounds(650, 120, 300, 30);
        word_txtf.setFont(tfnt);
        word_txtf.setText("");
        word_txtf.setEchoChar('*');
        lbl2.setBounds(770, 170, 60, 10);
        lbl2.setFont(lfnt);
        hint_txtf.setBounds(650, 190, 300, 30);
        btn1.setBounds(750, 234, 100, 50);
        hint_txtf.setFont(tfnt);
        buttons.setBounds(650, 300, 290, 190);
        nextBtn.setBounds(800, 560, 150, 50);
        exitBtn.setBounds(40, 560, 150, 50);
        buttons.setBorder(new TitledBorder("Guess from letters"));
        players.setBorder(new TitledBorder("Player who enter word"));
        //Adding letters
        this.addbtns();
        //Adding Components
        this.add(head);
        this.add(scores);
        this.add(p1_name);
        this.add(p2_name);
        this.add(name1_txtf);
        this.add(name2_txtf);
        this.add(home);
        this.add(p1_score);
        this.add(p2_score);
        p.add(p1);
        p.add(p2);
        players.add(p1);
        players.add(p2);
        this.add(players);
        this.add(nextBtn);
        nextBtn.show(false);
        this.add(exitBtn);
        this.add(word);
        this.add(lbl1);
        this.add(lbl2);
        this.add(word_txtf);
        this.add(hint_txtf);
        this.add(btn1);
        this.add(buttons);
        //twoPlayers.setBackground(Color.pink);
        this.add(twoPlayers);
        //Set Actions
        scores.addActionListener(this);
        save.addActionListener(this);
        home.addActionListener(this);
        p1.addActionListener(this);
        p2.addActionListener(this);
        nextBtn.addActionListener(this);
        exitBtn.addActionListener(this);
        for (int i = 0; i < letters.length; i++) {
            letters[i].addActionListener(this);
        }
        btn1.addActionListener(this);
    }

    public void addbtns() {
        for (int i = 0; i < letters.length; i++) {
            letters[i] = new JButton("" + x);
            buttons.add(letters[i]);
            x++;

        }
    }

    public void write() throws Exception {
        File file = new File("twoPlayers.txt");
        FileWriter fileWriter = new FileWriter(file, true);
        BufferedWriter writer = null;
        writer = new BufferedWriter(fileWriter);
        PrintWriter printWriter = new PrintWriter(writer);
        printWriter.println("\n" + name1_txtf.getText() + "   :        " + count1 + "         " + name2_txtf.getText() + "   :        " + count2);
        printWriter.close();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn1) {
            PlayMusic("Music\\\\Click.wav");
            if (word_txtf.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "enter word");
            } else {
                btn1.show(false);
                word_txtf.enable(false);
                checkWord = word_txtf.getText().toUpperCase();
                y = new char[checkWord.length() * 2];
                for (int i = 0; i < checkWord.length() * 2; i++) {
                    y[i] = ' ';
                    y[++i] = '_';
                }
                tempStr = String.valueOf(y);
                word.setText(tempStr);
            }
        } else if (e.getSource() == p1) {
            p1Check = true;
            PlayMusic("Music\\\\Click.wav");
            check[0] = true;
            p2.show(false);
        } else if (e.getSource() == p2) {
            p2Check = true;
            PlayMusic("Music\\\\Click.wav");
            check[1] = true;
            p1.show(false);
        } else if (e.getSource() == home) {
            PlayMusic("Music\\\\Click.wav");
            if ((!name1_txtf.getText().equals("") && !name2_txtf.getText().equals("")) && (p1Check || p2Check) && (!word_txtf.getText().equals(""))) {
                int p = JOptionPane.showConfirmDialog(this, "Do you want to save score ", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (p == 0) {
                    try {
                        write();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, ex);
                    }
                }
            }
            this.dispose();
            new HomeScreen().setVisible(true);
        } //            if((name1_txtf.getText()=="")||(name2_txtf.getText()==""))
        //            {
        //                    JOptionPane.showMessageDialog(this,"Enter all players names");
        //                    return;
        //            }
        //            save.show(false);
        //            try {
        //                String query = "INSERT INTO players  VALUES ('"+name1_txtf.getText()+"','"+count1+"','"+name2_txtf.getText()+"','"+count2+"')";
        //                pst = con.prepareStatement(query);
        //                pst.execute();
        //            } catch (Exception ea) {
        //                JOptionPane.showMessageDialog(this, e);
        //            } finally {
        //                try {
        //                    pst.close();
        //                } catch (Exception ea) {
        //                    JOptionPane.showMessageDialog(this, ea);
        //                }
        //            }
        else if (e.getSource() == scores) {
            PlayMusic("Music\\\\Click.wav");
            try {
                new PlayersScore().setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "error!");
            }
        } //***********************************************************
        else if (e.getSource() == exitBtn) {
            PlayMusic("Music\\\\Click.wav");
            if ((!name1_txtf.getText().equals("") && !name2_txtf.getText().equals("")) && (p1Check || p2Check) && (!word_txtf.getText().equals(""))) {
                int p = JOptionPane.showConfirmDialog(this, "Do you want to save score ", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (p == 0) {
                    try {
                        write();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, ex);
                    }
                }
            }
            System.exit(0);
        } else if (e.getSource() == nextBtn) {
            PlayMusic("Music\\\\Click.wav");
            if ((!name1_txtf.getText().equals("") && !name2_txtf.getText().equals("")) && (p1Check || p2Check) && (!word_txtf.getText().equals(""))) {
                int p = JOptionPane.showConfirmDialog(this, "Do you want to save score ", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (p == 0) {
                    try {
                        write();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, ex);
                    }
                }
            }
            this.dispose();
            new TwoPlayers().setVisible(true);
        } else {
            try {
                for (int i = 0; i < 26; i++) {
                    if (e.getSource() == letters[i]) {
                        PlayMusic("Music\\\\Click.wav");
                        letters[i].show(false);
                        if ((checkWord.indexOf(letters[i].getText()) != -1)) {
                            tempChar = letters[i].getText().toCharArray();
                            for (int j = 0; j < checkWord.length(); j++) {
                                if (tempChar[0] == checkWord.charAt(j)) {
                                    y[(1 + 2 * j)] = tempChar[0];
                                }
                            }

                            tempStr = String.valueOf(y);
                            word.setText(tempStr);
                            successNum++;
                            if (check[0]) {
                                count2++;
                                p2_score.setText("Player 2 score :  " + count2);
                            } else if (check[1]) {
                                count1++;
                                p1_score.setText("Player 1 score :  " + count1);
                            }
                            for (int j = 0; j < checkWord.length() * 2; j++) {
                                if (y[j] == '_') {
                                    end = false;
                                    break;
                                } else {
                                    end = true;
                                }
                            }
                            if (end) {
                                if (count1 == 0 && count2 == 0) {
                                    JOptionPane.showMessageDialog(this, "Good Guess !");
                                } else if (count1 == count2) {
                                    JOptionPane.showMessageDialog(this, "Draw");
                                    save.show(true);
                                } else if (count1 > count2) {
                                    JOptionPane.showMessageDialog(this, "Player 1 is winner !");
                                    save.show(true);
                                } else if (count1 < count2) {
                                    JOptionPane.showMessageDialog(this, "Player 2 is winner !");
                                    save.show(true);
                                }
                                for (int j = 0; j < 26; j++) {
                                    letters[j].show(false);
                                }
                                nextBtn.show(true);
                            }
                        } else {
                            failNum++;
                            if (check[0]) {
                                count1++;
                                p1_score.setText("Player 1 score :  " + count1);
                            } else if (check[1]) {
                                count2++;
                                p2_score.setText("Player 2 score :  " + count2);
                            }
                            if (failNum == 5) {
                                if (count1 == 0 && count2 == 0) {
                                    JOptionPane.showMessageDialog(this, "Bad Guess !");
                                } else if (count1 == count2) {
                                    JOptionPane.showMessageDialog(this, "Draw");
                                    save.show(true);
                                } else if (count1 > count2) {
                                    JOptionPane.showMessageDialog(this, "Player 1 is winner !");
                                    save.show(true);
                                } else if (count1 < count2) {
                                    JOptionPane.showMessageDialog(this, "Player 2 is winner !");
                                    save.show(true);
                                }
                                for (int j = 0; j < 26; j++) {
                                    letters[j].show(false);
                                }
                                nextBtn.show(true);
                            }
                        }
                    }
                }
            } catch (Exception ea) {

                JOptionPane.showMessageDialog(this, "enter word  " + "يا رئ   !", "ما صباح الفل بقى ", 1);
                for (int j = 0; j < 26; j++) {
                    letters[j].show(true);
                }
            }

        }

    }
}
