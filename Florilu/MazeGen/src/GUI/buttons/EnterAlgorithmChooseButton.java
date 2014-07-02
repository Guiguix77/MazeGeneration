package GUI.buttons;

import GUI.buttons.util.AGUIFeatures;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Florian on 09.02.14.
 */
public class EnterAlgorithmChooseButton extends AGUIFeatures {
    public EnterAlgorithmChooseButton() throws SlickException {
    }

    @Override
    public void doWhenClick(StateBasedGame stateBasedGame) {
        stateBasedGame.enterState(2);
    }
}
