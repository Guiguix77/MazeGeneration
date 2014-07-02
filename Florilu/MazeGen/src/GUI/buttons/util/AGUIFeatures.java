package GUI.buttons.util;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Florian on 29.01.14.
 */
public abstract class AGUIFeatures {

    public Font font;

    public Rectangle rect;

    private Image normalButtonTexture;
    private Image pressedButtonTexture;

    public boolean floats;
    public String text;

    public AGUIFeatures() throws SlickException{
        this.rect = new Rectangle(0.0f, 0.0f, 0.0f, 0.0f);
        this.normalButtonTexture = new Image("res/gui/button/buttonNormal.png");
        this.pressedButtonTexture = new Image("res/gui/button/buttonPressed.png");
    }

    public void setDimensions(Dimension dimension){
        this.rect.setX(dimension.getX());
        this.rect.setY(dimension.getY());
        this.rect.setWidth(dimension.getWidth());
        this.rect.setHeight(dimension.getHeight());
    }

    public void draw(GameContainer container, Graphics g){
        if(this.floats){
            this.pressedButtonTexture.draw(this.rect.getX(), this.rect.getY(), this.rect.getWidth(), this.rect.getHeight());
        }else{
            this.normalButtonTexture.draw(this.rect.getX(), this.rect.getY(), this.rect.getWidth(), this.rect.getHeight());
        }
        drawText(g);
    }

    public void drawText(Graphics g){
        if(this.text != null && this.font != null){
            g.drawString(this.text, this.rect.getX() + ((this.rect.getWidth() - font.getWidth(this.text)) / 2), this.rect.getY() + (this.rect.getHeight() - font.getHeight(text)) / 2);
        }
    }

    public abstract void doWhenClick(StateBasedGame stateBasedGame);
    public void setText(String text, Font font){
        this.text = text;
        this.font = font;
    }

    public void update(InputSummary inputSummary){
        if((inputSummary.mouseX == 0 && inputSummary.mouseY == 0) && (inputSummary.mouseFY > 0 && inputSummary.mouseFX > 0)){
            if(this.rect.contains(inputSummary.mouseFX, inputSummary.mouseFY)){
                this.floats = true;
            }else{
                this.floats = false;
            }
        }else{
            if(this.rect.contains(inputSummary.mouseX, inputSummary.mouseY)){
                this.floats = true;
            }else{
                this.floats = false;
            }
        }
        if(inputSummary.click && ((this.rect.contains(inputSummary.mouseX, inputSummary.mouseY)) || (this.rect.contains(inputSummary.mouseFX, inputSummary.mouseFY)))){
            doWhenClick(inputSummary.stateBasedGame);
        }
    }
}
