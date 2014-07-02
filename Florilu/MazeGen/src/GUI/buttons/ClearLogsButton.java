package GUI.buttons;

import GUI.buttons.util.AGUIFeatures;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.io.File;

/**
 * Created by Florian on 25.04.14.
 */
public class ClearLogsButton extends AGUIFeatures {
    public ClearLogsButton() throws SlickException {

    }

    @Override
    public void doWhenClick(StateBasedGame stateBasedGame) {
        System.out.println("HEY");
        File logsFolder = new File("logs");
        if(logsFolder.isDirectory()){
            File[] logs = logsFolder.listFiles();
            for(int i = 0; i < logs.length; i++){
                logs[i].delete();
            }
        }
    }
}
