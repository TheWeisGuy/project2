import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.io.*;


public class Assembler {
    //Input should be an arraylist of postfix expressions from Postfix.java
    private ArrayList<String> input;
    //Stores all the assembly outputs
    private ArrayList<String> output;

    private String filePath;
    //Stores what temporary register # is to be used next
    private int tempNo = 1;
    
    //Translates the postfix operations (key) to assembly operations (value)
    private Hashtable<String,String> ops = new Hashtable<String,String>(4);
    

    public Assembler(ArrayList<String> input){
        ops.put("*", "ML");
        ops.put("+", "AD");
        ops.put("-", "SB");
        ops.put("/", "DV");
        this.output = this.toAssembly(input);
    }

    /*
    This function will convert postfix expressions to assembly code

    Parameters: postfix - Arraylist of Postfix expressions from Postfix class
    Returns: Arraylist of equivilent assembly code
    */
    private ArrayList<String> toAssembly(ArrayList<String> postfix){
        //stores outputs
        ArrayList<String> out = new ArrayList<String>();
        //For every expression
        for(int i = 0; i<postfix.size(); i++){
            //Stack will mantain the order of operations as we parse postfix
            Stack<String> stack = new Stack<String>();
            //Getting the first expression
            String[] line = postfix.get(i).split(" ");
            //For each token of the postfix expression
            for(int j = 0; j<line.length;j++){
                String token = line[j];
                //If the token is not an operator
                if(!(this.ops.containsKey(token))){
                    stack.push(token);
                }
                else{
                    //Op found, getting two operands
                    //We must consider an operand may be a assembly expression
                    //In that case, it will contian "TMP#", which we must extract
                    String temp = stack.pop().trim();
                    //finding position of last TMP (the operand we want)
                    int n = temp.lastIndexOf("TMP");
                    String right;
                    //if TMP not found, temp is operand
                    if(n==-1){
                        right = temp;
                    }
                    else{
                        right = temp.substring(n, temp.length());
                    }
                    //Same Process for the right
                    String left;
                    String temp2 = stack.pop().trim();
                    Integer  m = temp2.lastIndexOf("TMP");
                    if(m==-1){
                        left = temp2;
                    }
                    else{
                        left = temp2.substring(m, temp2.length());
                    }
                    //Handing off to evaluate to construct assembly for this part
                    stack.push(this.evaluate(left, token, right));
                }
            }
            //What is left on the stack is full assembly
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
        return(out);
    }


    public ArrayList<String> getOutput(){
        return(this.output);
    } 

    public static void main(String argv[]){
        Postfix p = new Postfix(argv[0]);
        Assembler a = new Assembler(p.getOutput());
        ArrayList<String> out = a.getOutput();
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
