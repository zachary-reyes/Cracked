
/* 3.2 Stack Min: How would you design a stack which, in addition to push and pop, has a function min which returns the minimum element? Push, pop and min should all operate in 0(1) time. 
 * 
 * Hints:
 * -> Observe that the minimum element doesn't change very often. It only changes when a smaller element is added, or when the smallest element is popped.
 * -> What if we kept track of extra data at each stack node? What sort of data might make it easier to solve the problem?
 * -> Consider having each node know the minimum of its "substack" (all the elements beneath it, including itself).
 * 
 * Solution #1:
 * -> When you push an element onto the stack, the element is given the current minimum. It sets its "local min" to be the min.
 * -> This way, we are keeping track of the minimum at each state.
 * 
 * Issues: If we have a large stack, we waste a lot of space by keeping track of the min for every single element.
 *  -> Use an additional stack to keep track of mins
 */

import java.util.Stack;

public class StackWithMin extends Stack<NodeWithMin> {
    public void push(int value) {
        int newMin = Math.min(value, min());
        super.push(new NodeWithMin(value, newMin));
    }

    public int min() {
        if (this.isEmpty()) {
            return Integer.MAX_VALUE;   // Error value
        } else {
            return peek().min;
        }
    }
}

class NodeWithMin {
    public int value;
    public int min;

    public NodeWithMin(int v, int min) {
        value = v;
        this.min = min;
    }
}
