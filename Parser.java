import java.io.File;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.*;

/*This class reads in infix expressions and 
 *Outputs equivilent assembly code
 *
 *Takes the path of the input file, which may contain
 * multiple infix expressions on differnt lines separated by ";"
 *
 * */
public class Parser {
    //input and output store all the infix and assembly/outfix respectivly
    private ArrayList<String> input;
    private ArrayList<String> output;
    private String filePath;

    //Takes the file path fo the input file and will write to an output file
    //NOTE: What is the output file?
    public Parser(String fileName){
        this.filePath = fileName;
        this.input = this.readFile();
        this.output = this.toPostfix(this.tokenize());
    }

    /**
     * reades file and returns list of strings of lines
     * @return ArrayList of each line
     */
    private ArrayList<String> readFile(){
        try{
            //Reading in file and putting each line in ArrayList
            FileReader fr = new FileReader(new File(this.filePath));
            BufferedReader f = new BufferedReader(fr);
            ArrayList<String> strings = new ArrayList<String>();
            String line = f.readLine();
            strings.add(line);
            while(line==null){
                strings.add(line);
            }
            f.close();
            fr.close();
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
        for(int i = 0; i < this.input.size(); i++){

            String line = this.input.get(i);
            //Spliting by spaces in the line
            //NOTE: Unsure if the \n is also added to tokens, may cause problems
            String[] tokens = line.split(" ");
            out.add(tokens);
        }

        return out;
    }

    /**
     * @param tokens ArrayList of token strings
     * @return ArrayList of outfix strings
     */
    private ArrayList<String> toPostfix(ArrayList<String[]> tokens){
        Stack<String> stack = new Stack<String>();
        //out will hold a list of all infix expressions
        ArrayList<String> out = new ArrayList<String>();
        //loops throught all the tokenized infix expressions
        for(int i = 0; i<tokens.size();i++){
            //Getting the current infix
            String[] line = tokens.get(i);
            //initiating loop varables
            String token = line[0];
            int j = 1;
            while(!token.equals(";")){
                if(token.equals(")")){
                    //end of operation, pushing operation onto stack
                    String right = stack.pop();
                    String op = stack.pop();
                    String left = stack.pop();
                    stack.push(String.format("%s %s %s", left, right, op));
                }
                else if(!token.equals("(")){
                    stack.push(token);
                }
                token = line[j].strip();
                j++;
                
            }
            //For help debugging, the stack should always have a size of 1
            if(stack.getLength()>1){
                System.out.println("Invalid Infix expression at line: "+Integer.toString(i+1));
            }
            else{
                out.add(stack.pop());
            }
        }
        return(out);
    }

    //returns outputs
    public ArrayList<String> getOutput(){
        return(this.output);
    }

    public static void main(String argv[]){
        Parser parse = new Parser(argv[0]);
        System.out.println(parse.getOutput().toString());
    }

}
