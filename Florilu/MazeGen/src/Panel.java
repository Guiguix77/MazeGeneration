import GUI.buttons.util.Level;
import GUI.buttons.util.NoSolvingAlgorithmSelectedException;
import GUI.menus.ChooseSolvingAlgorithmMenu;
import javafx.scene.layout.Pane;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import util.AnalyzeLog;
import util.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by Florian on 24.01.14.
 */
public class Panel extends BasicGameState{

    private static StateManager stateManager;

    public static AppGameContainer container;

    private final boolean startSearch = true;

    public static final int CELLS = 151;
    public static int width = 1000, height = 1000;

    public int updateInterval = 1;

    public static Cell[][] cells = new Cell[CELLS][CELLS];
    public static Queue cellStack = new LinkedList();
    public ArrayList<Cell> solvingVisited = new ArrayList<>();
    public ArrayList<Cell> genVisited = new ArrayList<>();

    public int totalCells = ((CELLS - 1) / 2) * ((CELLS - 1) / 2);

    public static boolean maze = false;
    public boolean run = true;
    public boolean search = false;
    public boolean drawWay = false;

    public static float ratio = (float)width / (float)cells.length;

    public static Cell start, goal, end;
    public ArrayList<Cell> path = new ArrayList<>();

    public static Cell currentGenCell;

    public boolean wall;
    public boolean mousePressing = false;

    public boolean displayingMaze = false;
    public boolean mazeSolved = false;
    public boolean foundSolution = false;
    public boolean diagonal = false;

    private Logger logger;

    private final boolean automaticallyGenerateMazes = false;
    private final int mazesToGenerate = 10;
    private int generatedMazes = 0;

    public static void main(String[] args){
        try{
            stateManager = new StateManager();
        }catch (SlickException e){
            e.printStackTrace();
        }
    }

