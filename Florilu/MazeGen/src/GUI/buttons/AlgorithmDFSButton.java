package GUI.buttons;

import GUI.buttons.util.AGUIFeatures;
import GUI.buttons.util.ButtonStates;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Florian on 02.07.14.
 */
public class AlgorithmDFSButton extends AGUIFeatures {

    public boolean selected = false;

    public AlgorithmDFSButton() throws SlickException {
    }

    @Override
    public void doWhenClick(StateBasedGame stateBasedGame) {
        ButtonStates.DFSState = !ButtonStates.DFSState;
        this.setText("DFS = " + ButtonStates.DFSState, this.font);
    }
}
