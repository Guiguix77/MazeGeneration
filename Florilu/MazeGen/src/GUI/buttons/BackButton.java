package GUI.buttons;
import GUI.buttons.util.AGUIFeatures;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Florian on 29.01.14.
 */
public class BackButton extends AGUIFeatures {

    private int stateToEnter;

    public BackButton(int stateToEnter) throws SlickException {
        this.stateToEnter = stateToEnter;
    }

    @Override
    public void doWhenClick(StateBasedGame stateBasedGame) {
        stateBasedGame.enterState(this.stateToEnter);
    }
}
