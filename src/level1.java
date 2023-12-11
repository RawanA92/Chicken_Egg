import com.sun.opengl.util.j2d.TextRenderer;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;


public class level1 extends AnimListener_egg {

    int lost_time = 0 ;
    List<Falling_Egg> fallingEggs = new ArrayList<>();

    private Falling_Egg fallingEgg;

    int animationIndex = 0;
    int maxWidth = 100;
    int maxHeight = 100;
    int x = maxWidth / 2, y = maxHeight / 2;
    int frameCount = 0;
    int lives = 3;
    int score = 0;




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
            "GameOver.png",
            "Back.jpeg",
    };
    TextureReader_egg.Texture texture[] = new TextureReader_egg.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];


    /*
     5 means gun in array pos
     x and y coordinate for gun
     */
    public void init(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        fallingEggs.add(new Falling_Egg(15, 14 * 6, 8, 1.5f));
        fallingEggs.add(new Falling_Egg(15 * 5, 14 * 6, 8, 1.5f));
        fallingEggs.add(new Falling_Egg(15 * 3, 14 * 6, 8, 1.5f));


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
        // Create instances of FallingEgg and add them to the list (initially inactive)
        for (int i = 0; i < 30; i++) {
            fallingEggs.add(new Falling_Egg(-100, -100, 0, 0)); // Initialize eggs outside the visible area
        }
    }

    private void activateEggsForChicken() {
        // Set the corresponding egg as active
        for (Falling_Egg egg : fallingEggs) {
            if (!egg.isActive()) {
                egg.setActive(true);
                int randomIndex = (int) (Math.random() * 3);  // Generates a random index (0, 1, or 2)
                float randomX = 2 * 15 * (randomIndex + 1);
                egg.setY(14 * 6);  // Initial Y position for the chicken
                //egg.setX((float) Math.random() * (maxWidth - 10)); // Randomize the X position
                egg.setX(randomX); // Randomized X position based on the selected index

                //egg.setX(15 * 3);
                break; // Activate only one egg at a time
            }
        }
    }


    public void display(GLAutoDrawable gld) {
        boolean lost = false ;


        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer

        gl.glLoadIdentity();

        if (lives != 0) {
            DrawBackground(gl);
            handleKeyPress();
            animationIndex = animationIndex % 6;


//        DrawGraph(gl);
            DrawSprite(gl, x, 14, animationIndex, 4); //man
            float boxX = x + 5; // Adjust the box's X coordinate based on the man's position
            float boxY = 5.5f;

            if (frameCount  % 30 == 0 && frameCount < 1500) { // Activate eggs for the first 30 frames
                activateEggsForChicken();
            }

            // Update and draw falling eggs
            for (Falling_Egg egg : fallingEggs) {
                if (egg.isActive()) {
                    if (checkCollision(egg.getX(), egg.getY(), 1.0f, 1.0f, x + 1, 5.5f, x, 1.5f)) {
                        egg.setActive(false);
                        score++;
                    } else {
                        egg.fall();
                        DrawSprite(gl, egg.getX(), egg.getY(), 10, 0.5f);

                        if (egg.getY() < -1.0f) {
                            egg.setActive(false);
                            score--;
                        }

                        if (egg.getY() == boxX && egg.getY() == boxY) {
                            egg.setActive(false);
                            score++;
                        }
                    }
                }
            }

// Adjust the condition for decrementing lives
            if (score < 0) {
                lives--;
                score = 0; // Reset the score to prevent continuous decrement
            }


            // Increment the frame count
            frameCount++;
            DrawSprite(gl, boxX, boxY, 9, 1.5f); //box
            DrawSprite(gl, 15, 14 * 6, 8, 1.5f);
            DrawSprite(gl, 15 * 3, 14 * 6, 8, 1.5f);
            DrawSprite(gl, 15 * 5, 14 * 6, 8, 1.5f);

            textRenderer.beginRendering(gld.getWidth(), gld.getHeight());
            textRenderer.draw("Score: " + score, 10, 10); // Adjust the coordinates as needed
            textRenderer.endRendering();

            if (score == -1) {
                lives--;
            }
//            System.out.println(lives);

            for (int i = 0; i < lives; i++) {
                DrawSprite(gl, 15 * 0, 14 * 6, textureNames.length - 3, 0.5f);
            }
            for (int i = 1; i < lives; i++) {
                DrawSprite(gl, 15 * 0.2f, 14 * 6, textureNames.length - 3, 0.5f);
            }
            for (int i = 2; i < lives; i++) {
                DrawSprite(gl, 15 * 0.4f, 14 * 6, textureNames.length - 3, 0.5f);
            }

        } else {
            DrawBackground2(gl);
            musics.main_music.stopMusic();
            lost = true ;
            lost_time ++ ;
            if ((lost_time < 2) ) {
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
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(0.1 * scale, 0.1 * scale, 1);
        //System.out.println(x +" " + y);
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
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[textures.length - 2]);    // Turn Blending On

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

    /*
     * KeyListener
     */

    public void handleKeyPress() {

        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            if (x > 0) {
                x--;
            }
            animationIndex++;
        }
        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (x < maxWidth - 10) {
                x++;
            }
            animationIndex++;
        }
        /*if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (y > 0) {
                y--;
            }
            animationIndex++;
        }
        if (isKeyPressed(KeyEvent.VK_UP)) {
            if (y < maxHeight-10) {
                y++;
            }
            animationIndex++;
        }*/
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
    public void  playMusic(int i){

    }

    public void stopMusic(){ }
}


