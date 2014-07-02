package GUI.buttons.util;

/**
 * Created by Florian on 04.05.14.
 */
public class Dimension {

    private float x, y, width, height;

    public Dimension(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }

    public float getWidth(){
        return this.width;
    }

    public float getHeight(){
        return this.height;
    }
}
