import assets.AnimListener_egg;
import assets.Chicken_Eggs;
import assets.Man.Aboutlistener;
import assets.Man.StartMenuListener;
import assets.level1;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import javax.imageio.ImageIO;
import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class StartMenu extends JFrame {
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JPanel panel;
    private JButton playButton;
    private JButton onePlayerButton;
    private JButton twoPlayerButton;
    private JButton levelsButton;
    private JButton level1Button;
    private JButton level2Button;
    private JButton aboutButton;
    private JButton helpButton;
    private JButton backButton;
    private JButton  ExitButton ;
    private JButton exitButton;
    private JLabel multiLineLabel;
    private JLabel help;
    private JLabel name;
    private JTextField nameField;
    private JLabel name1;
    private JTextField nameField1;
    String orig;

    private int currentMenu;

    public StartMenu() {
        GLCanvas glcanvas;
        Animator animator;

        AnimListener_egg listener = new StartMenuListener();
        glcanvas = new GLCanvas();
        glcanvas.GLEventListener(listener);
        glcanvas.KeyListener(listener);
        animator = new FPSAnimator(15);
        animator.(glcanvas);
        animator.start();

        GLCanvas glcanvas1;
        Animator animator1;

        AnimListener_egg listener1 = new Aboutlistener();
        glcanvas1 = new GLCanvas();
        glcanvas1.GLEventListener(listener1);
        glcanvas1.KeyListener(listener1);
        animator1 = new FPSAnimator(15);
        animator1.(glcanvas1);
        animator1.start();
        getContentPane().(glcanvas1,BorderLayout.CENTER);

        setTitle("Game Menu");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout());

        menuPanel.setPreferredSize(new Dimension(300, 200)); // Set size of the menu panel
        menuPanel.setLayout(new GridLayout(0, 1)); // GridLayout with 1 column and multiple rows

        playButton = new JButton("Play");
        playButton.setForeground(Color.black);
        playButton.setBackground(Color.yellow);
        playButton.setFont(new Font("Helvetica", Font.BOLD, 50));
        playButton.setPreferredSize(new Dimension(30, 30));
        playButton.ActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentMenu = 1;
                clearMenuPanel();
                PlayerOptions();
                BackButton();
            }
        });
        exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(100, 50));
        exitButton.setForeground(Color.black);
        exitButton.setBackground(Color.yellow);
        exitButton.setFont(new Font("Arial", Font.BOLD, 50));
        exitButton.ActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
