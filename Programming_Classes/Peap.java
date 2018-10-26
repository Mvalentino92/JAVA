import java.util.*; 
import java.math.*; 
import java.io.*; 
import java.lang.*;

//Node class, to be used with Heap
class Node<E extends Comparable<E>>
{
	//Value, parent and each child
	private E value;
	private Node<E> parent;
	private Node<E> leftChild;
	private Node<E> rightChild;

	//No arg construtor
	public Node(){}

	//Only value
	public Node(E value) {this.value = value;}

	//Value and parent
	public Node(E value, Node<E> parent)
	{
		this.value = value;
		this.parent = parent;
	}

	//All arg constructor
	public Node(E value, Node<E> parent, Node<E> leftChild, Node<E> rightChild)
	{
		this.value = value;
		this.parent = parent;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	//Getters
	E getValue() {return value;}
	Node<E> getParent() {return parent;}
	Node<E> getLeftChild() {return leftChild;}
	Node<E> getRightChild() {return rightChild;}

	//Setters
	void setValue(E value) {this.value = value;}
	void setParent(Node<E> parent) {this.parent = parent;}
	void setLeftChild(Node<E> leftChild) {this.leftChild = leftChild;}
	void setRightChild(Node<E> rightChild) {this.rightChild = rightChild;}

	//Nulls children
	void nullChildren()
	{
		leftChild = null;
		rightChild = null;
	}
}

/*Binary heap class using a tree structure*/
public class Peap<E extends Comparable<E>>
{
	//The root of this Peap
	private Node<E> root = new Node<>();

	//The ArrayList<Integer> that will hold the current mapping of 0's and 1's
	private ArrayList<Integer> map = new ArrayList<>();

	//The size and depth, originally set to 0
	private int size = 0;
	private int depth = 0;

	//The sum of the maps, (if it equals size, that means we have all 1's, and need to drop down)
	private int sum = 0;

	//Boolean, that represents if the current parents left child is occupied
	//Will dictate if we need to call getNext or getPrevious or not
	private boolean leftPresent = false;

	//No arg constructor
	public Peap(){}

	//Get size and depth
	int getSize() {return size;}
	int getDepth() {return depth;}

				/*Methods to update map. We always keep map representing path to last/current node*/

	//Method to get the previous map (after we remove) 
	void getPreviousMap()
	{
		//If sum equals 0, then were reverting back to smaller map, and setting it to all 1's.
		if(sum == 0)
		{
			map.remove(map.size()-1);
			for(int i = 0; i < map.size(); i++) map.set(i,1);
			sum = map.size();
			depth--;
		}
		//Otherwise, update the normal way
		else
		{
			int index = map.size()-1;
			if(map.get(index) == 1) //If its a 1, just change to 0
			{
				map.set(index,0);
				sum--;
			}
			else
			{
				while(map.get(index) == 0) //While it has 0's change to 1's
				{
					sum++;
					map.set(index--,1);
				}
				//Finally, flip the last one
				map.set(index,0);
				sum--;
			}
		}
	}

	//Method to get the next map (to be used to add)
	void getNextMap()
	{
		//If sum equals length of map, then we need to expand length by 1, and set all 0's, and set sum to 0.
		if(sum == map.size())
		{
			for(int i = 0; i < map.size(); i++) map.set(i,0);
			map.add(0);
			sum = 0;
			depth++;
		}
		//Otherwise, update the normal way
		else
		{
			int index = map.size()-1;
			if(map.get(index) == 0) //If its a 0, just change to 1
			{
				map.set(index,1);
				sum++;
			}
			else
			{
				while(map.get(index) == 1) //While it has 1's change to 0's
				{
					sum--;
					map.set(index--,0);
				}
				//Finally, flip the last one
				map.set(index,1);
				sum++;
			}
		}
	}

	//Swap values
	void swapValues(Node<E> A, Node<E> B)
	{
		E temp = A.getValue();
		A.setValue(B.getValue());
		B.setValue(temp);
	}

