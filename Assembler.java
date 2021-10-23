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


public class Assembler {
    private ArrayList<String> input;
    private ArrayList<String> output;
    private String filePath;
    private int tempNo;

    private Hashtable<String,String> ops = new Hashtable<String,String>(4);
    
        
    public Assembler(ArrayList<String> input){
        ops.put("*", "ML");
        ops.put("+", "AD");
        ops.put("-", "SB");
        ops.put("/", "DV");
        System.out.println("Assembly: ");
        this.output = this.toAssembly(input);
    }

    private ArrayList<String> toAssembly(ArrayList<String> postfix){
        ArrayList<String> out = new ArrayList<String>();
        for(int i = 0; i<postfix.size(); i++){
            Stack<String> stack = new Stack<String>();
            String[] line = postfix.get(i).split(" ");
            for(int j = 0; j<line.length;j++){
                String token = line[j];
                if(!(this.ops.containsKey(token))){
                    stack.push(token);
                }
                else{
                    String temp = stack.pop().trim();
                    int n = temp.lastIndexOf("TMP");
                    String right;
                    if(n==-1){
                        right = temp;
                    }
                    else{
                        right = temp.substring(n, temp.length());
                    }
                    String left;
                    String temp2 = stack.pop().trim();
                    Integer  m = temp2.lastIndexOf("TMP");
                    if(m==-1){
                        left = temp2;
                    }
                    else{
                        left = temp2.substring(m, temp2.length());
                    }
                    stack.push(this.evaluate(left, token, right));
                }
            }
            out.add(stack.pop());
            System.out.println("");
            this.tempNo=1;
        }
        return(out);
    }

    private String evaluate(String left, String op, String right){
        String out = String.format("LD %s\n" , left);
        out += String.format("%s %s\n", this.ops.get(op), right);
        out += String.format("ST TMP%d\n", this.tempNo++);
        System.out.print(out);
        return(out);
    }


    public ArrayList<String> getOutput(){
        return(this.output);
    } 

    public static void main(String argv[]){
        Postfix p = new Postfix(argv[0]);
        ArrayList<String> out = p.getOutput();
        String str = "";
        Assembler assemble = new Assembler(out);

    }
}
