package ParkingLot;

// Check these out here: https://github.com/careercup/CtCI-6th-Edition/tree/master/Java/Ch%2007.%20Object-Oriented%20Design

public class TODO {
/* 
7.5 Online Book Reader: Design the data structures for an online book reader system.

Hints:
-> Think about all the different functionality a system to read books online would have to support. You don't have to do everything, but you should think about making your assumptions explicit.

Assumptions:
• User membership creation and extension.
• Searching the database of books.
• Reading a book.
• Only one active user at a time
• Only one active book by this user.

OnlineReaderSystem: 
    Library: Stores info about all the books
    UserManager: deals with user management
    Display: refreshes the display

    User 
    Book
*/
 

/* 7.6 Jigsaw: Implement an NxN jigsaw puzzle. Design the data structures and explain an algorithm to solve the puzzle. You can assume that you have a fitsWith method which, when passed two puzzle edges, returns true if the two edges belong together.

Hints: 
-> A common trick when solving a jigsaw puzzle is to separate edge and non-edge pieces. How will you represent this in an object-oriented manner?
-> Think about how you might record the position of a piece when you find it. Should it be stored by row and location?
-> Which will be the easiest pieces to match first? Can you start with those? Which will be the next easiest, once you've nailed those down?

Assumptions:
• Traditional jigsaw puzzle, grid-like, with rows and columns.
• Each piece is located in a single row and column and has four edges
• Each edge comes on one of three types: inner, outer, and flat
• A corner piece, for example, will have two flat-edges and two others edges, which could be inner or outer

• Need to store the position of each piece:
    Absolute Position:: "This piece is located at position (12,23)" ***THIS FOR OUR SOLUTION***
    Relative Position:: "I don't know where this piece is actually located, but I know it is next to this other piece"

Puzzle: 
    Piece(s) 
        Orientation: left, top, right, bottom
        Edge(s)
            Shape: inner, outer, flat

Algorithm: Group Corner pieces, border pieces, and inside pieces.
Once we've done that, we'll pick an arbitrary corner piece and put it in the top left corner. We will then walk through the puzzle in order, filling in piece by piece. At each location, we search through the correct group of pieces to find the matching piece. When we insert the piece into the puzzle, we need to rotate the piece to fit correctly.
*/


/* 7.7 Chat Server: Explain how you would design a chat server. In particular, provide details about the various backend components, classes, and methods. What would be the hardest problems to solve?

Hints: 
-> As always, scope the problem. Are "friendships" mutual? Do status messages exist? Do you support group chat?
-> This is a good problem to think about the major system components or technologies that would be useful.
-> How will you know if a user signs offline?

Assumptions:
• "Friending" is mutual; I am only your contact if you are mine
• Supports both group chat and one-on-one (private) chats
• Will have concept of users, add request status, online status, and messages
• The system would consist of a databse, a set of clients, and a set of servers

UserManager: Serves as a central place for core user actions
    User(s)
        UserStatus
            UserStatusType: Offline, Away, Idle, Available, Busy
        Message
        Conversation
            PrivateChat(s)
            GroupChat(s)
    AddRequest
        RequestStatus: Unread, Read, Accepted, Rejected

What problems would be the hardest to solve (or the most interesting)
    Q1: How do we know if someone is online - I mean, really, really know?
        A1: We may try regularly pinging the client to make sure it's still there

    Q2: How do we deal with conflicting information?
        A2: Info is stored in the computer's memory and some in the database...what if they get out of sync?

    Q3: How do we make our server scale?
        A3: Splitting our data across many servers increases concern about out-of-sync data

    Q4: How do we prevent denial of service (DOS) attacks?
        A4: Clients can push data to us. How to do we prevent them from pushing bad data?
 */

/* 7.8 Othello: Othello is played as follows: Each Othello piece is white on one side and black on the other. When a piece is surrounded by its opponents on both the left and right sides, or both the top and bottom, it is said to be captured and its color is flipped. On your turn, you must capture at least one of your opponent's pieces. The game ends when either user has no more valid moves. The win is assigned to the person with the most pieces. Implement the object-oriented design for Othello.

Hints: 
-> Should white pieces and black pieces be the same class? What are the pros and cons of this?
-> What class should maintain the score? 

Core: The game, the board, the pieces, and the players

Should BlackPiece and WhitePiece be classes?
    No. Each piece may flip back and forth frequently, continuously destroying and creating the same object.
    Have a Piece class with a flag in it representing the current color.

Do we need separate Board and Game classes?
    Keeping the objects separate allows us to have a logical separation between the board and the game.
    The drawback is that we are adding extra layers to our program.

Who Keeps Score?
    Implementation: Board holds this info, since it can be logically grouped with the board

Should Game be a Singleton Class?
    Has the advantage of making it easy for anyone to call a method within Game, without having to pass references.
    Drawback, Can only be instantiated once

Game
    Board
    Piece(s)
        Direction: left, right, up, down
        Color: White, Black
    Player(s)
*/
 
/* 
7.9 Circular Array: Implement a CircularArray class that supports an array-like data structure which can be efficiently rotated. If possible, the class should use a generic type (also called a template), and should support iteration via the standard for (Obj o : circularArray) notation.

Hints:
-> The rotate() method should be able to run in 0(1) time.

Two parts:
    Implement the CircularArray class.
        Shift the elements each time we call rotate(int shiftRight) ... not very efficient
        Instead, create a member variable head which points to what should be conceptually viewed as the start of the circular array. Increment head by shiftRight.

        Be Careful:
            - In Java, we cannot create an array of the generic type. Instead,we must either cast the array or define items to be of type List<T>. For simplicity, we have done the former.
            - The % operator will return a negative value when we do negValue % posVal. For example, -8 % 3 is -2. This is different from how mathematicians would define the modulus function. We must add items.length to a negative index to get the correct positive result.
            - We need to be sure to consistently convert the raw index to the rotated index. For this reason,we have implemented a convert function that is used by other methods. Even the rotate function uses convert. This is a good example of code reuse.

    Support Iteration (Iterator Interface)
        - Modify the CircularArray<T> definition to add implements Iterable<T>. This will also require us to add an iterator() method to CircularArray<T>.
        - Create a CircularArrayiterator<T> which implements Iterator<T>. This will also require us to implement in the CircularArrayIterator, the methods hasNext(), next(), and remove().
*/

/* 7.10 Minesweeper: Design and implement a text-based Minesweeper game. Minesweeper is the classic single-player computer game where an NxN grid has B mines (or bombs) hidden across the grid. The remaining cells are either blank or have a number behind them. The numbers reflect the number of bombs in the surrounding eight cells. The user then uncovers a cell. If it is a bomb, the player loses. If it is a number, the number is exposed. If it is a blank cell, this cell and all adjacent blank cells (up to and including the surrounding numeric cells) are exposed. The player wins when all non-bomb cells are exposed. The player can also flag certain places as potential bombs. This doesn't affect game play, other than to block the user from accidentally clicking a cell that is thought to have a bomb. (Tip for the reader: if you're not familiar with this game, please play a few rounds online first.)
 * 
 * Hints:
 * -> Should number cells, blank cells, and bomb cells be separate classes?
 * -> What is the algorithm to place the bombs around the board?
 * -> To place the bombs randomly on the board:Think about the algorithm to shuffle a deck of cards. Can you apply a similar technique?
 * -> How do you count the number of bombs neighboring a cell? Will you iterate through all cells?
 * -> When you click on a blank cell, what is the algorithm to expand the neighboring cells?
 * 
Game: hold state and handle user input
    GameState: WON, LOST, RUNNING
    Board: hold the list of Cell objects and do some basic moves with flipping over cells
        Cell(s)
            Using boolean flags instead of enums to indicate type
    UserPlay
    UserPlayResult
 *
 * Algorithms:
        1. The initialization (placing the bombs randomly)
        2. Setting the values of the numbered cells
        3. Expanding the blank region
 */

 /* 7.11 File System: Explain the data structures and algorithms that you would use to design an in-memory file system. Illustrate with an example in code where possible.
  * Hints:
    -> This is not as complicated as it sounds. Start by making a list of the key objects in the system, then think about how they interact.
    -> What is the relationship between files and directories?

    Each Directory has a set of Files and Directories

    Entry
        File
        Directory
 */

 /* 7.12 Hash Table: Design and implement a hash table which uses chaining (linked lists) to handle collisions
  * Hints:
  -> In order to handle collisions, the hash table should be an array of linked lists.
  -> Think carefully about what information the linked list node needs to contain.

  Hasher
    LinkedListNode
        Key
        Value
  */


}
