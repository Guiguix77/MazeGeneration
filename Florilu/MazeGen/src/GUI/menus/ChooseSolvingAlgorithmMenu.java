package GUI.menus;

import GUI.buttons.AlgorithmAStarButton;
import GUI.buttons.AlgorithmBFSButton;
import GUI.buttons.AlgorithmDFSButton;
import GUI.buttons.BackButton;
import GUI.buttons.util.Dimension;
import GUI.buttons.util.InputSummary;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class ChooseSolvingAlgorithmMenu extends BasicGameState{

    public AlgorithmAStarButton aStarButton;
    public BackButton backButton;
    public AlgorithmDFSButton dfsButton;
    public AlgorithmBFSButton bfsButton;

    private InputSummary inputSummary;
    private final int BUTTONWIDTH = 200, BUTTONHEIGHT = 50;

    @Override
    public int getID() {
        return 2;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

        float x = (gameContainer.getWidth() - 200) / 2;

        aStarButton = new AlgorithmAStarButton();
        aStarButton.setText("AStar = " + aStarButton.selected, gameContainer.getDefaultFont());
        aStarButton.setDimensions(new Dimension(x, 100, BUTTONWIDTH, BUTTONHEIGHT));

        dfsButton = new AlgorithmDFSButton();
        dfsButton.setText("DFS = " + dfsButton.selected, gameContainer.getDefaultFont());
        dfsButton.setDimensions(new Dimension(x, 155, BUTTONWIDTH, BUTTONHEIGHT));

        bfsButton = new AlgorithmBFSButton();
        bfsButton.setText("BFS = " + bfsButton.selected, gameContainer.getDefaultFont());
        bfsButton.setDimensions(new Dimension(x, 210, BUTTONWIDTH, BUTTONHEIGHT));

        backButton = new BackButton(1);
        backButton.setText("Back", gameContainer.getDefaultFont());
        backButton.setDimensions(new Dimension(x, 265, BUTTONWIDTH, BUTTONHEIGHT));
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        aStarButton.draw(gameContainer, g);
        dfsButton.draw(gameContainer, g);
        bfsButton.draw(gameContainer, g);
        backButton.draw(gameContainer, g);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input in = gameContainer.getInput();
        float x1 = in.getMouseX();
        float y1 = in.getMouseY();

        inputSummary = new InputSummary(stateBasedGame, x1, y1, in.isMousePressed(Input.MOUSE_LEFT_BUTTON));

        aStarButton.update(inputSummary);
        backButton.update(inputSummary);
        dfsButton.update(inputSummary);
        bfsButton.update(inputSummary);
    }
}