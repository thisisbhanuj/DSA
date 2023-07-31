package ds.stack;

import utility.Employee;

import java.util.EmptyStackException;

public class ArrayStack{
    private Employee[] stack;
    private int top;

    public ArrayStack (int capacity){
        stack = new Employee[capacity];
    }

    public void push(Employee employee){
        if (stack.length == top) {
            Employee[] additional = new Employee[stack.length * 2];
            System.arraycopy(stack, 0, additional, 0, stack.length);
            stack = additional;
        }
        stack[top++] = employee;
    }

    public Employee pop(){
        if (isEmpty()){
            throw new EmptyStackException();
        }
        Employee data = stack[--top];
        stack[top] = null;
        return data;
    }

    public Employee peek(){
        return stack[top-1];
    }

    public boolean isEmpty(){
        if (stack.length == 0) return true;
        return false;
    }

    public void printStack(){
        for (int i = 0; i < top; i++) {
            System.out.println("Stack Values : " + stack[i]);
        }
    }
}