/*
Henry Weisman & Emerson Wright
CS 321 Project 2

This class will take an input file with infix expressions
and convert to postfix expressions

If an output file is specified, then it will write all
postfix expressions there, otherwise it is printed

Usage:
java Postfix <input> <output>
*/

import java.io.*;
import java.util.ArrayList;

public class Postfix {
    private ArrayList<String> input;
    private ArrayList<String> output;
    private String filePath;
    
        
    public Postfix(String fileName){
        this.filePath = fileName;
        this.input = this.readFile();
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
            while(line!=null){
                strings.add(line);
                line = f.readLine();
            }
            f.close();
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
        //for each expression
        for(int i = 0; i<tokens.size();i++){
            String[] line = tokens.get(i);
            String token = line[0];
            int j = 1;

            //loops till end of expression
            while(!(token.equals(";"))){
                // a ")" means a entire operation is at the top of the stack
                if(token.equals(")")){
                    String right = stack.pop();
                    String op = stack.pop();
                    String left = stack.pop();
                    stack.push(left+" "+right+" "+op);
                }
                //push everything but "("
                else if(!(token.equals("("))){
                    stack.push(token);
                }
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
        Postfix p = new Postfix(argv[0]);
        ArrayList<String> out = p.getOutput();
        String str = "";
        for(int i = 0; i < out.size(); i++ ){
            str += out.get(i);
            str += "\n";
        }


        if (argv.length > 1){
            String path = argv[1];
            File f = new File(path);
            try{
                f.createNewFile();
                FileWriter w = new FileWriter(path);
                w.write(str);
                w.close();
            }catch (IOException e){
                System.out.println("error writing output to given path");
            }
        }else{
            System.out.println(str);
        }

    }

}
