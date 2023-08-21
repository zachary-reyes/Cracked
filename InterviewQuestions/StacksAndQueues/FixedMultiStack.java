
/* 3.1 Three in One: Describe how you could use a single array to implement three stacks 
 * 
 * Hints: 
 * -> A stack is simply a data structure in which the most recently added elements are removed first. Can you simulate a single stack using an array? Remember that there are many possible solutions, and there are tradeoffs of each  
 * -> We could simulate three stacks in an array by just allocating the first third of the array to the first stack, the second third to the second stack, and the final third to the third stack. One might actually be much bigger than the others, though. Can we be more flexible with the divisions?
 * -> If you want to allow for flexible divisions, you can shift stacks around. Can you ensure that all available capacity is used?
 * Try thinking about the array as circular, such that the end of the array "wraps around" to the start of the array
 * 
 * Solution #1: Fixed Division
 * -> Divide the array in three equal parts and allow the individual stack to grow in that limited space.
 * -> Stack 1: [0, n/3)
 * -> Stack 2: [n/3, 2n/3)
 * -> Stack 3:[2n/3, n)
 * 
 * Solution #2: Flexible Division: 
 * -> Allow the stack blocks to be flexible in size. When one stack exceeds its initial capacity, we grow the allowable capacity and shift elements as necessary.
 * -> Our array is circular, such that the final stack may start at the end of the array and wrap around to the beginning
 * -> This would be far too complex for an interview (reference pg. 229)
*/

import java.util.EmptyStackException;

public class FixedMultiStack {

    private class FullStackException extends Exception {
        public FullStackException(String message) {
            super(message);
        }
    }

    private int numberOfStacks = 3;
    private int stackCapacity;
    private int[] values;
    private int[] sizes;

    public FixedMultiStack(int stackSize) {
        stackCapacity = stackSize;
        values = new int[stackSize * numberOfStacks];
        sizes = new int[numberOfStacks];
    }

    /* Push value onto stack */
    public void push(int stackNum, int value) throws FullStackException {
        /* Check that we have space for the next element */
        if (isFull(stackNum)) {
            throw new FullStackException("No space");
        }

        /* Increment stack pointer and then update top value */
        sizes[stackNum]++;
        values[indexOfTop(stackNum)] = value;
    }

    /* Pop item from top stack */
    public int pop(int stackNum) {
        if (isEmpty(stackNum)) {
            throw new EmptyStackException();
        }

        int topIndex = indexOfTop(stackNum);
        int value = values[topIndex];   // get top
        values[topIndex] = 0;   // Clear
        sizes[stackNum]--;  // Shrink
        return value;
    }

    /* Return top element */
    public int peek(int stackNum) {
        if (isEmpty(stackNum)) {
            throw new EmptyStackException();
        }
        return values[indexOfTop(stackNum)];
    }

    /* Return if stack is empty */
    public boolean isEmpty(int stackNum) {
        return sizes[stackNum] == 0;
    }

    /* Return if stack is full */
    public boolean isFull(int stackNum) {
        return sizes[stackNum] == stackCapacity;
    }

    /* Returns index of the top of the stack */
    private int indexOfTop(int stackNum) {
        int offset = stackNum * stackCapacity;
        int size = sizes[stackNum];
        return offset + size - 1;
    }
    
    public static void main(String[] args) throws FixedMultiStack.FullStackException {
        FixedMultiStack stack = new FixedMultiStack(3);

        
            stack.push(0, 1);
            stack.push(0, 2);
            stack.push(0, 3);

            stack.push(1, 4);
            stack.push(1, 5);

            stack.push(2, 6);

            System.out.println("Peek stack 0: " + stack.peek(0)); // Should print 3
            System.out.println("Peek stack 1: " + stack.peek(1)); // Should print 5
            System.out.println("Peek stack 2: " + stack.peek(2)); // Should print 6

            System.out.println("Pop stack 0: " + stack.pop(0)); // Should print 3
            System.out.println("Pop stack 1: " + stack.pop(1)); // Should print 5
            System.out.println("Pop stack 2: " + stack.pop(2)); // Should print 6

            System.out.println("Is stack 0 empty? " + stack.isEmpty(0)); // Should print false
            System.out.println("Is stack 1 empty? " + stack.isEmpty(1)); // Should print false
            System.out.println("Is stack 2 empty? " + stack.isEmpty(2)); // Should print true

            System.out.println("Is stack 0 full? " + stack.isFull(0)); // Should print false
            System.out.println("Is stack 1 full? " + stack.isFull(1)); // Should print false
            System.out.println("Is stack 2 full? " + stack.isFull(2)); // Should print false
        
    }
}
