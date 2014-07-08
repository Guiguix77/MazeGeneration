import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by Florian on 24.01.14.
 */
public class Cell {
    public int gridPosX, gridPosY;
    public float ratio;

    public boolean wall = false, solvingVisited, genVisited, allWallsIntact, way, start = false, goal = false, currentCell = false;

    public Cell[] solvingChild = new Cell[8];
    public Cell solvingPrevious, genBacktrack;
    public Cell north, east, south, west;
    public Cell dfsNorth, dfsEast, dfsSouth, dfsWest;

    public Cell(int x, int y, float ratio, boolean wall) {
        this.gridPosX = x;
        this.gridPosY = y;
        this.ratio = ratio;
        this.wall = wall;
    }

    public void dfsCheckNeighbors(){
        if(!this.wall){
            if(this.gridPosY > 0){
                if(Panel.cells[this.gridPosX][this.gridPosY - 1].wall == false){
                    this.dfsNorth = null;
                    this.dfsNorth = Panel.cells[this.gridPosX][this.gridPosY - 1];
                }
            }
            if(this.gridPosX != Panel.CELLS - 1){
                if(Panel.cells[this.gridPosX + 1][this.gridPosY].wall == false){
                    this.dfsEast = null;
                    this.dfsEast = Panel.cells[this.gridPosX + 1][this.gridPosY];
                }
            }
            if(this.gridPosY != Panel.CELLS - 1){
                if(Panel.cells[this.gridPosX][this.gridPosY + 1].wall == false){
                    this.dfsSouth = null;
                    this.dfsSouth = Panel.cells[this.gridPosX][this.gridPosY + 1];
                }
            }
            if(this.gridPosX > 0){
                if(Panel.cells[this.gridPosX - 1][this.gridPosY].wall == false){
                    this.dfsWest = null;
                    this.dfsWest = Panel.cells[this.gridPosX - 1][this.gridPosY];
                }
            }
        }
    }

    public void genCheckNeighbors() {
        int walls = 0;
        if(!this.wall){
            if (Panel.cells[this.gridPosX][this.gridPosY - 1].wall && (this.gridPosY != 1)) {
                this.north = Panel.cells[this.gridPosX][this.gridPosY - 2];
                walls++;
            }
            if (Panel.cells[this.gridPosX + 1][this.gridPosY].wall && (this.gridPosX != Panel.CELLS - 2)) {
                this.east = Panel.cells[this.gridPosX + 2][this.gridPosY];
                walls++;
            }
            if (Panel.cells[this.gridPosX][this.gridPosY + 1].wall && (this.gridPosY != Panel.CELLS - 2)) {
                this.south = Panel.cells[this.gridPosX][this.gridPosY + 2];
                walls++;
            }
            if (Panel.cells[this.gridPosX - 1][this.gridPosY].wall && (this.gridPosX != 1)) {
                this.west = Panel.cells[this.gridPosX - 2][this.gridPosY];
                walls++;
            }

            if (walls == 4) {
                allWallsIntact = true;
            } else {
                if (Panel.cells[this.gridPosX][this.gridPosY - 1].wall ||
                        Panel.cells[this.gridPosX + 1][this.gridPosY].wall ||
                        Panel.cells[this.gridPosX][this.gridPosY + 1].wall ||
                        Panel.cells[this.gridPosX - 1][this.gridPosY].wall) {
                    allWallsIntact = true;
                } else {
                    allWallsIntact = false;
                }
            }

            if (Panel.currentGenCell != null) {
                if (Panel.currentGenCell.gridPosX == this.gridPosX && Panel.currentGenCell.gridPosY == this.gridPosY) {
                    this.currentCell = true;
                } else {
                    this.currentCell = false;
                }
            }
        }
    }

