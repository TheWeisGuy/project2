import java.io.File;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.*;


public class parser {
    private ArrayList<String> input;
    private String output;
    private String filePath;
    public parser(String fileName){
        this.output = "";
        this.filePath = fileName;
        this.input = this.readFile();
    }

    public ArrayList<String> readFile(){
        try{
            FileReader fr = new FileReader(new File(this.filePath));
            BufferedReader f = new BufferedReader(fr);
            ArrayList<String> strings = new ArrayList<String>();
            String line = f.readLine();
            strings.add(line);
            while(line==null){
                strings.add(line);
            }
            this.input = strings;
            return strings;
        }
        catch(FileNotFoundException ex) {
            System.out.println("Board.read():: unable to open file " + this.filePath );
          }
        catch(IOException ex) {
            System.out.println("Board.read():: error reading file " + this.filePath);
          }
        return null;
    }

    /*
    Splits lines by spaces
    */
    private ArrayList<String[]> tokenize(){


        //Stores the arrays of tokens
        ArrayList<String[]> out = new ArrayList<String[]>();

        //Loops through each line
        for(int i = 0; i < out.size(); i++){

            String line = this.input.get(i);
            //Spliting by spaces in the line
            //NOTE: Unsure if the \n is also added to tokens, may cause problems
            String[] tokens = line.split(" ");
            out.add(tokens);
        }

        return out;
    }

    
    


}
