
/* 3.4 Queue via Stacks: Implement a MyQueue class which implements a queue using two stacks.
 * 
 * Hints: 
 * -> The major difference between a queue and a stack is the order of elements. A queue removes the oldest item and a stack removes the newest item. How could you remove the oldest item from a stack if you only had access to the newest item?
 * -> We can remove the oldest item from a stack by repeatedly removing the newest item (inserting those into the temporary stack) until we get down to one element. Then, after we've retrieved the newest item, putting all the elements back. The issue with this is that doing several pops in a row will require O(N) work each time. Can we optimize for scenarios where we might do several pops in a row?
 * 
 * Solution: Modify peek() and pop() to go in reverse order
 */
import java.util.Stack;
 
public class MyQueue<T> {

    Stack<T> stackNewest, stackOldest;

    public MyQueue() {
        stackNewest = new Stack<T>();
        stackOldest = new Stack<T>();
    }

    public int size() {
        return stackNewest.size() + stackOldest.size();
    }

    public void add(T value) {
        /* Push onto stackNewest, which always has the newest elements on top */
        stackNewest.push(value);
    }

    /* Move elements from stackNewest into StackOldest. This is usually done so that we can do operations on stackOldest */
    private void shiftStacks() {
        if (stackOldest.isEmpty()) {
            while (!stackNewest.isEmpty()) {
                stackOldest.push(stackNewest.pop());
            }
        }
    }
    
    public T peek() {
        shiftStacks();  // Ensure stackOldest has the current elements
        return stackOldest.peek(); // retrieve the oldest item
    }

    public T remove() {
        shiftStacks();  // Ensure stackOldest has the current elements
        return stackOldest.pop();
    }
}
