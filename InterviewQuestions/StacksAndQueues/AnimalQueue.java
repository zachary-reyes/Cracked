
/* Animal Shelter: An animal shelter, which holds only dogs and cats, operates on a strictly "first in, first out" basis. People must adopt either the "oldest" (based on arrival time) of all animals at the shelter, or they can select whether they would prefer a dog or a cat (and will receive the oldest animal of that type). They cannot select which specific animal they would like. Create the data structures to maintain this system and implement operations such as enqueue, dequeueAny, dequeueDog, and dequeueCat. You may use the built-in Linked list data structure.
 * 
 * Hints: 
 * -> We could consider keeping a single linked list for dogs and cats, and then iterating through it to find the first dog (or cat). What is the impact of doing this?
 * -> Let's suppose we kept separate lists for dogs and cats. How would we find the oldest animal of any type? Be creative!
 * -> Think about how you'd do it in real life. You have a list of dogs in chronological order and a list of cats in chronological order. What data would you need to find the oldest animal? How would you maintain this data?
 * 
 * Solution #1: Use separate queues for dogs and cats, and place them within a wrapper class. We then store some sort of timestamp to make when each animal was enqueued.
 * 
 * Solution #2: 
 * -> Maintain a single queue...making dequeueAny easy, but making us iterate through the queue to find the first dog or cat for dequeueDog and dequeueCat
 */

import java.util.LinkedList;

abstract class Animal {
    private int order;
    protected String name;
    public Animal(String n) {name = n;}
    public void setOrder(int ord) {order = ord;}
    public int getOrder() {return order;}

    /* Compare order of animals to return the older item */
    public boolean isOlderThan(Animal a) {
        return this.order < a.getOrder();
    }
}

public class AnimalQueue {

    private class Dog extends Animal {
        public Dog(String n) {super(n);}
    }

    public class Cat extends Animal {
        public Cat(String n) {super(n);}
    }

    LinkedList<Dog> dogs = new LinkedList<Dog>();
    LinkedList<Cat> cats = new LinkedList<Cat>();
    private int order = 0;  // acts as timestamp

    public void enqueue(Animal a) {
        /* Order is used as a sort of timestamp, so that we can compare the insertion order of a dog to a cat */
        a.setOrder(order);
        order++;

        // if (a instanceof Dog) dogs.addLast(Dog(a));          NOTE: For some reason dont work
        // else if (a instanceof Cat) cats.addLast(Cat(a));
    }

    public Animal dequeueAny() {
        /* Look at tops of dog and cat queues, and pop the queue with the oldest value */
        if (dogs.size() == 0) {
            return dequeueCats();
        } else if (cats.size() == 0) {
            return dequeueDogs();
        }
        Dog dog = dogs.peek();
        Cat cat = cats.peek();
        if (dog.isOlderThan(cat)) {
            return dequeueDogs();
        } else {
            return dequeueCats();
        }
    }

    public Dog dequeueDogs() {
        return dogs.poll();
    }

    public Cat dequeueCats() {
        return cats.poll();
    }
}


