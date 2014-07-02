package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Florian on 14.05.14.
 */
public class AnalyzeLog {

    public static final int FIRST = 0;
    public static final int LAST = -1;

    public static void analyzeBacktracks(int logNumber){
        File[] logs = new File("logs").listFiles();

        int logNumberInt = 0;
        if(logNumber == FIRST){
            logNumberInt = FIRST;
        }else if(logNumber == LAST){
            logNumberInt = logs.length;
        }else{
            logNumberInt = logNumber;
        }

        File log = logs[logNumberInt];

        try{
            BufferedReader reader = new BufferedReader(new FileReader(log));
            String line = null;
            int count = 0;
            while((line = reader.readLine()) != null){
                if(line != null){
                    char[] lineInChars = line.toCharArray();
                    char[] mayBacktrack = new char[12];
                    if(lineInChars.length != 0){
                        for(int i = 0; i < 12; i++){
                            mayBacktrack[i] = lineInChars[i];
                        }
                        if(new String(mayBacktrack).equals("Backtracking")){
                            count++;
                        }
                    }
                }
            }
            System.out.println(count);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
