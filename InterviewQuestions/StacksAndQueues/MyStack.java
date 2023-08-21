import java.util.EmptyStackException;
import java.util.Stack;

public class MyStack<T> {

    private static class StackNode<T> {
        private T data;
        private StackNode<T> next;

        public StackNode(T data) {
            this.data = data;
        }
    }

    private StackNode<T> top;

    public T pop() {
        if (top == null) throw new EmptyStackException();
        T item = top.data;
        top = top.next;
        return item;
    }

    public void push(T item) {
        StackNode<T> t = new StackNode<T>(item);
        t.next = top;
        top = t;
    }

    public T peek() {
        if (top == null) throw new EmptyStackException();
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    /* 3.5 Sort Stack: Write a program to sort a stack such that the smallest items are on the top. You can use an additional temporary stack, but you may not copy the elements into any other data structure (such as an array). The stack supports the following operations: push, pop, peek, and isEmpty. 
    * 
    * Hints:
    * -> One way of sorting an array is to iterate through the array and insert each element into a new array in sorted order. Can you do this with a stack?
    * -> Imagine your secondary stack is sorted. Can you insert elements into it in sorted order? You might need some extra storage. What could you use for extra storage?
    * -> Keep the secondary stack in sorted order, with the biggest elements on the top. Use the primary stack for additional storage.
    * 
    * Solution: 
    * -> Sort s1 by inserting each element from s1 in order into s2
    * -> Use s1 extra storage when inserting (check notes for diagram, also pg. 237)
    *
    * -> If allowed unlimited stacks, can implement modified quicksort or mergesort
    * 
    * Time Complexity: O(N^2)
    * Space Complexity: O(N)
    */
    void sort(Stack<Integer> s) {
        Stack<Integer> r = new Stack<Integer>();
        while (!s.isEmpty()) {
            /* Insert each element in s in sorted order into r */
            int tmp = s.pop();
            while (!r.empty() && r.peek() > tmp) {
                s.push(r.pop());
            }
            r.push(tmp);
        }

        /* Copy the elements from r back into s */
        while (!r.isEmpty()) {
            s.push(r.pop());
        }
    }

}

