class TreeNode<E extends Comparable<E>> 
{
	//Data Fields (no parent)
	E value;
	TreeNode<E> left;
	TreeNode<E> right;

	//No arg, just value, and all arg constructors
	public TreeNode(){}
	public TreeNode(E value) {this.value = value;}
	public TreeNode(E value, TreeNode<E> left, TreeNode<E> right)
	{
		this.value = value;
		this.left = left;
		this.right = right;
	}

	//Setters
	void setValue(E value) {this.value = value;}
	void setLeft(TreeNode<E> left) {this.left = left;}
	void setRight(TreeNode<E> right) {this.right = right;}
}

class Tree<E extends Comparable<E>>
{
	//Data Fields
	TreeNode<E> root = new TreeNode<E>();
	int current = 0;
	int depth = 0;

	//No arg constructor
	public Tree(){}

	//The add function wrapper
	void add(E element) 
	{
		if(current == 0)
		{
			root.setValue(element);
			current++;
			depth++;
		}
		else addHelper(root,element,current,depth);
	}


	//The recursive add function
	void addHelper(TreeNode<E> cRoot, E element, int cCurrent, int cDepth)
	{
		//Main Base case, if were at the end of a depth
		if(cCurrent == (int)Math.pow(2,depth+1) - 1)
		{
			while(cRoot.left != null) cRoot = cRoot.left;
			cRoot.setLeft(new TreeNode<E>(element));
			current++;
			depth++;
			return;
		}

		//Sub base cases, if current is less than 3
		if(cCurrent < 3)
		{
			//If it's 1, add the left child, and increment current.
			if(cCurrent == 1)
			{
				cRoot.setLeft(new TreeNode<E>(element));
				current++;
			}
			//Else (its 2), add right child, increment current
			else 
			{
				cRoot.setRight(new TreeNode<E>(element));
				current++;
			}
		}
		//Recur Left
		else if(cCurrent < (int)Math.pow(2,cDepth) + (int)Math.pow(2,cDepth-1) - 1)
			addHelper(cRoot.left, element, cCurrent - (int)Math.pow(2,cDepth-1), cDepth - 1);
		//Recur right
		else addHelper(cRoot.right, element, cCurrent - (int)Math.pow(2,cDepth), cDepth - 1);
	}

	//Print in Order
	void printIn()
	{
		printInHelper(root);
		System.out.println();
	}

	//Helper for print
	void printInHelper(TreeNode<E> cRoot)
	{
		if(cRoot == null) return;
		System.out.print(cRoot.value+" ");
		printInHelper(cRoot.left);
		printInHelper(cRoot.right);
	}

	//Print breadth
	void printBreadth() 
	{
		for(int i = 0; i <= depth; i++) printBreadthHelper(root,i,0);
		System.out.println();
	}

	//Print breadth helper
	void printBreadthHelper(TreeNode<E> cRoot, int cDepth, int currentDepth)
	{
		if(cRoot == null) return;
		if(cDepth == currentDepth) 
		{
			System.out.print(cRoot.value+" ");
			return;
		}
		printBreadthHelper(cRoot.left,cDepth,currentDepth + 1);
		printBreadthHelper(cRoot.right,cDepth,currentDepth + 1);
	}

}

public class BinaryTree
{
	public static void main(String[] args)
	{
		Tree<Integer> tree = new Tree<>();
		for(int i = 1; i <= 27; i++) tree.add(i);
		System.out.print("In Order Traversal: ");
		tree.printIn();
		System.out.print("Breadth Traversal: ");
		tree.printBreadth();
	}
}
