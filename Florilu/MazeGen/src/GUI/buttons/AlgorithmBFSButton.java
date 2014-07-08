package GUI.buttons;

import GUI.buttons.util.AGUIFeatures;
import GUI.buttons.util.ButtonStates;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Florian on 09.02.14.
 */
public class AlgorithmBFSButton extends AGUIFeatures {

    public boolean selected = false;

    public AlgorithmBFSButton() throws SlickException {
    }

    @Override
    public void doWhenClick(StateBasedGame stateBasedGame) {
        ButtonStates.BFSState = !ButtonStates.BFSState;
        this.setText("BFS = " + ButtonStates.BFSState, this.font);
    }
}
