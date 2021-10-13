import java.io.File;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.*;


public class Parser {
    private ArrayList<String> input;
    private ArrayList<String> output;
    private String filePath;
    public Parser(String fileName){
        this.filePath = fileName;
        this.input = this.readFile();
        System.out.println(this.tokenize().get(0)[0]);
        this.output = this.toPostfix(this.tokenize());
    }

    /**
     * reades file and returns list of strings of lines
     * @return ArrayList of each line
     */
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
        ArrayList<String> out = new ArrayList<String>();
        for(int i = 0; i<tokens.size();i++){
            String[] line = tokens.get(i);
            String token = line[0];
            int j = 1;

            //crashes here
            //while loop does not meet end condition
            while(token!=";"){
                if(token == ")"){
                    String right = stack.pop();
                    String op = stack.pop();
                    String left = stack.pop();
                    stack.push(left+right+op);
                }
                else if(token!="("){
                    stack.push(token);
                }
                System.out.println(token);
                token = line[j];
                j++;
                
            }
            if(stack.getLength()>1){
                System.out.println("Invalid Infix expression at line: "+Integer.toString(i+1));
            }
            else{
                out.add(stack.pop());
            }
        }
        return(out);
    }

    public ArrayList<String> getOutput(){
        return(this.output);
    }

    public static void main(String argv[]){
        Parser parse = new Parser(argv[0]);
        System.out.println(parse.getOutput().toString());
    }

}