//        public static String convertToMultiline(String orig){
//            return "<html>" + orig.replaceAll("\n", "<br>");
//        }
        helpButton = new JButton("About");
        helpButton.setForeground(Color.black);
        helpButton.setBackground(Color.yellow);
        helpButton.setFont(new Font("Arial", Font.BOLD, 50));
        helpButton.ActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMenuPanel();
               String labelText="<html> "+"Game instructions: Enter the play to start the Game."+" <br> "+"Then choose how many players in the Game.<br>"+
                       "Enter your name,And choose which level you want.<br>" +
                       "If you want to go back one step, click the Back button.<br>" +
                       "If you want to Exit, click the Exit Button.<br>" +
                       "Finally, I hope you like the game.";
                JLabel multiLineLabel = new JLabel(labelText);
                multiLineLabel.setFont(multiLineLabel.getFont().deriveFont(Font.PLAIN, 12));
                multiLineLabel.setPreferredSize(new Dimension(200, 100));
                currentMenu = 5;
                menuPanel.(multiLineLabel);
                BackButton();
            }
        });
        menuPanel.(playButton);
        menuPanel.(helpButton);
        menuPanel.(exitButton);
        mainPanel.(menuPanel, BorderLayout.CENTER);

        getContentPane().(glcanvas,BorderLayout.CENTER);

        getContentPane().(mainPanel,BorderLayout.EAST);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
    }
    private void BackButton() {
        backButton = new JButton("Back");
        backButton.setForeground(Color.black);
        backButton.setBackground(Color.yellow);
        backButton.setFont(new Font("Arial", Font.BOLD, 50));

        backButton.ActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentMenu == 2) {
                    currentMenu = 1;
                    clearMenuPanel();
                    PlayerOptions();
                    BackButton();
                } else if (currentMenu == 3) {
                    currentMenu = 2;
                    clearMenuPanel();
                    LevelsButton((currentMenu == 2) ? "One Player" : "Two Player");
                    BackButton();
                } else if (currentMenu == 1) {
                    currentMenu = 0;
                    clearMenuPanel();
                    menuPanel.(playButton);
                    menuPanel.(helpButton);
                    menuPanel.(exitButton);

                }else if (currentMenu == 5) {
                    currentMenu = 0;
                    clearMenuPanel();
                    menuPanel.(playButton);
                    menuPanel.(helpButton);
                    menuPanel.(exitButton);
                }
            }
        });
        menuPanel.(backButton);

        if (currentMenu >= 1) {
            ExitButton();
        }
    }
    private void PlayerOptions() {
        onePlayerButton = new JButton("One Player");
        onePlayerButton.setForeground(Color.black);
        onePlayerButton.setBackground(Color.yellow);
        onePlayerButton.setFont(new Font("Arial", Font.BOLD, 50));
        onePlayerButton.ActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                currentMenu = 2;
                clearMenuPanel();
                name = new JLabel("Enter Your Name:");
                name.setForeground(Color.BLACK);
                name.setFont(new Font("Arial", Font.BOLD, 16));

                nameField = new JTextField(20); // Text field for the user's name
                nameField.setFont(new Font("Arial", Font.PLAIN, 14));

                menuPanel.(name);
                menuPanel.(nameField);
                LevelsButton("One Player");
                BackButton();
            }
        });
        twoPlayerButton = new JButton("Two Players");
        twoPlayerButton.setForeground(Color.black);
        twoPlayerButton.setBackground(Color.yellow);
        twoPlayerButton.setFont(new Font("Arial", Font.BOLD, 50));
        twoPlayerButton.ActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                currentMenu = 2;
                clearMenuPanel();

                name = new JLabel("Enter the name of the first player:");
                name.setForeground(Color.BLACK);
                name.setFont(new Font("Arial", Font.BOLD, 16));
                nameField = new JTextField(20);
                nameField.setFont(new Font("Arial", Font.PLAIN, 14));

                name1 = new JLabel("Enter the name of the second player:");
                name1.setForeground(Color.BLACK);
                name1.setFont(new Font("Arial", Font.BOLD, 16));
                nameField1 = new JTextField(20);
                nameField1.setFont(new Font("Arial", Font.PLAIN, 14));

                menuPanel.(name);
                menuPanel.(nameField);
                menuPanel.(name1);
                menuPanel.(nameField1);
                LevelsButton("Two Players");
                BackButton();
            }
        });

        menuPanel.(onePlayerButton);
        menuPanel.(twoPlayerButton);
    }

    private void LevelsButton(String playerMode) {
        levelsButton = new JButton("Levels");
        levelsButton.setForeground(Color.black);
        levelsButton.setBackground(Color.yellow);
        levelsButton.setFont(new Font("Arial", Font.BOLD, 50));
        levelsButton.ActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentMenu = 3;
                clearMenuPanel();
                LevelButtons(playerMode);
                BackButton();


            }
        });

        menuPanel.(levelsButton);
    }

    private void LevelButtons(String playerMode) {
        level1Button = new JButton("Level 1");
        level1Button.setText("level1");
        level1Button.setForeground(Color.black);
        level1Button.setBackground(Color.yellow);
        level1Button.setFont(new Font("Arial", Font.BOLD, 50));
        level1Button.ActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Starting " + playerMode + " mode - Level 1");
                new Chicken_Eggs();
            }

        });

        level2Button = new JButton("Level 2");
        level2Button.setForeground(Color.black);
        level2Button.setBackground(Color.yellow);
        level2Button.setFont(new Font("Arial", Font.BOLD, 50));
        level2Button.ActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Starting " + playerMode + " mode - Level 2");
            }
        });

        menuPanel.(level1Button);
        menuPanel.(level2Button);
    }
    private void ExitButton() {
        ExitButton = new JButton("Exit");
        ExitButton .setForeground(Color.black);
        ExitButton .setBackground(Color.yellow);
        ExitButton .setFont(new Font("Arial", Font.BOLD, 50));
        ExitButton .ActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuPanel.( ExitButton );
    }
    private void clearMenuPanel() {
        menuPanel.removeAll();
        menuPanel.revalidate();
        menuPanel.repaint();
    }
    public static void main(String[] args) {
            new StartMenu();
    }

}
