package GUI.buttons;

import GUI.buttons.util.AGUIFeatures;
import GUI.buttons.util.ButtonStates;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Florian on 09.02.14.
 */
public class AlgorithmAStarButton extends AGUIFeatures {

    public boolean selected = false;

    public AlgorithmAStarButton() throws SlickException {
    }

    @Override
    public void doWhenClick(StateBasedGame stateBasedGame) {
        ButtonStates.AStarState = !ButtonStates.AStarState;
        this.setText("AStar = " + ButtonStates.AStarState, this.font);

        System.out.println("HEY");
    }
}
