package GUI.buttons;

import GUI.buttons.util.AGUIFeatures;
import GUI.buttons.util.Level;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Florian on 04.05.14.
 */
public class SaveLevelButton extends AGUIFeatures {



    public SaveLevelButton() throws SlickException {
    }

    @Override
    public void doWhenClick(StateBasedGame stateBasedGame) {

        System.out.println("HEY");

        /*File saveFolder = null;
        int files = 0;
        if(!Level.checkChangedLevelState()){
            saveFolder = new File("saves");
            if(!saveFolder.exists()){
                saveFolder.mkdir();
            }
            files = saveFolder.listFiles().length;

            File save = new File(saveFolder + "/save" + files + ".save");
            try{
                save.createNewFile();

                BufferedWriter writer = new BufferedWriter(new FileWriter(save));
                char[][] level = Level.level;
                for(int y = 0; y < level.length; y++){
                    for(int x = 0; x < level.length; x++){
                        writer.write(level[x][y]);
                    }
                    writer.newLine();
                }
                writer.close();

            }catch (IOException e){
                e.printStackTrace();
            }
        }*/
    }
}