    public Panel(){

    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        gameContainer.getGraphics().setBackground(Color.white);
        gameContainer.getGraphics().setColor(Color.black);
        gameContainer.setShowFPS(false);
        gameContainer.setAlwaysRender(true);

        Level.init(CELLS, CELLS);

        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells.length; j++){
                cells[i][j] = new Cell(i, j, ratio, false);
            }
        }
        this.logger = new Logger();
        this.logger.createNewLogFile();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {

        Input in = gameContainer.getInput();

        if(in.isKeyPressed(Input.KEY_C)){
            clear();
        }

        gameContainer.setMaximumLogicUpdateInterval(updateInterval);

        if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
            float x1 = in.getMouseX() / ratio;
            float y1 = in.getMouseY() / ratio;
            if(start == null){
                start = new Cell((int)x1, (int)y1, ratio, false);
                start.start = true;
                cells[start.gridPosX][start.gridPosY].start = true;
                cellStack.add(cells[start.gridPosX][start.gridPosY]);
            }else if(goal == null){
                goal = new Cell((int)x1, (int)y1, ratio, false);
                cells[goal.gridPosX][goal.gridPosY].goal = true;
            }else{
                changeCellAtPosition((int)x1, (int)y1);
            }

            if(start != null && goal != null){
                this.wall = cells[(int)x1][(int)y1].wall;
            }
        }

        if(in.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
            if(start != null && goal != null){
                mousePressing = true;
                float x1 = in.getMouseX() / ratio;
                float y1 = in.getMouseY() / ratio;
                cells[(int)x1][(int)y1].wall = this.wall;
            }
        }else{
            mousePressing = false;
        }

        if(in.isKeyPressed(Input.KEY_ENTER)){
            if(mazeSolved && displayingMaze){
                clear();
                createGraph();
                maze = true;
            }else if(!maze){
                this.search = true;
            }
        }

        if(in.isKeyPressed(Input.KEY_M) && !maze){
            createGraph();
            maze = true;
            this.logger.initWriter();
        }

        if(in.isKeyPressed(Input.KEY_S)){
            clear();
            for(int i = 0; i < cells.length; i++){
                for(int j = 0; j < cells.length; j++){
                    Random random = new Random();
                    if(random.nextInt(3) == 0){
                        cells[i][j].wall = true;
                    }
                }
            }
            setDefaultStartAndEnd(true);
        }

        if(in.isKeyPressed(Input.KEY_ADD)){
            if(updateInterval > 1){
                updateInterval -= 1;
            }
        }
        if(in.isKeyPressed(Input.KEY_SUBTRACT)){
            if(updateInterval < 20){
                updateInterval += 1;
            }
        }

        if(in.isKeyPressed(Input.KEY_P)){
            if(!gameContainer.isPaused()){
                gameContainer.pause();
            }else{
                gameContainer.resume();
            }
        }

        if(in.isKeyPressed(Input.KEY_D)){
            this.diagonal = !this.diagonal;
        }

        if(in.isKeyPressed(Input.KEY_ESCAPE)){
            stateBasedGame.enterState(1);
        }

        if(in.isKeyPressed(Input.KEY_A)){
            AnalyzeLog.analyzeBacktracks(AnalyzeLog.FIRST);
        }

        if(in.isKeyPressed(Input.KEY_I)){
            this.createImage();
        }

        if(this.search){
            if(start == null || goal == null){
                this.search = false;
            }else{
                if(stateManager.algorithmMenu.dfsButton.selected){

                }else if(stateManager.algorithmMenu.aStarButton.selected){

                }else if(stateManager.algorithmMenu.bfsButton.selected){
                    if(solvingVisited.size() < 1){
                        solvingVisited.add(cells[start.gridPosX][start.gridPosY]);
                        cells[start.gridPosX][start.gridPosY].solvingVisited = true;
                        cellStack.add(cells[start.gridPosX][start.gridPosY]);
                    }else{
                        if(!cellStack.isEmpty()){
                            Cell current = (Cell)cellStack.remove();
                            Cell child;
                            if(current.goal){
                                this.end = current;
                                this.drawWay = true;
                                this.search = false;
                                getPath(false, null);
                                if(displayingMaze){
                                    mazeSolved = true;
                                }
                                foundSolution = true;
                            }else{
                                while((child = getUnvisitedChildCell(current)) != null){
                                    child.solvingVisited = true;
                                    child.solvingPrevious = current;
                                    solvingVisited.add(child);
                                    cellStack.add(child);
                                    current.solvingCheckNeighbors(diagonal);
                                    getPath(true, child);
                                }
                            }
                        }
                    }
                }else{
                    try{
                        throw new NoSolvingAlgorithmSelectedException();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        if(this.maze){
            if(this.logger.used){
                this.logger.createNewLogFile();
                this.logger.initWriter();
                this.logger.used = false;
            }
            if(cells.length != 0){
                logger.writeInLog("Checking neighbors");
                for(int i = 0; i < CELLS; i++){
                    for(int j = 0; j < CELLS; j++){
                        cells[i][j].genCheckNeighbors();
                    }
                }

                if(genVisited.size() < 1){
                    genVisited.add(cells[1][1]);
                    currentGenCell = cells[1][1];
                    currentGenCell.genVisited = true;
                    logger.writeInLog("Adding Cell X1 and Y1 to visited Cells");
                }
                if(genVisited.size() != totalCells){
                    ArrayList<Cell> currentNeighbors = new ArrayList<>();
                    if(currentGenCell.north != null && !currentGenCell.north.genVisited && currentGenCell.north.allWallsIntact){
                        currentNeighbors.add(currentGenCell.north);
                        logger.writeInLog("Adding north cell to neighbors at: " + currentGenCell.north.gridPosX + "; " + currentGenCell.north.gridPosY);
                    }
                    if(currentGenCell.east != null && !currentGenCell.east.genVisited && currentGenCell.east.allWallsIntact){
                        currentNeighbors.add(currentGenCell.east);
                        logger.writeInLog("Adding east cell to neighbors at: " + currentGenCell.east.gridPosX + "; " + currentGenCell.east.gridPosY);
                    }
                    if(currentGenCell.south != null && !currentGenCell.south.genVisited && currentGenCell.south.allWallsIntact){
                        currentNeighbors.add(currentGenCell.south);
                        logger.writeInLog("Adding south cell to neighbors at: " + currentGenCell.south.gridPosX + "; " + currentGenCell.south.gridPosY);
                    }
                    if(currentGenCell.west != null && !currentGenCell.west.genVisited && currentGenCell.west.allWallsIntact){
                        currentNeighbors.add(currentGenCell.west);
                        logger.writeInLog("Adding west cell to neighbors at: " + currentGenCell.west.gridPosX + "; " + currentGenCell.west.gridPosY);
                    }

                    if(currentNeighbors.size() > 0){
                        Random random = new Random();
                        int randomValue = random.nextInt(currentNeighbors.size());
                        logger.writeInLog("Generated random number: " + randomValue);
                        Cell neighbor = currentNeighbors.get(randomValue);
                        logger.writeInLog("Picked neighbor: " + neighbor.gridPosX + "; " + neighbor.gridPosY);
                        neighbor.genVisited = true;

                        int wallX = 0;
                        int wallY = 0;

                        //break wall
                        if(neighbor.gridPosX > currentGenCell.gridPosX){
                            wallX = currentGenCell.gridPosX + 1;
                        }else if(neighbor.gridPosX < currentGenCell.gridPosX){
                            wallX = currentGenCell.gridPosX - 1;
                        }else{
                            wallX = currentGenCell.gridPosX;
                        }

                        if(neighbor.gridPosY > currentGenCell.gridPosY){
                            wallY = currentGenCell.gridPosY + 1;
                        }else if(neighbor.gridPosY < currentGenCell.gridPosY){
                            wallY = currentGenCell.gridPosY - 1;
                        }else{
                            wallY = currentGenCell.gridPosY;
                        }

                        cells[wallX][wallY].wall = false;
                        cells[wallX][wallY].genVisited = true;
                        logger.writeInLog("Breaking wall at: " + wallX + "; " + wallY);
                        genVisited.add(cells[wallX][wallY]);
                        neighbor.genBacktrack = currentGenCell;
                        logger.writeInLog("Set current cell to backtrack cell at: " + currentGenCell.gridPosX + "; " + currentGenCell.gridPosY);
                        currentGenCell = neighbor;

                    }else{
                        currentGenCell = currentGenCell.genBacktrack;
                        logger.writeInLog("Backtracking at: " + currentGenCell.genBacktrack.gridPosX + "; " + currentGenCell.genBacktrack.gridPosY);
                    }
                }else{
                    currentGenCell = null;
                    this.maze = false;
                    setDefaultStartAndEnd(true);
                    displayingMaze = true;
                    if((this.search = !this.search) == true){
                        if(this.startSearch == false){
                            this.search = false;
                            createImage();
                        }
                    }
                    logger.writeInLog("Done Maze, closing logger.");
                    logger.closeWriter();
                    if(this.automaticallyGenerateMazes && (generatedMazes < mazesToGenerate)){
                        generatedMazes += 1;
                        clear();
                        createGraph();
                        this.maze = true;
                    }else if(this.logger.stepList.size() != 0){
                        this.logger.printStepList();
                    }else{
                        ;
                    }
                }
            }
        }
        char[][] copyOfLevel = new char[CELLS][CELLS];
        for(int x = 0; x < cells.length; x++){
            for(int y = 0; y < cells.length; y++){
                if(cells[x][y].wall){
                    copyOfLevel[x][y] = 'W';
                }else if(!cells[x][y].wall){
                    copyOfLevel[x][y] = 'D';
                }else if(cells[x][y].start){
                    copyOfLevel[x][y] = 'S';
                }else if(cells[x][y].goal){
                    copyOfLevel[x][y] = 'G';
                }else{
                    copyOfLevel[x][y] = 'D';
                }
            }
        }
        Level.update(copyOfLevel);


    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells.length; j++){
                cells[i][j].draw(g, this.maze);
                if(cellStack.size() > 0){
                    if(cellStack.contains(cells[i][j])){
                        g.setColor(Color.blue);
                        g.fillRect(cells[i][j].gridPosX * ratio, cells[i][j].gridPosY * ratio, ratio, ratio);
                        g.setColor(Color.black);
                    }
                }
                if(cells[i][j].start){
                    g.setColor(Color.green);
                    g.fillRect(cells[i][j].gridPosX * ratio, cells[i][j].gridPosY * ratio, ratio, ratio);
                    g.setColor(Color.black);
                }
            }
        }

        if(this.path.size() > 0){
            for(int i = 0; i < path.size(); i++){
                if(!foundSolution && search){
                    g.setColor(Color.yellow);
                    g.fillRect(path.get(i).gridPosX * ratio, path.get(i).gridPosY * ratio, ratio, ratio);
                    g.setColor(Color.black);
                }else{
                    cells[path.get(i).gridPosX][path.get(i).gridPosY].way = true;
                }
            }
        }
    }

    public Cell getUnvisitedChildCell(Cell cell){
        if(cell.gridPosX != 0 && !Panel.cells[cell.gridPosX - 1][cell.gridPosY].wall && !Panel.cells[cell.gridPosX - 1][cell.gridPosY].solvingVisited && !cellStack.contains(Panel.cells[cell.gridPosX - 1][cell.gridPosY])){
            return Panel.cells[cell.gridPosX - 1][cell.gridPosY];
        }else if(cell.gridPosX != cells.length - 1 && !Panel.cells[cell.gridPosX + 1][cell.gridPosY].wall && !Panel.cells[cell.gridPosX + 1][cell.gridPosY].solvingVisited && !cellStack.contains(Panel.cells[cell.gridPosX + 1][cell.gridPosY])){
            return Panel.cells[cell.gridPosX + 1][cell.gridPosY];
        }else if(cell.gridPosY != 0 && !Panel.cells[cell.gridPosX][cell.gridPosY - 1].wall && !Panel.cells[cell.gridPosX][cell.gridPosY - 1].solvingVisited && !cellStack.contains(Panel.cells[cell.gridPosX][cell.gridPosY - 1])){
            return Panel.cells[cell.gridPosX][cell.gridPosY - 1];
        }else if(cell.gridPosY != cells.length - 1 && !Panel.cells[cell.gridPosX][cell.gridPosY + 1].wall && !Panel.cells[cell.gridPosX][cell.gridPosY + 1].solvingVisited && !cellStack.contains(Panel.cells[cell.gridPosX][cell.gridPosY + 1])){
            return Panel.cells[cell.gridPosX][cell.gridPosY + 1];
        }else if(diagonal && cell.gridPosX != 0 && cell.gridPosY != 0 && !Panel.cells[cell.gridPosX - 1][cell.gridPosY - 1].wall && !Panel.cells[cell.gridPosX - 1][cell.gridPosY - 1].solvingVisited && !cellStack.contains(Panel.cells[cell.gridPosX - 1][cell.gridPosY - 1]) && !possibleWallhack(cell, true, false, false, false)){
            return Panel.cells[cell.gridPosX - 1][cell.gridPosY - 1];
        }else if(diagonal && cell.gridPosX != cells.length - 1 && cell.gridPosY != 0 && !Panel.cells[cell.gridPosX + 1][cell.gridPosY - 1].wall && !Panel.cells[cell.gridPosX + 1][cell.gridPosY - 1].solvingVisited && !cellStack.contains(Panel.cells[cell.gridPosX + 1][cell.gridPosY - 1]) && !possibleWallhack(cell, false, true, false, false)){
            return Panel.cells[cell.gridPosX + 1][cell.gridPosY - 1];
        }else if(diagonal && cell.gridPosX != cells.length - 1 && cell.gridPosY != cells.length - 1 && !Panel.cells[cell.gridPosX + 1][cell.gridPosY + 1].wall && !Panel.cells[cell.gridPosX + 1][cell.gridPosY + 1].solvingVisited && !cellStack.contains(Panel.cells[cell.gridPosX + 1][cell.gridPosY + 1]) && !possibleWallhack(cell, false, false, true, false)){
            return Panel.cells[cell.gridPosX + 1][cell.gridPosY + 1];
        }else if(diagonal && cell.gridPosX != 0 && cell.gridPosY != cells.length - 1 && !Panel.cells[cell.gridPosX - 1][cell.gridPosY + 1].wall && !Panel.cells[cell.gridPosX - 1][cell.gridPosY + 1].solvingVisited && !cellStack.contains(Panel.cells[cell.gridPosX - 1][cell.gridPosY + 1]) && !possibleWallhack(cell, false, false, false, true)){
            return  Panel.cells[cell.gridPosX - 1][cell.gridPosY + 1];
        }else{
            return null;
        }
    }

    public boolean possibleWallhack(Cell cell, boolean upLeft, boolean upRight, boolean bottomRight, boolean bottomLeft){
        if(cell.gridPosX != 0 && cell.gridPosY != 0 && cell.gridPosX != cells.length - 1 && cell.gridPosY != cells.length - 1){
            if(upLeft){
                if(Panel.cells[cell.gridPosX][cell.gridPosY - 1].wall && Panel.cells[cell.gridPosX - 1][cell.gridPosY].wall){
                    return true;
                }else{
                    return false;
                }
            }else if(upRight){
                if(Panel.cells[cell.gridPosX + 1][cell.gridPosY].wall && Panel.cells[cell.gridPosX][cell.gridPosY - 1].wall){
                    return true;
                }else{
                    return false;
                }
            }else if(bottomRight){
                if(Panel.cells[cell.gridPosX + 1][cell.gridPosY].wall && Panel.cells[cell.gridPosX][cell.gridPosY + 1].wall){
                    return true;
                }else{
                    return false;
                }
            }else if(bottomLeft){
                if(Panel.cells[cell.gridPosX][cell.gridPosY + 1].wall && Panel.cells[cell.gridPosX - 1][cell.gridPosY].wall){
                    return true;
                }else{
                    return false;
                }
            }else{
                return true;
            }
        }else{
            return true;
        }
    }

    public void getPath(boolean instant, Cell currentCell){
        if(instant){
            ArrayList<Cell> array = new ArrayList<>();
            Cell current = currentCell;
            while(current.solvingPrevious != cells[start.gridPosX][start.gridPosY]){
                array.add(current.solvingPrevious);
                current = current.solvingPrevious;
            }
            path = array;
        }else{
            ArrayList<Cell> array = new ArrayList<>();
            Cell current = end;
            while(current.solvingPrevious != cells[start.gridPosX][start.gridPosY]){
                array.add(current.solvingPrevious);
                current = current.solvingPrevious;
            }
            path = array;
        }
    }

    public void changeCellAtPosition(int x, int y){
        cells[x][y].wall = !cells[x][y].wall;
    }

    public void createGraph(){
        for(int i = 0; i < CELLS; i++){
            for(int j = 0; j < CELLS; j++){
                cells[i][j].wall = true;
            }
        }

        for(int i = 0; i < CELLS / 2; i++){
            for(int j = 0; j < CELLS / 2; j++){
                cells[(i * 2) + 1][(j * 2) + 1].wall = false;
            }
        }

        for(int i = 0; i < CELLS; i++){
            cells[i][0].wall = true;
            cells[i][CELLS - 1].wall = true;

            cells[0][i].wall = true;
            cells[CELLS - 1][i].wall = true;
        }

        /*for(int i = 0; i < CELLS; i++){
            cells[0][i].wall = true;
            cells[CELLS - 1][i].wall = true;
        }*/
    }

    public void setDefaultStartAndEnd(boolean maze){
        int sX = 0, sY = 0, gX = 0, gY = 0;
        if(maze){
            sX = 1;
            sY = 1;
            gX = cells.length - 2;
            gY = cells.length - 2;
        }else{
            sX = 0;
            sY = 0;
            gX = cells.length - 1;
            gY = cells.length - 1;
        }
        start = cells[sX][sY];
        cells[sX][sY].start = true;
        cells[sX][sY].wall = false;
        goal = cells[gX][gY];
        cells[gX][gY].goal = true;
        cells[gX][gY].wall = false;
    }

    public void clear(){
        start = null;
        goal = null;
        end = null;
        path = new ArrayList<>();
        cellStack = new LinkedList();
        solvingVisited = new ArrayList<>();
        genVisited = new ArrayList<>();
        maze = false;
        search = false;
        drawWay = false;
        displayingMaze = false;
        mazeSolved = false;
        foundSolution = false;
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells.length; j++){
                cells[i][j].wall = false;
                cells[i][j].allWallsIntact = false;
                cells[i][j].currentCell = false;
                cells[i][j].east = null;
                cells[i][j].north = null;
                cells[i][j].west = null;
                cells[i][j].south = null;
                cells[i][j].genVisited = false;
                cells[i][j].goal = false;
                cells[i][j].start = false;
                cells[i][j].genBacktrack = null;
                cells[i][j].solvingChild = new Cell[8];
                cells[i][j].solvingPrevious = null;
                cells[i][j].solvingVisited = false;
                cells[i][j].way = false;

            }
        }
    }

    private void createImage(){
        int width = cells.length;
        int height = cells.length;

        BufferedImage BImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                java.awt.Color blackColor = new java.awt.Color(0, 0, 0);
                java.awt.Color whiteColor = new java.awt.Color(255, 255, 255);
                java.awt.Color yellowColor = new java.awt.Color(java.awt.Color.YELLOW.getRGB());
                if(cells[x][y].wall){
                    BImage.setRGB(x, y, blackColor.getRGB());
                }else{
                    BImage.setRGB(x, y, whiteColor.getRGB());
                }
                if(path.size() > 0){
                    for(int i = 0; i < path.size(); i++){
                        BImage.setRGB(path.get(i).gridPosX, path.get(i).gridPosY, yellowColor.getRGB());
                    }
                }
            }
        }

        File imageFolder = new File("images/");
        if(!imageFolder.exists()){
            imageFolder.mkdir();
        }

        int folderSize = imageFolder.listFiles().length;

        File image = new File(imageFolder + "/image" + folderSize + ".png");

        try{
            ImageIO.write(BImage, "png", image);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getID() {
        return 0;
    }
}
