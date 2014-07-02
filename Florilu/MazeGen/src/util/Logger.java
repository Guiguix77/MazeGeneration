package util;

import com.sun.corba.se.spi.activation._InitialNameServiceImplBase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Florian on 21.04.14.
 */
public class Logger {

    private File currentLogFile = null;
    private BufferedWriter writer = null;
    private boolean activatedLogging = false;
    private ArrayList<String> linesToWrite = new ArrayList<>();
    public boolean used = false;
    public int steps = 0;

    public ArrayList<Integer> stepList = new ArrayList<>();

    public Logger(){

    }

    public void createNewLogFile(){
        File folder = new File("logs");
        if(!folder.exists()){
            folder.mkdir();
        }
        String logFileName = ("log" + folder.listFiles().length + ".log");
        File logFile = new File(folder + "/" + logFileName);
        this.currentLogFile = logFile;
    }

    public void initWriter(){
        try{
            this.writer = new BufferedWriter(new FileWriter(this.currentLogFile));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void writeInLog(String text){
        linesToWrite.add(text);
        this.steps++;
    }

    public void writeSteps(){
        try{
            this.writer.newLine();
            this.writer.newLine();
            this.writer.write("STEPS: " + this.steps);
            this.stepList.add(this.steps);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void closeWriter(){
        try{
            currentLogFile.createNewFile();
            for(int i = 0; i < linesToWrite.size(); i++){
                writer.write(linesToWrite.get(i));
                writer.newLine();
            }
            this.writeSteps();
            this.writer.close();
            this.currentLogFile = null;
            this.used = true;
            this.steps = 0;
        }catch (IOException e){
            e.printStackTrace();;
        }
    }

    public void setActivateLog(boolean bool){
        this.activatedLogging = bool;
    }

    public void printStepList(){
        System.out.println();
        for(int i = 0; i < stepList.size(); i++){
            System.out.print(this.stepList.get(i) + ", ");
        }
    }
}
