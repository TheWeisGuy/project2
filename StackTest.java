/** 
 * CS321 Project 02
 * Henry Weisman
 * Emerson Wright
*/

public class StackTest{
    /*
    Creates a stack object and runs its methods to test for functionality
    */
    public static void main(String[] args) {
        Stack<Integer> test = new Stack();
        test.push(5);
        test.push(10);
        test.push(7);
        System.out.println("Stack after pushing 5, 10, then 7:\n "+test.toString());
        System.out.println("Stack length: "+test.getLength());
        System.out.println("Pop top value: "+test.pop());
        System.out.println("Stack after popping:\n "+test.toString());
        System.out.println("Stack length: "+test.getLength());
        test.clear();
        System.out.println("Test after clearing: "+test.toString());
        Stack<String> test2 = new Stack();
        test2.push("String");
        System.out.println("Stack after pushing String:\n "+test2.toString());
        Stack<Double> test3 = new Stack();
        test3.push(4.5);
        Stack<Stack> test4 = new Stack();
        test4.push(test3);
        System.out.println("Stack of stacks:\n"+test4.toString());
    }
}

