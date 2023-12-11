import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

import javax.media.opengl.GLCanvas;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;


public class Chicken_Eggs extends JFrame {
    public static Clip voice;
    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            System.out.println(i%4);
//        }
        musics.main_music.playMusic();
        new Chicken_Eggs();
    }


    public Chicken_Eggs() {
        GLCanvas glcanvas;
        Animator animator;
        if (voice == null) voice = utilities.playmusic(".//assets//sound//chicken dance song.wav",false);
        AnimListener_egg listener = new level2();
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        animator = new FPSAnimator(24);
        animator.add(glcanvas);
        animator.start();

        setTitle("Chicken Eggs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
    }
}