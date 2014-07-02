package GUI.buttons.util;

/**
 * Created by Florian on 04.05.14.
 */
public class Level {
    public static char[][] level;

    public static char[][] preLevel;

    public static void init(int width, int height){
        level = new char[width][height];
        preLevel = new char[width][height];
    }

    public static void update(char[][] panelLevel){
        preLevel = level;
        level = panelLevel;
    }

    public static boolean checkChangedLevelState(){
        if(preLevel == level){
            return false;
        }else{
            return true;
        }
    }
}
