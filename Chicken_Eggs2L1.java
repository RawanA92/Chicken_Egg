import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

import javax.media.opengl.GLCanvas;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;




public class Chicken_Eggs2L1 extends JFrame {
    public static Clip voice;
    public static void main(String[] args) {

        new Chicken_Eggs2L1();
        musics.main_music.playMusic();

    }


    public Chicken_Eggs2L1() {
        GLCanvas glcanvas;

        Animator animator;
        if (voice == null) voice = utilities.playmusic(".//assets//sound//chicken dance song.wav",false);
        AnimListener_egg listener = new level12player();
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