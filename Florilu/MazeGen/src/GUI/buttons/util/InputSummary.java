package GUI.buttons.util;

import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Florian on 25.04.14.
 */
public class InputSummary {

    public StateBasedGame stateBasedGame;
    public int mouseX, mouseY;
    public float mouseFX, mouseFY;
    public boolean click;

    public InputSummary(StateBasedGame stateBasedGame, int mouseX, int mouseY, boolean click){
        this.stateBasedGame = stateBasedGame;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.click = click;
    }

    public InputSummary(StateBasedGame stateBasedGame, float mouseFX, float mouseFY, boolean click){
        this.stateBasedGame = stateBasedGame;
        this.mouseFX = mouseFX;
        this.mouseFY = mouseFY;
        this.click = click;
    }
}
