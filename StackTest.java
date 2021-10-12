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
    }
}

