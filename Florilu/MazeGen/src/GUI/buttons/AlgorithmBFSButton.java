package GUI.buttons;

import GUI.buttons.util.AGUIFeatures;
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
        this.selected = !selected;
        this.setText("BFS = " + this.selected, this.font);
    }
}
