package four;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;

public class Window extends JFrame {
    
    private Game game;
    
    private JButton[][] table;
    
    private JLabel text;
    
    public Window(){
        init();
        setVisible(true);
    }
    
    public Window(int n, int m){
        init();
        this.game = new Game(n,m);
        this.table = new JButton[m][n];
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,n));
        
        for(int i = 0; i < n; i++){
            addButton(buttonPanel,i);
        }
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(m, n));

        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                JButton button = new JButton();
                button.setEnabled(false);
                mainPanel.add(button);
                this.table[i][j]=button;
            }
        }
        
        JPanel mainFrame = new JPanel();
        mainFrame.setLayout(new BorderLayout());
                
        JPanel main = new JPanel();
        
        text = new JLabel(game.getTurn().toString() + " játékos köre");
        text.setHorizontalAlignment(JLabel.CENTER);
        mainFrame.add(text,BorderLayout.SOUTH);
        
        mainFrame.add(buttonPanel,BorderLayout.NORTH);
        mainFrame.add(mainPanel,BorderLayout.CENTER);
        
        getContentPane().add(mainFrame);
        setVisible(true);
    }
    
    public void addButton(JPanel panel, int i){
        JButton button = new JButton();
        
        button.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = game.freeIndex(i);
                if(index!=-1){
                    Player actual = game.step();
                    if(actual==Player.Kék){
                        table[index][i].setBackground(new Color(0,0,255));
                    }else{
                        table[index][i].setBackground(new Color(255,0,0));
                    }
                    game.setTable(index,i,actual);
                    updateText();
               
                if(game.findWinner(index)!=Player.Neutral){
                    String winnerText = game.findWinner(index).toString();
                    int n = JOptionPane.showConfirmDialog(null, winnerText + " játékos nyert. Szeretne új játékot kezdeni?","Új Játék", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION){
                        dispose();
                        new Window();
                    }else{
                        System.exit(0);
                    }
                }
                if(game.isFull()){
                    int n = JOptionPane.showConfirmDialog(null,"A játék döntetlen. Szeretne új játékot kezdeni?","Új Játék", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION){
                        dispose();
                        new Window();
                    }else{
                        System.exit(0);
                    }
                }
                }
            }
        });
        button.setPreferredSize(new Dimension(40, 40));
        panel.add(button);
    }
    
    public void showExitConfirmation(){
        int n = JOptionPane.showConfirmDialog(this, "Biztosan ki szeretne lépni?",
                "Megerősítés", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    public void updateText(){
        this.text.setText(game.getTurn().toString() + " játékos köre");
    }
    
    public void init(){
        setTitle("4 in a row játék");
        setSize(600, 700);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Uj Jatek");
        
        JMenuItem small = new JMenuItem(new AbstractAction("8x5"){
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
                new Window(8,5);
            }
        });
        small.setMnemonic(KeyEvent.VK_S);
        menu.add(small);
        JMenuItem medium = new JMenuItem(new AbstractAction("10x6"){
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
                new Window(10,6);
            }
        });
        
        menu.add(medium);
        JMenuItem large = new JMenuItem(new AbstractAction("12x7"){
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
                new Window(12,7);
            }
        });
        
        menu.add(large);
        
        menuBar.add(menu);
        
        getContentPane().add(menuBar,BorderLayout.NORTH);
        
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                showExitConfirmation();
            }

        });
        
        
    }
}
