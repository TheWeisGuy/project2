import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.*;
import java.util.Hashtable;


public class Parser {
    private ArrayList<String> input;
    private ArrayList<String> output;
    private String filePath;

    private Hashtable<String,String> ops = new Hashtable<String,String>(4);
    
        
    public Parser(String fileName){
        ops.put("*", "ML");
        ops.put("+", "AD");
        ops.put("-", "SB");
        ops.put("/", "DV");
        this.filePath = fileName;
        this.input = this.readFile();
        this.output = this.toPostfix(this.tokenize());
        this.output = this.toAssembly(this.output);
        
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

            while(!(token.equals(";"))){
                if(token.equals(")")){
                    String right = stack.pop();
                    String op = stack.pop();
                    String left = stack.pop();
                    stack.push(left+" "+right+" "+op);
                }
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

    private ArrayList<String> toAssembly(ArrayList<String> postfix){
        ArrayList<String> out = new ArrayList<String>();
        for(int i = 0; i<postfix.size(); i++){
            Stack<String> stack = new Stack<String>();
            String[] line = postfix.get(i).split(" ");
            System.out.println(postfix.get(i));
            for(int j = 0; j<line.length;j++){
                String token = line[j];
                if(!(this.ops.containsKey(token))){
                    System.out.println("Token: "+token);
                    stack.push(token);
                }
                else{
                    String temp = stack.pop().trim();
                    System.out.println("Temp: "+temp);
                    int n = temp.lastIndexOf("TMP");
                    String right;
                    if(n==-1){
                        
                        right = temp;
                    }
                    else{
                        right = temp.substring(n, temp.length()-1);
                    }
                    String left;
                    temp = stack.pop().trim();
                    n = temp.lastIndexOf("TMP");
                    if(n==-1){
                        left = temp;
                    }
                    else{
                        left = temp.substring(n, temp.length()-1);
                    }
                    System.out.println(right);
                    System.out.println("Left: "+left);
                    System.out.println(this.evaluate(left, token, right));
                    stack.push(this.evaluate(left, token, right));
                }
            }
            out.add(stack.pop());
        }


        return(out);

    }

    private String evaluate(String left, String op, String right){
        int n = 1;
        if(right.contains("TMP")){
            n = Integer.parseInt(right.substring(3)) + 1;
        }

        String out = String.format("LD %s\n" , left);
        out += String.format("%s %s\n", this.ops.get(op), right);
        out += String.format("ST TMP%d\n", n);
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