    public void solvingCheckNeighbors(boolean diagonal) {
        if (this.gridPosY != 0) {
            if (!Panel.cells[this.gridPosX][this.gridPosY - 1].wall && !Panel.cells[this.gridPosX][this.gridPosY - 1].solvingVisited) {
                solvingChild[0] = Panel.cells[this.gridPosX][this.gridPosY - 1];
            }
        }
        if (!(this.gridPosX == Panel.cells.length - 1) && !Panel.cells[this.gridPosX + 1][this.gridPosY].solvingVisited) {
            if (!Panel.cells[this.gridPosX + 1][this.gridPosY].wall) {
                solvingChild[1] = Panel.cells[this.gridPosX + 1][this.gridPosY];
            }
        }
        if (this.gridPosY != Panel.cells.length - 1 && !Panel.cells[this.gridPosX][this.gridPosY + 1].solvingVisited) {
            if (!Panel.cells[this.gridPosX][this.gridPosY + 1].wall) {
                solvingChild[2] = Panel.cells[this.gridPosX][this.gridPosY + 1];
            }
        }
        if (this.gridPosX != 0) {
            if (!Panel.cells[this.gridPosX - 1][this.gridPosY].wall && !Panel.cells[this.gridPosX - 1][this.gridPosY].solvingVisited) {
                solvingChild[3] = Panel.cells[this.gridPosX - 1][this.gridPosY];
            }
        }
        if(diagonal && this.gridPosX != 0 && this.gridPosY != 0){
            if(!Panel.cells[this.gridPosX - 1][this.gridPosY - 1].wall && !Panel.cells[this.gridPosX - 1][this.gridPosY - 1].solvingVisited){
                solvingChild[4] = Panel.cells[this.gridPosX - 1][this.gridPosY - 1];
            }
        }
        if(diagonal && this.gridPosX != Panel.cells.length - 1 && this.gridPosY != 0){
            if(!Panel.cells[this.gridPosX + 1][this.gridPosY - 1].wall && !Panel.cells[this.gridPosX + 1][this.gridPosY - 1].solvingVisited){
                solvingChild[5] = Panel.cells[this.gridPosX + 1][this.gridPosY - 1];
            }
        }
        if(diagonal && this.gridPosX != Panel.cells.length - 1 && this.gridPosY != Panel.cells.length - 1){
            if(!Panel.cells[this.gridPosX + 1][this.gridPosY + 1].wall && !Panel.cells[this.gridPosX + 1][this.gridPosY + 1].solvingVisited){
                solvingChild[6] = Panel.cells[this.gridPosX + 1][this.gridPosY + 1];
            }
        }
        if(diagonal && this.gridPosX != 0 && this.gridPosY != Panel.cells.length - 1){
            if(!Panel.cells[this.gridPosX - 1][this.gridPosY + 1].wall && !Panel.cells[this.gridPosX - 1][this.gridPosY + 1].solvingVisited){
                solvingChild[7] = Panel.cells[this.gridPosX - 1][this.gridPosY + 1];
            }
        }
    }

    public void draw(Graphics g, boolean mazeOrSolving) {
       if (mazeOrSolving) {
            if (this.wall) {
                g.setColor(Color.black);
                g.fillRect(this.gridPosX * this.ratio, this.gridPosY * this.ratio, this.ratio, this.ratio);
                g.setColor(Color.black);
            }else if (this.currentCell) {
                g.setColor(Color.red);
                g.fillRect(this.gridPosX * this.ratio, this.gridPosY * this.ratio, this.ratio, this.ratio);
                g.setColor(Color.black);
            } else if (this.genVisited) {
                g.setColor(Color.white);
                g.fillRect(this.gridPosX * this.ratio, this.gridPosY * this.ratio, this.ratio, this.ratio);
                g.setColor(Color.black);
            } else {
                g.setColor(Color.black);//White for making the points visible
                g.fillRect(this.gridPosX * this.ratio, this.gridPosY * this.ratio, this.ratio, this.ratio);
                g.setColor(Color.black);
            }
        } else {
            if(this.solvingVisited){
                g.setColor(Color.darkGray);
                g.fillRect(this.gridPosX * this.ratio, this.gridPosY * this.ratio, this.ratio, this.ratio);
                g.setColor(Color.black);
            }else if(!this.wall){
                g.drawRect(this.gridPosX * this.ratio, this.gridPosY * this.ratio, this.ratio, this.ratio);
            }else{
                g.fillRect(this.gridPosX * this.ratio, this.gridPosY * this.ratio, this.ratio, this.ratio);
            }

            if(this.way){
                g.setColor(Color.yellow);
                g.fillRect(this.gridPosX * ratio, this.gridPosY * ratio, this.ratio, this.ratio);
                g.setColor(Color.black);
            }
            if(this.goal){
                g.setColor(Color.red);
                g.fillRect(this.gridPosX * this.ratio, this.gridPosY * this.ratio, this.ratio, this.ratio);
                g.setColor(Color.black);
            }
        }

    }
}
