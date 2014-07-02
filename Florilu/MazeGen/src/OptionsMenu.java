import GUI.buttons.*;
import GUI.buttons.util.Dimension;
import GUI.buttons.util.InputSummary;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Florian on 29.01.14.
 */
public class OptionsMenu extends BasicGameState{

    private BackButton backButton;
    private EnterAlgorithmChooseButton algorithmChooseButton;
    private ClearLogsButton clearLogsButton;
    private GenAndSolOptButton genSolButton;
    private SaveLevelButton saveLevelButton;
    private LoadLevelButton loadLevelButton;
    private final int BUTTONWIDTH = 200, BUTTONHEIGHT = 50;
    private InputSummary inputSummary;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

        float x = (gameContainer.getWidth() - BUTTONWIDTH) / 2;

        this.algorithmChooseButton = new EnterAlgorithmChooseButton();
        this.algorithmChooseButton.setDimensions(new Dimension(x, 100, BUTTONWIDTH, BUTTONHEIGHT));
        this.algorithmChooseButton.setText("Enter Algorithm Menu", gameContainer.getDefaultFont());

        this.genSolButton = new GenAndSolOptButton();
        this.genSolButton.setDimensions(new Dimension(x, 155, BUTTONWIDTH, BUTTONHEIGHT));
        this.genSolButton.setText("Gen and Solve Options", gameContainer.getDefaultFont());

        this.saveLevelButton = new SaveLevelButton();
        this.saveLevelButton.setDimensions(new Dimension(x, 210, BUTTONWIDTH, BUTTONHEIGHT));
        this.saveLevelButton.setText("Save Level", gameContainer.getDefaultFont());

        this.loadLevelButton = new LoadLevelButton();
        this.loadLevelButton.setDimensions(new Dimension(x, 265, BUTTONWIDTH, BUTTONHEIGHT));
        this.loadLevelButton.setText("Load Level", gameContainer.getDefaultFont());

        this.clearLogsButton = new ClearLogsButton();
        this.clearLogsButton.setDimensions(new Dimension(x, 320, BUTTONWIDTH, BUTTONHEIGHT));
        this.clearLogsButton.setText("Clear Logs", gameContainer.getDefaultFont());

        this.backButton = new BackButton(0);
        this.backButton.setDimensions(new Dimension(x, 375, BUTTONWIDTH, BUTTONHEIGHT));
        this.backButton.setText("Back", gameContainer.getDefaultFont());
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        algorithmChooseButton.draw(gameContainer, g);
        genSolButton.draw(gameContainer, g);
        saveLevelButton.draw(gameContainer, g);
        loadLevelButton.draw(gameContainer, g);
        clearLogsButton.draw(gameContainer, g);
        backButton.draw(gameContainer, g);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input in = gameContainer.getInput();
        boolean click = in.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);

        this.inputSummary = new InputSummary(stateBasedGame, in.getMouseX(), in.getMouseY(), click);

        backButton.update(inputSummary);
        algorithmChooseButton.update(inputSummary);
        saveLevelButton.update(inputSummary);
        loadLevelButton.update(inputSummary);
        clearLogsButton.update(inputSummary);
        genSolButton.update(inputSummary);
    }

    @Override
    public int getID() {
        return 1;
    }
}
