package GUI.buttons;

import GUI.buttons.util.AGUIFeatures;
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
        this.selected = !selected;
        this.setText("DFS = " + this.selected, this.font);
    }
}
