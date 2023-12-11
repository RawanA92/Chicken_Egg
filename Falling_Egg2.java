package assets;

import javax.media.opengl.GL;

public class Falling_Egg2 extends level1 {
    private float x, y;
    private int index;
    private float scale;
    private boolean isActive;


    public Falling_Egg2(float x, float y, int index, float scale) {
        this.x = x;
        this.y = y;
        this.index = index;
        this.scale = scale;
        this.isActive = false;
    }

    public void fall() {
        // Implement the logic for making the egg fall down
        y -= 0.7f; // Adjust the falling speed as needed
    }

    public void draw(GL gl) {

        DrawSprite(gl, x, y, 10, 0.5f);
    }

    public float getX() {

        return x;
    }

    public float getY() {

        return y;
    }
    public float getX2() {

        return x;
    }

    public float getY2() {

        return y;
    }

    public void setX(float x) {

        this.x = x-15;
    }

    public void setY(float y) {

        this.y = y;
    }
    public void setX2(float x) {

        this.x = x-15;
    }

    public void setY2(float y) {

        this.y = y;
    }
    // Getter and setter for isActive
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {

        isActive = active;
    }
}
