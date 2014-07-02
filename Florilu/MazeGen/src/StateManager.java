import GUI.menus.ChooseSolvingAlgorithmMenu;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Florian on 29.01.14.
 */
public class StateManager extends StateBasedGame{

    public Panel panel;
    public OptionsMenu optionsMenu;
    public ChooseSolvingAlgorithmMenu algorithmMenu;

    public StateManager() throws SlickException{
        super("MazeGeneration and Solving");

        panel = new Panel();
        optionsMenu = new OptionsMenu();
        algorithmMenu = new ChooseSolvingAlgorithmMenu();

        AppGameContainer container = new AppGameContainer(this);
        container.setDisplayMode(1000, 1000, false);
        container.setResizable(false);
        container.start();
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        addState(panel);
        addState(optionsMenu);
        addState(algorithmMenu);
    }
}
