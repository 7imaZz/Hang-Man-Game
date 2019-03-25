package hangman;


import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.swing.*;
import javax.swing.event.AncestorListener;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


public class HomeScreen extends JFrame implements ActionListener 
{
    private String str="";
    static InputStream Music;
    static AudioStream audios; 
    private ImageIcon mu,unmu; 
    public static int c = 0; 
    private JPanel BackGround;
    private ImageIcon image; 
    private JLabel img;
    private JButton btn1,btn2,multy,mute, unmute;  
    public HomeScreen()
    {       
        if(c==0)
        {
            PlayMusic("Music\\\\Carmen.wav"); 
        }
        this.setLayout(null);
       // this.setLocationRelativeTo(null);
        this.setTitle("Hang Man");
        this.setSize(1000, 700);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Constructig Components
        mu = new ImageIcon(getClass().getResource("mute.png"));
        unmu = new ImageIcon(getClass().getResource("unmute.png"));
        BackGround = new JPanel();
        image = new ImageIcon(getClass().getResource("Back.jpg")); 
        img = new JLabel(image);
        mute = new JButton(mu);
        unmute = new JButton(unmu); 
        btn1=new JButton("Single Player");
        btn2=new JButton("Exit");
        
        multy=new JButton("2 Players");
        //Locating Components
        BackGround.setBounds(0,0,1000, 670);
        
        btn1.setBounds(670,350,250,50);
        multy.setBounds(670,450,250,50);
        btn2.setBounds(670,550,250,50);
        mute.setBounds(960, 10, 30, 30);
        unmute.setBounds(960, 10, 30, 30);
        unmute.show(false);
        
        this.add(unmute);
        this.add(mute); 
        this.add(btn1);
        this.add(btn2);
        
        this.add(multy);
        this.add(BackGround);
        BackGround.add(img); 
   
        mute.addActionListener(this);
        unmute.addActionListener(this);
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        multy.addActionListener(this);
        
        c++;
    }
    public static void PlayMusic(String filePath)
    {
         
        try
        {
            Music = new FileInputStream(new File(filePath)); 
            audios = new AudioStream(Music);
            AudioPlayer.player.start(audios);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "ERORR");
        }        
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
           if(e.getSource()==mute)
           {
               
               mute.show(false);
               unmute.show(true);
               AudioPlayer.player.stop(audios);
           }
           if(e.getSource()==unmute)
           {
               
               unmute.show(false);
               mute.show(true);
               AudioPlayer.player.start(audios);
           }
           if(e.getSource()==btn1)
           {
               PlayMusic("Music\\\\Click.wav"); 
               this.dispose();
               new ActionPage(8,0,1,0,str,0).setVisible(true);
           }
           else if(e.getSource()==btn2)
           {
               PlayMusic("Music\\\\Click.wav");  
               System.exit(0);
           }
           else if(e.getSource() == multy)
           {
               PlayMusic("Music\\\\Click.wav"); 
               this.dispose();
               new TwoPlayers().setVisible(true); 
           }
    }
}
