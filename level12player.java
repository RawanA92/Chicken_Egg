import com.sun.opengl.util.j2d.TextRenderer;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;


public class level12player extends AnimListener_egg {

    int lost_time = 0 ;
    List<Falling_Egg2player> fallingEggs = new ArrayList<>();

    private Falling_Egg2player fallingEgg;

    int animationIndex = 0;
    int maxWidth = 100;
    int maxHeight = 100;
    int x = maxWidth / 2, y = maxHeight / 2;
    int frameCount = 0;
    int lives = 3;
    int score = 0;
    float boxX = 1;
    float boxY = 5.5f;
    float boxX2 = 50;
    float boxY2 = 5.5f;




    TextRenderer textRenderer = new TextRenderer(new Font("SansSerif", Font.PLAIN, 24));


    String textureNames[] = {
            "Char_run_armless_00000.png",
            "Char_run_armless_00001.png",
            "Char_run_armless_00002.png",
            "Char_run_armless_00003.png",
            "Char_run_armless_00004.png",
            "Char_run_armless_00005.png",
            "chicken2.jpg",
            "Platform.png",
            "Chicken_Idle (2).png",
            "Box.png",
            "Egg.png",
            "Egg_rotten.png",
            "ChickenNest.png",
            "heart.png",
            "Chicken_lay01 (2).png",
            "Game Over, Loser_.jpeg",
            "Back.jpeg",
    };
    TextureReader_egg.Texture texture[] = new TextureReader_egg.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];
    private int score1=0;
    private int score2=0;



    /*
     5 means gun in array pos
     x and y coordinate for gun
     */
    public void init(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        fallingEggs.add(new Falling_Egg2player(15, 14 * 6, 8, 1.5f));
        fallingEggs.add(new Falling_Egg2player(15 * 5, 14 * 6, 8, 1.5f));
        fallingEggs.add(new Falling_Egg2player(15 * 3, 14 * 6, 8, 1.5f));


        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader_egg.readTexture(assetsFolderName + "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

//                mipmapsFromPNG(gl, new GLU(), texture[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Imagedata
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 30; i++) {
            fallingEggs.add(new Falling_Egg2player(-100, -100, 0, 0)); // Initialize eggs outside the visible area
        }
    }

    private void activateEggsForChicken() {
        for (Falling_Egg2player egg : fallingEggs) {
            if (!egg.isActive()) {
                egg.setActive(true);
                int randomIndex = (int) (Math.random() * 4);
                float randomX = 2 * 15 * (randomIndex + 1);
                egg.setY(14 * 6);
                egg.setX(randomX);

                break;
            }
        }
    }

    public void display(GLAutoDrawable gld) {
        boolean lost = false;
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl.glLoadIdentity();

        if (lives != 0) {
            if(score1!=10 && score2!=10) {

                DrawBackground(gl);
                handleKeyPress();
                handleKeyPress2();
                animationIndex = animationIndex % 6;

                if (frameCount % 50 == 0 && frameCount < 1500) {
                    activateEggsForChicken();
                }

                for (Falling_Egg2player egg : fallingEggs) {
                    if (egg.isActive()) {
                        if (checkCollision(egg.getX(), egg.getY(), 1.0f, 1.0f, boxX + 1, 5.5f, x - 33, 1.5f)) {
                            egg.setActive(false);
                            score1++;
                        } else if (checkCollision(egg.getX(), egg.getY(), 1.0f, 1.0f, boxX2 + 1, boxY2, x - 33, 1.5f)) {
                            egg.setActive(false);
                            score2++;
                        } else {
                            egg.fall();
                            DrawSprite(gl, egg.getX(), egg.getY(), 10, 0.5f);

                            if (egg.getY() < -1.0f && boxX< 0) {
                                egg.setActive(false);
                                score1--;
                            }
                            if (egg.getY() < -1.0f && boxX2>0 ) {
                                egg.setActive(false);
                                score2--;
                            }

                            if (egg.getY() == boxX && egg.getY() == boxY) {
                                egg.setActive(false);
                                score1++; // Increase score for the first box
                            }
                            if (egg.getY() == boxX2 && egg.getY() == boxY2) {
                                egg.setActive(false);
                                score2++; // Increase score for the first box
                            }
                        }
                    }
                }

                if (score1 < 0) {
                    lives--;
                    score1 = 0;
                }

                if (score2 < 0) {
                    lives--;
                    score2 = 0;
                }

                frameCount++;
                DrawSprite(gl, boxX, boxY, 9, 1.5f);
                DrawSprite(gl, boxX2, boxY2, 9, 1.5f);
                DrawSprite(gl, 1, 14 * 6, 14, 1.5f);
                DrawSprite(gl, 15 * 2, 14 * 6, 14, 1.5f);
                DrawSprite(gl, 15 * 4, 14 * 6, 14, 1.5f);
                DrawSprite(gl, 15 * 6, 14 * 6, 14, 1.5f);

                textRenderer.beginRendering(gld.getWidth(), gld.getHeight());
                textRenderer.draw("Score Box 1: " + score1, 10, 30);
                textRenderer.draw("Score Box 2: " + score2, 10, 10);
                textRenderer.endRendering();

                if (score1 == -1 || score2 == -1) {
                    lives--;
                }

                for (int i = 0; i < lives; i++) {
                    DrawSprite(gl, 15 * 0.5f, 14 * 6, 13, 0.5f);
                }
                for (int i = 1; i < lives; i++) {
                    DrawSprite(gl, 15 * 0.7f, 14 * 6, 13, 0.5f);
                }
                for (int i = 2; i < lives; i++) {
                    DrawSprite(gl, 15 * 0.9f, 14 * 6, 13, 0.5f);
                }
            } else if (score1 ==10 && score2<10){
                JOptionPane.showMessageDialog(null, "Player 1 is win  Good Job");
                System.exit(0);
                new StartMenu1();

            } else if (score2 ==10 && score1 <10){
                JOptionPane.showMessageDialog(null, "Player 2 is win  Good Job");

                new StartMenu1();

            }


        } else {
            DrawBackground2(gl);
            musics.main_music.stopMusic();
            lost = true;
            lost_time++;
            if ((lost_time < 2)) {
                musics.game_over.playMusic();
                lost_time++;
            }
        }
    }


    private boolean checkCollision(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {
        return x1 < x2 + w2 &&
                x1 + w1 > x2 &&
                y1 < y2 + h2 &&
                y1 + h1 > y2;
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void DrawSprite(GL gl, float x, float y, int index, float scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);


        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(0.1 * scale, 0.1 * scale, 1);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    public void DrawBackground(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[textures.length - 1]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    public void DrawBackground2(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[textures.length - 2]);

        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }



    public void handleKeyPress() {

        int step = 1;

        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            if (boxX > 0) {
                boxX -= step;
            }
            animationIndex++;
        }

        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (boxX < maxWidth / 2) {
                boxX += step;
            }
            animationIndex++;
        }
        if (isKeyPressed(KeyEvent.VK_B)) {
            musics.main_music.stopMusic();
        }
    }
    public void handleKeyPress2() {
        int step = 1;

        if (isKeyPressed(KeyEvent.VK_A)) {
            if (boxX2 > maxWidth / 2) {
                boxX2 -= step;
            }
            animationIndex++;
        }

        if (isKeyPressed(KeyEvent.VK_D)) {
            if (boxX2 < maxWidth - 10) {
                boxX2 += step;
            }
            animationIndex++;
        }
        if (isKeyPressed(KeyEvent.VK_B)) {
            musics.main_music.stopMusic();
        }
    }


    public BitSet keyBits = new BitSet(256);

    @Override
    public void keyPressed(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.set(keyCode);
    }

    @Override
    public void keyReleased(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.clear(keyCode);
    }

    @Override
    public void keyTyped(final KeyEvent event) {
        // don't care
    }

    public boolean isKeyPressed(final int keyCode) {
        return keyBits.get(keyCode);
    }

}
