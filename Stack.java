/*
CS321 Project 02
Henry Weisman
Emerson Wright

Stack implemented with Linked Lists
*/

public class Stack<T>{

    private class Node{
        /*
        This class defies the Node that compose the linked list
        The tail is the node below the current on in the stack, the 
        firt node added will have no tail
        Each node stores data of type T
        */
        private Node tail;
        private T data;
        
        public Node(T data){
            this.data = data;
            this.tail = null;
        }

        public Node(T data, Node tail){
            this.data = data;
            this.tail = tail;
        }

        public T getData(){
            return this.data;
        }
        
        public Node getTail(){
            return this.tail;
        }

        public void setTail(Node tail){
            this.tail = tail;
        }
    }

    //Top is the last Node added, and the first returned
    private Node top;
    private int len;

    public Stack(){
        this.top = null;
        this.len = 0;
    }

    //Adds data to the top of the stack
    public void push(T data){
        Node n = new Node(data, this.top);
        this.top = n;
        this.len++;
    }

    //Returns the top of the stack
    public T pop(){
        T data = this.top.getData();
        this.top = this.top.getTail();
        this.len--;
        return data;
    }

    //Returns the size of the stack
    public int getLength(){
        return this.len;
    }
    public int size(){
        return this.len;
    }

    //Empties list
    public void clear(){
        this.top = null;
        this.len = 0;
    }

    //Prints the content of the stack
    public String toString(){
        String str = "";
        Node next = this.top;
        while(next != null){
            str += next.getData().toString() + "\n";
            next = next.getTail();
        }
        return str;
    }
}
