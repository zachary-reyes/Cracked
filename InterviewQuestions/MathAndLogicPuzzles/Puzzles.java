import java.util.Random;

public class Puzzles {

    // check if a number is prime
    public static boolean primeNaive(int n) {
        if (n < 2) {
            return false;
        }
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    // check if a number is prime
    public static boolean primeSlightlyBetter(int n) {
        if (n < 2) {
            return false;
        }
        int sqrt = (int) Math.sqrt(n);
        for (int i = 2; i < sqrt; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    // generate a list of primes
    public static boolean[] sieveOfEratosthenes(int max) {
        boolean[] flags = new boolean[max + 1];
        int count = 0;

        init(flags);    // Set all flags to true other than 0 and 1
        int prime = 2;

        while (prime <= Math.sqrt(max)) {
            /* Cross off remaining multiples of prime */
            crossOff(flags, prime);

            /* Find next value which is true */
            prime = getNextPrime(flags, prime);
        }

        return flags;
    }

    public static void crossOff(boolean[] flags, int prime) {
        /* Cross off remaining multiples of prime. We can start with (prime*prime), because if we have a k * prime, where k < prime, this value would have already been crossed off in a prior iteration. */
        for (int i = prime * prime; i < flags.length; i+= prime) {
            flags[i] = false;
        }
    }

    public static int getNextPrime(boolean[] flags, int prime) {
        int next = prime + 1;
        while (next < flags.length && !flags[next]) {
            next++;
        }
        return next;
    }

    public static void init(boolean[] flags) {
        for (int i = 2; i < flags.length; i++) {
            flags[i] = true;
        }
    }

    /* 6.1 The Heavy Pill: You have 20 bottles of pills. 19 bottles have 1.0 gram pills, but one has pills of weight 1.1 grams. Given a scale that provides an exact measurement, how would you find the heavy bottle? You can only use the scale once.
    * 
    * Hints: #186, #252, #319, #387
    * -> You can only use the scale once. This means that all, or almost all, of the bottles must be used. They also must be handled in different ways or else you couldn't distinguish between them.
    * -> What happens if you put one pill from each bottle on the scale? What if you put two pills from each bottle on the scale?
    * -> Imagine there were just three bottles and one had heavier pills. Suppose you put different numbers of pills from each bottle on the scale (for example, bottle 1 has 5 pills, bottle 2 has 2 pills, and bottle 3 has 9 pills). What would the scale show?
    * -> You should be able to have an equation that tells you the heavy bottle based on the weight.
    * 
    * Approach: 
    *  Use sticky constraints as a clue: Can only use scale once
    *  Think about if we only had two bottles...putting a pill from each still wouldn't allow us to know which bottle we want, but by taking 1 pill from Bottle 1 and 2 pills from bottle 2, we can conclude:
    *  If Bottle 1 were the heavy pills, we'd get 3.1 grams. If bottle 2 were the heavier, we'd get 3.2 grams
    * Thus. 
    * RULE: take one pill from Bottle #1, two pills from Bottle #2, three pills from Bottle #3 and so on. Weight the mix of pills. If all pills were one gram each, the scale would read 210 grams (1 + 2 + ... + 20) = 20 * 21 / 2 = 210...any overage comes from the extra 0.1 gram pills.
    * 
    * (weight - 210 grams)/0.1 grams
    * 
    * So if set of pills weighed 211.3 grams, Bottle #13 would have the heavy pills
    * 
    */
    public static int pills(double measuredWeight) {
        // Calculate the difference from the expected weight (210 grams)
        double weightDifference = measuredWeight - 210.0;

        // Calculate the bottle number using the equation (weight - 210) / 0.1
        int heavyBottleNumber = (int) (weightDifference / 0.1);

        return heavyBottleNumber;
    }

    /* 6.2 Basketball: You have a basketball hoop and someone says that you can play one of two games. Game 1: You get one shot to make the hoop. Game 2: You get three shots and you have to make two of three shots. If p is the probability of making a particular shot, for which values of p should you pick one game or the other?
    * 
    *  Hints: #181, #239, #284, #323
    * -> Calculate the probability of winning the first game and winning the second game, then compare them.
    * -> To calculate the probability of winning the second game, start with calculating the probability of making the first hoop,the second hoop,and not the third hoop.
    * -> If two events are mutually exclusive (they can never occur simultaneously), you can add their probabilities together. Can you find a set of mutually exclusive events that repre­sent making two out of three hoops?
    * -> The probability of making two out of three shots is probability(make shot 1, make shot 2, miss shot 3) + probability(make shot 1, miss shot 2, make shot 3) + probability(miss shot 1, make shot 2, make shot 3) + probability(make shot 1, make shot 2, make shot 3).
    * 
    * Approach:
    *    Apply straightforward probability laws by comparing the probabilities of winning each game. 
    * 
    *    P(1) = p
    *    P(2) = s(2,3) + s(3,3)  // The probability of making exactly two shots out of three OR making all three shots
    *    
    *    s(3,3) = p^3
    *    +
    *    s(2,3) = P(making 1 and 2, not 3) + P(making 2 and 3, not 1) = 3(1 - p)p^2
    *    =
    *    3p^2 - 2p^3
    * 
    *    So, which game do we play?
    * 
    *    Play Game 1 if P(1) > P(2): p > 3p^2 - 2p^3 ??
    * 
    *    So, we should play Game 1 if 0 < p < 0.5 and Game 2 if 0.5 < p < 1
    * 
    *    If p = 0, 0.5, or 1, then P(1) = P(2), so it doesn't matter which game we play
    */

    /* 6.3 Dominos: There is an 8x8 chessboard in which two diagonally opposite corners have been cut off. You are given 31 dominos, and a single domino can cover exactly two squares. Can you use the 31 dominos to cover the entire board? Prove your answer (by providing an example or showing why it's impossible).
    * 
    *  Hints: #367, #397 
    * -> Picture a domino laying down on the board. How many black squares does it cover? How many white squares?
    * -> How many black squares are there on the board? How many white squares?
    * 
    * Approach: 
        For each row we place, we'll alwyas have one domino that need to poke into the next row.
        No matter what we try, it is impossible to lay down all the dominoes
        
        The cleaner proof: 
            Initially, 32 white and 32 black squares on the board.
            Removing opposite corners (which must be the same color) leaves us with 30 of one color and 32 of the other

            Each domino we place will take up one white and one black square. Therefore, 31 dominos will take up 31 white squares and 31 black squares exactly. Hence, it is impossible.
    */

    /* 6.4 Ants on a Triangle: There are three ants on different vertices of a triangle. What is the probability of collision (between any two or all of them) if they start walking on the sides of the triangle? Assume that each ant randomly picks a direction, with either direction being equally likely to be chosen, and that they walk at the same speed. Similarly, find the probability of collision with n ants on an n-vertex polygon. 
    * 
    * Hints: #157, #195, #296
    * -> In what cases will they not collide?
    * -> The only way they won't collide is if all three are walking in the same direction. What's the probability of all three walking clockwise?
    * -> You can think about this either as the probability(3 ants walking clockwise) + proba­bility(3 ants walking counter-clockwise). Or, you can think about it as: The first ant picks a direction. What's the probability of the other ants picking the same direction?
    *
    * Approach: 
        The ants will collide if any of them are moving towards each other. So compute the probability that they are all moving in the same direction and work backwards from there.

        With 3 ants, each having the ability to move in two directions:
            P(clockwise) = (1/2)^3
            P(counter clockwise) = (1/2)^3
            P(same direction) = (1/2)^3 + (1/2)^3 = 1/4
        Thus, the probability of collision equals the probability of the ants not moving in the same direction:
            P(collision) = 1 - P(same direction) = 1 - 1/4 = 3/4

        To generalize, 
            P(clockwise) = (1/2)^n
            P(counter clockwise) = (1/2)^n
                P(same direction) = (1/2)^n + (1/2)^n = (1/2)^(n - 1)
            Thus, the probability of collision equals the probability of the ants not moving in the same direction:
                P(collision) = 1 - P(same direction) = 1 - (1/2)^(n - 1) 
    */

    /* 6.5 Jugs of Water: You have a five-quart jug, a three-quart jug, and an unlimited supply of water (but no measuring cups). How would you come up with exactly four quarts of water? Note that the jugs are oddly shaped such that filling up exactly "half" of the jug would be impossible.
     * 
     *  Hints: 149, 379, 400
     * -> Play around with the jugs of water, pouring water back and forth, and see if you can measure anything other than 3 quarts or 5 quarts. That's a start.
     * -> If you fill the 5-quart jug and then use it to fill the 3-quart jug, you'll have two quarts left in the 5-quart jug. You can either keep those two quarts where they are, or you can dump the contents of the smaller jug and pour the two quarts in there.
     * -> Once you've developed a way to solve this problem, think about it more broadly. If you are given a jug of size X and another jug of size Y, can you always use it to measure Z?
     * 
     *  Approach: 
     *      The question has a math/comp sci root: If the two jug sizes are relatively prime, you can measure any value between one and the sum of the jug sizes
     * 
     * 1. Filled 5-quart jug 
     * 2. Filled 3-quart with 5-quarts contents
     * 3. Dumped 3-quart.
     * 4. Fill 3-quart with 5-quart's contents.
     * 5. Filled 5-quart
     * 6. Fill remainder of 3-quart with 5-quart.
     * 7. Done! We have 4 quarts.
     */

    /* 6.6 Blue-Eyed Island: A bunch of people are living on an island, when a visitor comes with a strange order: all blue-eyed people must leave the island as soon as possible. There will be a flight out at 8:00 pm every evening. Each person can see everyone else's eye color, but they do not know their own (nor is anyone allowed to tell them). Additionally, they do not know how many people have blue eyes, although they do know that at least one person does. How many days will it take the blue-eyed people to leave?
     * 
     * Hints: 218, 282, 341, 370
     * -> This is a logic problem, not a clever word problem. Use logic/math/algorithms to solve it.
     * -> Suppose there were exactly one blue-eyed person. What would that person see? When wouId they leave?
     * -> Now suppose there were two blue-eyed people. What would they see? What would they know? When would they leave? Remember your answer from the prior hint. Assume they know the answer to the earlier hint.
     * -> Build up from this. What if there were three blue-eyed people? What if there were four blue-eyed people?
     * 
     *  Apply Base Case & Build Approach:
     *      Case c = 1: Exactly one person has blue eyes
     *          The person will look around, not see anyone else with blue eyes, and conclude he has blue eyes. Leave that day
     * 
     *      Case c = 2: Exactly two people have blue eyes
     *          A person sees another person with blue eyes, but doesn't know if c = 1 or 2. They know what happens if c = 1 , so if that blue eyed person is still there day 2, c = 2, and thus first person also has blue eyes. They both leave the second night.
     * 
     *      Case c > 2: General Case
     *          If c men have blue eyes, it take c nights for the blue-eyed men to leave. All will leave on the same night.
     */

    /* 6.7 The Apocalypse: In the new post-apocalyptic world, the world queen is desperately concerned about the birth rate. Therefore, she decrees that all families should ensure that they have one girl or else they face massive fines. If all families abide by this policy-that is, they have continue to have children until they have one girl, at which point they immediately stop-what will the gender ratio of the new generation be? (Assume that the odds of someone having a boy or a girl on any given pregnancy is equal.) Solve this out logically and then write a computer simulation of it.
     * 
     * Hints: 154, 160, 171, 188, 201
     * -> Observe that each family will have exactly one girl.
     * -> Think about writing each family as a sequence of Bs and Gs.
     * -> You can attempt this mathematically, although the math is pretty difficult. You might find it easier to estimate it up to families of, say, 6 children. This won't give you a good mathematical proof, but it might point you in the right direction of what the answer might be.
     * -> Logic might be easier than math. Imagine we wrote every birth into a giant string of Bs and Gs. Note that the groupings of families are irrelevant for this problem. What is the probability of the next character added to the string being a B versus a G?
     * -> Observe that biology hasn't changed; only the conditions under which a family stops having kids has changed. Each pregnancy has a 50% odds of being a boy and a 50% odds of being a girl.
     * 
     * Approach
        In fact, we don't really care about the groupings of families because we're concerned about the population as a whole. As soon as a child is born, we can just append its gender (B or G) to the string. What are the odds of the next character being a G? Well, if the odds of having a boy and girl is the same, then the odds of the next character being a G is 50%. Therefore, roughly half of the string should be Gs and half should be Bs, giving an even gender ratio. This actually makes a lot of sense. Biology hasn't been changed. Half of newborn babies are girls and half are boys. Abiding by some rule about when to stop having children doesn't change this fact. Therefore, the gender ratio is 50% girls and 50% boys.
     * 
     * Simulation: When run on a large value of families n, you should get something very close to 0.5
     */
    public static double runNFamilies(int n) {
        int boys = 0;
        int girls = 0;
        for (int i = 0; i < n; i++) {
            int[] genders = runOneFamily();
            girls += genders[0];
            boys += genders[1];
        }
        return girls / (double)(boys + girls);
    }

    public static int[] runOneFamily() {
        Random random = new Random();
        int boys = 0;
        int girls = 0;
        while (girls == 0) {    // until we have a girl
            if (random.nextBoolean()) {  // girl
                girls += 1;
            } else {
                boys += 1;
            }
        }
        int[] genders = {girls, boys};
        return genders;
    }

    /* 6.8  The Egg Drop Problem: There is a building of 100 floors. If an egg drops from the Nth floor or above, it will break. If it's dropped from any floor below, it will not break. You're given two eggs. Find N, while minimizing the number of drops for the worst case.
     * 
     * Hints: 156, 233, 294, 333, 357, 374, 395
     * ->  This is really an algorithm problem, and you should approach it as such. Come up with a brute force, compute the worst-case number of drops, then try to optimize that.
     * -> As a first approach, you might try something like binary search. Drop it from the 50th floor, then the 75th, then the 88th, and so on. The problem is that if the first egg drops at the 50th floor, then you'll need to start dropping the second egg starting from the 1st floor and going up.This could take, at worst, 50 drops (the 50th floor drop, the 1st floor drop, the 2nd floor drop, and up through the 49th floor drop). Can you beat this?
     * -> It's actually better for the first drop to be a bit lower. For example, you could drop at the 10th floor, then the 20th floor, then the 30th floor, and so on. The worst case here will be 19 drops (10, 20, ..., 100, 91, 92, ..., 99). Can you beat that? Try not randomly guessing at different solutions. Rather, think deeper. How is the worst case defined? How does the number of drops of each egg factor into that?
     * -> If we drop Egg 1 at fixed intervals (e.g., every 10 floors), then the worst case is the worst case for Egg 1 + the worst case for Egg 2. The problem with our earlier solutions is that as Egg 1 does more work, Egg 2 doesn't do any less work. Ideally, we'd like to balance this a bit. As Egg 1 does more work (has survived more drops), Egg 2 should have less work to do. What might this mean?
     * -> Try dropping Egg 1 at bigger intervals at the beginning and then at smaller and smaller intervals. The idea is to keep the sum of Egg 1 and Egg 2's drops as constant as possible. For each additional drop that Egg 1 takes, Egg 2 takes one fewer drop. What is the right interval?
     * -> Let X be the first drop of Egg1. This means that Egg 2 would do X -  1 drops if Egg 1 broke. We want to try to keep the sum of Egg 1 and Egg 2's drops as constant as possible. If Egg 1 breaks on the second drop, then we want Egg 2 to do X - 2 drops. If Egg 1 breaks on the third drop,then we want Egg2 to do X - 3 drops. This keeps the sum of Egg 1 and Egg 2 fairly constant. What is X?
     * -> I got 14 drops in the worst case. What did you get?
     * 
     * Generalized Approach: (x(x + 1))/2 = # of floors
     * 
     * Simulation: As in many other maximizing/minimizing problems, the key is "worst case balancing"
    */
    public static int breakingPoint = 72; // Set the breaking point for testing
    public static int countDrops = 0;

    public static boolean drop(int floor) {
        countDrops++;
        return floor >= breakingPoint;
    }

    // 
    public static int findBreakingPoint(int floors) {
        int interval = 14;
        int previousFloor = 0;
        int egg1 = interval;

        /* Drop egg1 at decreasing intervals */
        while (!drop(egg1) && egg1 <= floors) {
            interval -= 1;
            previousFloor = egg1;
            egg1 += interval;
        }

        /* Drop egg2 at 1 unit increments */
        int egg2 = previousFloor + 1;
        while (egg2 < egg1 && egg2 <= floors && !drop(egg2)) {
            egg2 += 1;
        }

        /* If it didn't break, return -1 */
        return egg2 > floors ? -1 : egg2;
    }

    /* 6.9 100 Lockers: There are 100 closed lockers in a hallway. A man begins by opening all 100 lockers. Next, he closes every second locker. Then, on his third pass, he toggles every third locker (closes it if it is open or opens it if it is closed). This process continues for 100 passes, such that on each pass i, the man toggles every ith locker. After his 100th pass in the hallway, in which he toggles only locker #100, how many lockers are open? 
     * 
     * Hints: 139, 172, 264, 306
     * -> Given a specific door x, on which rounds will it be toggled (open or closed)?
     * -> In which cases would a door be left open at the end of the process?
     * -> Note: If an integer x is divisible by a, and b = x / a, then x is also divisible by b. Does this mean that all numbers have an even number of factors?
     * -> The number 3 has an even number of factors (1 and 3). The number 12 has an even number of factors (1, 2, 3, 4, 6, 12). What numbers do not? What does this tell you about the doors?
     * 
     * Approach: Think through what it means for a door to be toggled.
     * 
     * Question #1: For which rounds is a door toggled (open or closed)?
     *  
     * Answer: A door n is toggled once for each factor of n, including itself and 1. That is, door 15 is toggled on rounds 1, 3, 5, and 15.
     * 
     * Question #2: When would a door be left open?
     * 
     * Answer: A door is left open if the number of factors (which we will call x) is odd. You can think about this by pairing factors off as an open and a close. If there's one remaining, the door will be open.
     * 
     * Question #3: When would x be odd?
     * 
     * Answer: The value x is odd if n is a perfect square. Here's why: pair n's factors by their complements. For example, if n is 36, the factors are (1, 36), (2, 18), (3, 12), (4, 9). (6, 6). Note that (6, 6) only contributes one factor, thus giving n an odd number of factors
     * 
     * Question: How many perfect squares are there? 
     * 
     * Answer: There are 1O perfect squares. You could count them (1, 4, 9, 16, 25, 36, 49, 64, 81, 100), or you could simply realize that you can take the numbers 1 through 10 and square them:
     *  
     * 1*1, 2*2, 3*3, ..., 10*10
     * 
     * Therefore, there are 10 lockers open at the end of this process. 
    */

    /* 6.10 Poison: You have 1000 bottles of soda, and exactly one is poisoned. You have 10 test strips which can be used to detect poison. A single drop of poison will turn the test strip positive permanently. You can put any number of drops on a test strip at once and you can reuse a test strip as many times as you'd like (as long as the results are negative). However, you can only run tests once per day and it takes seven days to return a result. How would you figure out the poisoned bottle in as few days as possible?
     * 
     * Hints: 146, 163, 183, 191, 205, 221, 230, 241, 249
     * -> Solution 1: Start with a simple approach. Can you just divide up the bottles into groups? Remember that you can't re-use a test strip once it is positive, but you can reuse it as long as it's negative.
     * -> Solution 1: There is a relatively simple approach that works in 28 days, in the worst case. There are better approaches though.
     * -> Solution 2: Why do we have such a time lag between tests and results? There's a reason the question isn't phrased as just "minimize the number of rounds of testing." The time lag is there for a reason.
     * -> Solution 2: Consider running multiple tests at once.
     * -> Solution 2: Think about trying to figure out the bottle, digit by digit. How can you detect the first digit in the poisoned bottle? What about the second digit? The third digit?
     * -> Solution 2: Be very careful about edge cases. What if the third digit in the bottle number matches the first or second digit?
     * -> Solution 2: You can run an additional day of testing to check digit 3 in a different way. But again, be very careful about edge cases here.
     * -> Solution 3: Think about each test strip as being a binary indicator for poisoned vs. non­ poisoned.
     * -> Solution 3: If each test strip is a binary indicator, can we map ,integer keys to a set of 1O binary indicators such that each key has a unique configuration (mapping)?
     * 
     * Naive Approach: (28 days)
     * 1. Divide bottles across available test strips, one drop per test strip. (That is, groups of 100)
     * 2. After 7 days, check results
     * 3. Focusing now On the positive test strip: Repeat with testing of this group til we find poison
     * 
     * FOLLOW UP: Write code to simulate your approach
    */

    public static void main(String[] args) {
        // Test primeNaive
        System.out.println("primeNaive(17): " + Puzzles.primeNaive(17));
        System.out.println("primeNaive(20): " + Puzzles.primeNaive(20));

        // Test primeSlightlyBetter
        System.out.println("primeSlightlyBetter(17): " + Puzzles.primeSlightlyBetter(17));
        System.out.println("primeSlightlyBetter(20): " + Puzzles.primeSlightlyBetter(20));

        // Test sieveOfEratosthenes
        boolean[] primeFlags = Puzzles.sieveOfEratosthenes(100);
        System.out.println("Prime numbers up to 100:");
        for (int i = 2; i < primeFlags.length; i++) {
            if (primeFlags[i]) {
                System.out.print(i + " ");
            }
        }
        System.out.println();

        // Find the Heaviest Pill Bottle
        int bottle = pills(211.3);
        System.out.println("The bottle with the heavy pills is " + bottle);

        // Run the simulation to estimate the gender ratio
        int numFamilies = 1000000;  // Number of families to simulate
        double genderRatio = runNFamilies(numFamilies);
        System.out.println("Estimated gender ratio after simulating " + numFamilies + " families: " + genderRatio);

        // Test the Egg Drop Problem
        int totalFloors = 100;
        int result = findBreakingPoint(totalFloors);

        if (result == -1) {
            System.out.println("The breaking point couldn't be found within " + totalFloors + " floors.");
        } else {
            System.out.println("The breaking point is at or above floor " + result + ".");
            System.out.println("Total drops used: " + countDrops);
        }
    }
}