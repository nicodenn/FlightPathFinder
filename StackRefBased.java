/*Nico Dennis
 * last edited May 6th, 2019
 * creates a referenced based stack that is to be used in an infix calculator.*/
import java.util.EmptyStackException;

public class StackRefBased {
	private Node top;

	//Creates new reference based stack
	public StackRefBased() {
		top = null;
	}

	//Checks if stack is empty
	public boolean StackIsEmpty() {
		return (top == null);
	}
	//Pushes first element into stack
	public void pushInitial(Node newItem)
	{
		top = newItem;
	}
	//pushes and object into the stack
	public void pushDepart(Node departItem) {
		Node temp = top;
		top = departItem;
		top.setNext(temp);
		
	}
	//returns top item in the stack, throws error if empty
	public Node pop() throws EmptyStackException {
		if (top != null) {
			Node curr = top;
			top = top.getNext();
			curr.setNext(null);
			return curr;
		}
		else {
			throw new EmptyStackException();
		}
	}
	//Returns objext on top of stack without popping it, throws error if stack is empty
	public Node peek()
		throws EmptyStackException {
		if (!StackIsEmpty()) {
			return top;
		} else {
			throw new EmptyStackException();
		}
	}

}