	//Method to add an element to heap and maintain order.
	void add(E value)
	{
		//If this is the first value, make the roots value this node and return
		if(size == 0)
		{
			root.setValue(value);
			size++;
			return;
		}
		//If it's a fresh start on the map after 3 elements are in, init map
		if(size == 3 && map.size() == 0) map.add(0);

		//Create a Node from the value
		Node<E> toAdd = new Node(value);

		//Set currentNode equal to root.
		Node<E> currentNode = root;

		//Do it once
		if(toAdd.getValue().compareTo(currentNode.getValue()) > 0)
		{
			swapValues(toAdd,currentNode);
		}

		//Now begin to iterate if you can, by finding the next avaiable 
		for(int i = 0; i < map.size(); i++)
		{
			//If it's a 0, set current to left child, if not, set it to right
			if(map.get(i) == 0) currentNode = currentNode.getLeftChild();
			else currentNode = currentNode.getRightChild();

			if(toAdd.getValue().compareTo(currentNode.getValue()) > 0)
			{
				//Switch there values	
				swapValues(toAdd,currentNode);
			}
		}

		//Now, the toAdd value holds the actual value to add. Either add it to the left, or the right.
		if(!leftPresent)
		{
			//Add node, leftPresent is now true
			currentNode.setLeftChild(toAdd);
			toAdd.setParent(currentNode);
			toAdd.nullChildren();
			leftPresent = true;
			size++;
		}
		else 
		{
			//leftPresent is now false, cause we changing the map to point at next node
			currentNode.setRightChild(toAdd);
			toAdd.setParent(currentNode);
			toAdd.nullChildren();
			leftPresent = false;
			getNextMap();
			size++;
		}
	}

	//Remove the top element
	E remove()
	{
		//Store value to return
		E retval = root.getValue();

		//Special cases for size 1
		if(size == 1)
		{
			size--;
			leftPresent = false;
			return retval;
		}

		//Figure out by leftPresent where to go to grab the last entry
		Node<E> currentNode = root;
		if(leftPresent)
		{
			leftPresent = false;
			for(int i = 0; i < map.size(); i++)
			{
				if(map.get(i) == 0) currentNode = currentNode.getLeftChild();
				else currentNode = currentNode.getRightChild();
			}
			swapValues(root,currentNode.getLeftChild());
			currentNode.setLeftChild(null);
		}
		else
		{
			getPreviousMap();
			leftPresent = true;
			for(int i = 0; i < map.size(); i++)
			{
				if(map.get(i) == 0) currentNode = currentNode.getLeftChild();
				else currentNode = currentNode.getRightChild();
			}
			swapValues(root,currentNode.getRightChild());
			currentNode.setRightChild(null);
		}

		//So we need to trickle down.
		Node<E> top = root;
		while(true)
		{
			//If were at the end (no left child), break!
			if(top.getLeftChild() == null) break;

			//Check if it's less than the left side
			if(top.getValue().compareTo(top.getLeftChild().getValue()) < 0)
			{
				//If there's no right, swap with left and return
				if(top.getRightChild() == null)
				{
					swapValues(top,top.getLeftChild());
					size--;
					return retval;
				}
				//Otherwise, compare left to right, swap, and continue
				if(top.getLeftChild().getValue().compareTo(top.getRightChild().getValue()) > 0)
				{
					swapValues(top,top.getLeftChild());
					top = top.getLeftChild();
					continue;
				}
			}
			//If left isn't larger, check to see if right exists before continueing
			if(top.getRightChild() == null) break;

			//If right exists, check if it's bigger and swap then continue
			if(top.getValue().compareTo(top.getRightChild().getValue()) < 0)
			{
				swapValues(top,top.getRightChild());
				top = top.getRightChild();
				continue;
			}
			//Otherwise if none of that occurs, break
			break;
		}
		size--;
		return retval;
	}
}
