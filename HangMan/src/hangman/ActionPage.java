package hangman;

import static hangman.HomeScreen.PlayMusic;
import static hangman.HomeScreen.audios;
import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class ActionPage extends JFrame implements ActionListener {
    private Font fnt;
    private int max;  
    private Random r=new Random();
    private String []texts=
    {
    "TIGER","ZEBRA","DOG","GIRAFFE","ELEPHANT","MONKEY","DONKEY","LION",
    "EYE","NOSE","HEAD","FACE","NECK","FINGER","EAR","FOOT",
    "APRIL","MARCH","FEBRUARY","OCTOBER","MAY","JUNE","NOVEMBER","DECEMBER",
    "SHIRT","SKIRT","JACKET","HAT","PANTS","SOCKS","TROUSERS","COAT",
    "WATERMELON","APPLE","MANGO","PEACHES","ORANGE","LEMON","BANANA","PINEAPPLE",
    "TOMATTO","CARROT","POTATTO","BEANS","PEPPER","ONION","CABBAGE","BROCCOLI",
    "TV","TABLE","CHAIR","BED","COUCH","DOOR","WINDOW","FRIDGE", 
    "KEYBOARD","MOUSE","SCANNER","MONITOR","PRINTER","SPEAKER","SCREEN","TOUCHPAD",
    "DOCTOR","ENGINEER","CARPENTER","TEACHER","PILOT","OFFICER","DRIVER","NURSE",
    "TRAIN","CAR","TAXI","BIKE","PLANE","MOTORBIKE","SHIP","BOAT",
    " "," " ," "," "," "," "," "," "
    };
    private String playerName="";
    private JTextField nameTxtf;
    private JButton home,save,scores; 
    private int NumOfLev;
    private ImageIcon i1, i2, i3, i4, i5, i6, i7, i8, i9, i10; 
    private int test = 0; 
    private int sc;  
    private int Min; 
    private int n;
    private int successNum;
    private int failNum = 0;
    private boolean end;
    private String s1; 
    private char[] s2;
    private char[] y;// character array to convert button text in it
    private String s3;
    private JLabel word, Hint, im;
    private char x = 65;//initial value for ascii code to initialize letters
    private JLabel time, score, finish, Level, Cong,name;
    private JPanel buttons, img;
    private JButton[] letters = new JButton[26];
    private JButton exitBtn, nextBtn;
    private int iteratin=0;
    public ActionPage(int max, int min, int levl, int scor,String pName,int iteratin) 
    {
        
        //declaring some variables
        this.iteratin=iteratin;
        playerName=pName;
        this.sc = scor; 
        this.Min = min; 
        this.NumOfLev = levl; 
        this.max = max; 
        n = ThreadLocalRandom.current().nextInt(min, this.max);
        s1 = texts[n];
        //establish form
        this.setTitle("Hang Man");
        this.setSize(1000, 700);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //establish word label
        fnt=new Font("Font", Font.BOLD, 20);
        end = false;
        successNum = 0;
        failNum = 0;
        s2 = new char[(s1.length()*2)];
        for (int i = 0; i < s1.length()*2; i++) {
            s2[i] = ' ';
            s2[++i] = '_';
        }
        s3 = String.valueOf(s2);
        
        y = new char[1];
        //Constructing components
        scores=new JButton("Scores");
        nameTxtf=new JTextField();
        name=new JLabel("Enter Your Name");
        save=new JButton("Save");
        Cong = new JLabel("Congratulations"); 
        home=new JButton("Home");
        word = new JLabel(s3);
        buttons = new JPanel();
        //time = new JLabel("Time : ");
        score = new JLabel("Score : "+sc);
        finish = new JLabel();
        exitBtn = new JButton("Exit");
        nextBtn = new JButton("Next");
        Level = new JLabel("Level"+NumOfLev); 
        Hint = new JLabel();  
        i1 = new ImageIcon(getClass().getResource("1.jpg"));
        i2 = new ImageIcon(getClass().getResource("2.jpg"));
        i3 = new ImageIcon(getClass().getResource("3.jpg"));
        i4 = new ImageIcon(getClass().getResource("4.jpg"));
        i5 = new ImageIcon(getClass().getResource("5.jpg"));
        i6 = new ImageIcon(getClass().getResource("6.jpg"));
        i7 = new ImageIcon(getClass().getResource("7.jpg"));
        i8 = new ImageIcon(getClass().getResource("8.jpg"));
        im = new JLabel(i1);
        img = new JPanel();
        //declaring letters
        for (int i = 0; i < letters.length; i++) {
            letters[i] = new JButton(x + "");
            letters[i].setName("" + x);
            buttons.add(letters[i]);
            x++;
        }

        //establish components
        scores.setBounds(425, 600, 150, 50);
        nameTxtf.setBounds(780, 110, 150, 25);
        nameTxtf.setText(playerName);
        if(iteratin>0)
            nameTxtf.enable(false);
        name.setBounds(660, 110, 150, 20);
        save.setBounds(800, 540, 150, 50);
        home.setBounds(40, 540, 150, 50);
        buttons.setBounds(660, 200, 300, 190);
        img.setBounds(10, 40, 300, 500);
        buttons.setBorder(new TitledBorder("Guess from letters"));
        exitBtn.setBounds(40, 600, 150, 50);
        nextBtn.setBounds(800, 600, 150, 50);
        //time.setBounds(20, 10, 100, 20);
        score.setBounds(20, 10, 100, 20);
        word.setBounds(450, 300, 200, 20);
        Level.setBounds(420, 80, 100, 50);
        Hint.setBounds(660, 170, 500, 20);
        Hint.setForeground(Color.red);
        nextBtn.show(false);
        
        /////
        if(NumOfLev == 1)
            {
                Hint.setText("** HINT: This Word Refers To An Animal **");
            }
        else if(NumOfLev == 2)
            {
                Hint.setText("** HINT: This Word Refers To A Part Of Body **");
            }
        else if(NumOfLev == 3)
            {
                Hint.setText("** HINT: This Word Refers To A Month **");
            }
        else if(NumOfLev == 4)
            {
                Hint.setText("** HINT: This Word Refers To A Piece Of Clothes **");
            }
        else if(NumOfLev == 5)
            {
                Hint.setText("** HINT: This Word Refers To Fruits **");
            }
        else if(NumOfLev == 6)
            {
                Hint.setText("** HINT: This Word Refers To Vegetables **");
            }
        else if(NumOfLev == 7)
            {
                Hint.setText("** HINT: This Word Refers To Home Thing **");                
            }
        else if(NumOfLev == 8)
            {
                Hint.setText("** HINT: This Word Refers To A Computer's Component **");
            }
        else if(NumOfLev == 9)
            {
                Hint.setText("** HINT: This Word Refers To A Job **");
            }
        else if(NumOfLev == 10)
            {
                Hint.setText("** HINT: This Word Refers To Means Of Transport **");
            }
        if(NumOfLev==11)
            {
                fnt.deriveFont(Font.BOLD, 40);
                Level.setText("You Saved The Man");
                Level.setBounds(370, 400, 400, 100);
                Hint.show(false);
                buttons.show(false);
                word.show(false);
                score.setFont(fnt);
                score.setBounds(420, 300, 200, 20);
                Cong.setBounds(280, 340, 400, 100);
                Cong.setForeground(Color.red);
                Cong.setFont(fnt.deriveFont(Font.BOLD,50));
                this.add(Cong); 
            } 
        //set actions
        for (int i = 0; i < 26; i++) {
            letters[i].addActionListener(this);
        }
        scores.addActionListener(this);
        save.addActionListener(this);
        home.addActionListener(this);
        nextBtn.addActionListener(this);
        exitBtn.addActionListener(this);
        //adding components to form
        //this.add(time);
        this.add(scores);
        this.add(nameTxtf);
        this.add(name);
        this.add(home);
        //this.add(save);
        home.setBackground(Color.white);
        this.add(score);
        this.add(buttons);
        this.add(img); 
        this.add(exitBtn);
        this.add(nextBtn);
        this.add(word);                                                         
        buttons.add(finish);
        img.add(im); 
        this.add(Level); 
        Level.setFont(fnt);
        this.add(Hint);
  
    }
//    private static void PlayMusic(String filePath)
//    {   
//        InputStream Music; 
//        try
//        {
//            Music = new FileInputStream(new File(filePath)); 
//            AudioStream audios = new AudioStream(Music);
//            AudioPlayer.player.start(audios);
//        }
//        catch(Exception e)
//        {
//            JOptionPane.showMessageDialog(null, "ERORR");
//        }        
//    }
     public void write() throws Exception {
        File file = new File("singlePlayer.txt");
        FileWriter fileWriter = new FileWriter(file,true);
        BufferedWriter writer=null;
        writer =new BufferedWriter(fileWriter);
        PrintWriter printWriter=new PrintWriter(writer); 
        printWriter.println("\n"+nameTxtf.getText() + "  :        " + sc);
        printWriter.close();
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        
        if (e.getSource() == nextBtn && test == 0) 
        {
            PlayMusic("Music\\\\Click.wav"); 
            Min+=8; 
            NumOfLev++; 
            max+=8;
            iteratin++;
            this.dispose();
            new ActionPage(max,Min,NumOfLev,sc+10,nameTxtf.getText(),iteratin).setVisible(true);
//            AudioPlayer.player.stop(audios);
//            AudioPlayer.player.start(audios);
        }
        else if(e.getSource() == nextBtn && test > 0)
        {
            iteratin++;
            PlayMusic("Music\\\\Click.wav"); 
            this.dispose();
            new ActionPage(8,0,1,0,nameTxtf.getText(),iteratin).setVisible(true);
//            AudioPlayer.player.stop(audios);
//            AudioPlayer.player.start(audios);
        }
        else if (e.getSource() == exitBtn) 
        {
            if (!nameTxtf.getText().equals(""))
             {
            int p= JOptionPane.showConfirmDialog(this, "Do you want to save score ", "Confirmation", JOptionPane.YES_NO_OPTION);
            if(p==0){
               try{
                    write();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex);
                }
            }
            }
            PlayMusic("Music\\\\Click.wav"); 
            System.exit(0);
        }
        else if (e.getSource() == home) 
        {
            PlayMusic("Music\\\\Click.wav"); 
            if (!nameTxtf.getText().equals(""))
             {
            int p= JOptionPane.showConfirmDialog(this, "Do you want to save score ", "Confirmation", JOptionPane.YES_NO_OPTION);
            if(p==0){
               try{
                    write();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex);
                }
            }
            }

            this.dispose();
            new HomeScreen().setVisible(true);
        }
        else if (e.getSource() == scores) {
            PlayMusic("Music\\\\Click.wav");
            try {
                new PlayerScore().setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "error!");
            }
        }
        else 
        {
            for (int i = 0; i < 26; i++) 
            {
               
                if (e.getSource() == letters[i]) 
                {
                    PlayMusic("Music\\\\Click.wav"); 
                    letters[i].show(false);
                    if (s1.indexOf(letters[i].getText()) != -1) 
                    {
                        
                        y = letters[i].getText().toCharArray();
                        for (int j = 0; j < s1.length(); j++) 
                        {
                            if (y[0] == s1.charAt(j)) 
                            {
                                s2[(1+2*j)] = y[0];
                            }
                        }
                        s3 = String.valueOf(s2);
                        word.setText(s3);                       
                        for (int j = 0; j < s1.length()*2; j++) 
                        {
                            if (s2[j] == '_') 
                            {
                                end = false;
                                break;
                            } 
                            else
                            {
                                end = true;
                            }
                        }
                        if (end) 
                        {
                            successNum++; 
                            JOptionPane.showMessageDialog(this, "Good Guess !");
                            for (int j = 0; j < 26; j++) 
                            {
                                letters[j].show(false);
                            }
                            finish.setText("Good Game!");
                            //score.setText("Score : " + successNum*10);
                            nextBtn.show(true);
                            
                            //buttons.show(false);
                        }
                    } 
                    else 
                    {
                        failNum++;
                        if(failNum == 1)
                        {
                            PlayMusic("Music\\\\Ouch.wav");
                            im.setIcon(i2);
                            img.add(im); 
                        }
                        if(failNum == 1)
                        {
                            PlayMusic("Music\\\\Ouch.wav");
                            im.setIcon(i2);
                            img.add(im); 
                        }
                        if(failNum == 2)
                        {
                            PlayMusic("Music\\\\Ouch.wav");
                            im.setIcon(i3);
                            img.add(im); 
                        }if(failNum == 3)
                        {
                            PlayMusic("Music\\\\Ouch.wav");
                            im.setIcon(i4);
                            img.add(im); 
                        }if(failNum == 4)
                        {
                            PlayMusic("Music\\\\Ouch.wav");
                            im.setIcon(i5);
                            img.add(im); 
                        }if(failNum == 5)
                        {
                            PlayMusic("Music\\\\Ouch.wav");
                            im.setIcon(i6);
                            img.add(im); 
                        }
                        if(failNum == 6)
                        {
                            PlayMusic("Music\\\\OH NO.wav");
                            im.setIcon(i7);
                            img.add(im); 
                        }
                        if (failNum == 7) 
                        {
                            test++; 
                            im.setIcon(i8);
                            PlayMusic("Music\\\\Breaking Neck.wav");
                            img.add(im);
                            JOptionPane.showMessageDialog(this, "Bad Guess !");
                            for (int j = 0; j < 26; j++) 
                            {
                                letters[j].show(false);
                            }
                            finish.setText("Game Over !");
                            fnt.deriveFont(Font.BOLD, 40);
                            word.show(false);
                            score.setFont(fnt);
                            score.setBounds(420, 300, 200, 20);
                            nextBtn.setText("Try Again");
                            nextBtn.show(true);
                        }
                    }
                }
            }
        }

    }
       
}
