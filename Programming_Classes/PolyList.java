//Node class for building polynomails
class polyNode
{
	//Each term had coefficient, exponent and link to next term
	int coeff;
	int exponent;
	polyNode next;

	//No ARG
	public polyNode() {}
	
	//Coeff and exponent
	public polyNode(int coeff, int exponent)
	{
		this.coeff = coeff;
		this.exponent = exponent;
	}

	//All ARG
	public polyNode(int coeff, int exponent, polyNode next)
	{
		this.coeff = coeff;
		this.exponent = exponent;
		this.next = next;
	}

	//Getters
	int getCoeff() {return coeff;}
	int getExponent() {return exponent;}
}

//Linked list representing a polynomial
public class PolyList
{
	//Has head, tail, size, and descending boolean (default is true)
	polyNode head;
	polyNode tail;
	int size = 0;
	boolean descending = true;

	//No ARG
	public PolyList(){}

	//Head (for copying purposes)
	public PolyList(polyNode head)	
	{
		this.addAll(head);
	}

	//Returns the head of the list
	polyNode getHead() {return head;}

	//Adding terms to the end
	void addLast(polyNode p)
	{
		if(size == 0) initList(p);
		else
		{
			if(p.getExponent() > head.getExponent()) descending = false;
			tail.next = p;
			tail = tail.next;
			size++;
		}
	}

	//Starts the list by initializing the head
	void initList(polyNode p)
	{
		head = p;
		tail = p;
		size++;
	}

	//Deepcopys the node, information (excluding it's link)
	polyNode deepCopy(polyNode p)
	{
		return new polyNode(p.getCoeff(),p.getExponent());
	}

	//Deepcopys before adding
	void deepAddLast(polyNode p)
	{
		addLast(deepCopy(p));
	}

	//Deepcopys each node in chained nodes and adds all to list
	void addAll(polyNode current)
	{
		while(current != null)
		{
			this.deepAddLast(current);
			current = current.next;
		}
	}

	//Print the polynomial
	void printPoly()
	{
		polyNode current = head;
		boolean firstTerm = true;
		String sign = "";
		int flip = 1;
		while(current != null)
		{
			//Prints first term normally
			if(firstTerm)
			{
				System.out.print(this.printTerm(current,flip,sign,firstTerm));
				firstTerm = false;
				current = current.next;
				continue;
			}
			//Tracks how to handle minus and plus signs
			if(current.getCoeff() > 0)
			{
				sign = "+";
				flip = 1;
			}
			else 
			{
				sign = "-";
				flip = -1;
			}
			//Print each term and point to next
			System.out.print(this.printTerm(current,flip,sign,firstTerm));
			current = current.next;
		}
		System.out.println();
	}

	//Returns the x part of the term
	String printX(polyNode p)
	{
		if(p.getExponent() > 0) return "x^"+p.getExponent()+" ";
		else return " ";
	}

	//Returns coeff part
	String printCoeff(polyNode p,int flip,boolean firstTerm)
	{
		if(p.getCoeff() == 1 && p.getExponent() > 0) return "";
		else if(p.getCoeff() == -1 && p.getExponent() > 0) 
		{
			if(firstTerm) return "-";
			else return "";
		}
		else return (p.getCoeff()*flip)+"";
	}

	//Returns entire term to print (Tried to address all of possibilities.
	//Just really tedious.
	String printTerm(polyNode p,int flip, String sign, boolean firstTerm)
	{
		if(p.getCoeff() == 0) return "";
		String retval = "";
		if(!firstTerm) retval += sign+" ";
		retval += this.printCoeff(p,flip,firstTerm) + this.printX(p);
		return retval;
	}

	//Adds polynomials together and returns a new list
	PolyList addPoly(PolyList toAdd)
	{
		//PolyList to return, and grabbing head from each list
		PolyList retval = new PolyList();
		polyNode p1 = this.getHead();
		polyNode p2 = toAdd.getHead();

		//Calls the wrapper function to add to retval, then return retval
		this.addPolyWrapper(p1,p2,retval);
		return retval;
	}

	//Wrapper function for addings polynomials
	private void addPolyWrapper(polyNode p1, polyNode p2, PolyList retval)
	{
		//If either list is empty, add all elements of remaining
		//Otherwise, add the corresponding exponent based on order
		if(p1 == null) retval.addAll(p2);
		else if(p2 == null) retval.addAll(p1);
		else
		{
			if(descending)
			{
				if(p1.getExponent() > p2.getExponent())
				{
					retval.deepAddLast(p1);
					p1 = p1.next;
					this.addPolyWrapper(p1,p2,retval);
				}
				else if(p2.getExponent() > p1.getExponent())
				{
					retval.deepAddLast(p2);
					p2 = p2.next;
					this.addPolyWrapper(p1,p2,retval);
				}
				else
				{
					int sum = p1.getCoeff() + p2.getCoeff();
					retval.addLast(new polyNode(sum,p1.getExponent()));
					p1 = p1.next;
					p2 = p2.next;
					this.addPolyWrapper(p1,p2,retval);
				}
			}
			else
			{
				if(p1.getExponent() < p2.getExponent())
				{
					retval.deepAddLast(p1);
					p1 = p1.next;
					this.addPolyWrapper(p1,p2,retval);
				}
				else if(p2.getExponent() < p1.getExponent())
				{
					retval.deepAddLast(p2);
					p2 = p2.next;
					this.addPolyWrapper(p1,p2,retval);
				}
				else
				{
					int sum = p1.getCoeff() + p2.getCoeff();
					retval.addLast(new polyNode(sum,p1.getExponent()));
					p1 = p1.next;
					p2 = p2.next;
					this.addPolyWrapper(p1,p2,retval);
				}
			}
		}
	}

	/*MAIN METHOD*/
	public static void main(String[] args)
	{
		//P1
		PolyList p1 = new PolyList();
		p1.addLast(new polyNode(-4,11));
		p1.addLast(new polyNode(6,9));
		p1.addLast(new polyNode(-5,6));
		p1.addLast(new polyNode(-6,5));
		p1.addLast(new polyNode(8,4));
		p1.addLast(new polyNode(3,3));
		p1.addLast(new polyNode(-7,2));
		p1.addLast(new polyNode(1,1));
		p1.addLast(new polyNode(3,0));
		System.out.print("P1 is: ");
		p1.printPoly();

		//P2
		PolyList p2 = new PolyList();
		p2.addLast(new polyNode(6,13));
		p2.addLast(new polyNode(-9,11));
		p2.addLast(new polyNode(5,10));
		p2.addLast(new polyNode(-1,9));
		p2.addLast(new polyNode(1,4));
		p2.addLast(new polyNode(-1,1));
		System.out.print("P2 is: ");
		p2.printPoly();

		//P1 + P2
		PolyList sumP1P2 = p1.addPoly(p2);
		System.out.print("There sum is: ");
		sumP1P2.printPoly();
		System.out.println();

		//P3
		PolyList p3 = new PolyList();
		p3.addLast(new polyNode(-4,0));
		p3.addLast(new polyNode(-4,2));
		p3.addLast(new polyNode(2,5));
		p3.addLast(new polyNode(1,6));
		p3.addLast(new polyNode(-1,7));
		p3.addLast(new polyNode(8,14));
		System.out.print("P3 is: ");
		p3.printPoly();

		//P4
		PolyList p4 = new PolyList();
		p4.addLast(new polyNode(3,0));
		p4.addLast(new polyNode(7,1));
		p4.addLast(new polyNode(-3,2));
		p4.addLast(new polyNode(-2,5));
		p4.addLast(new polyNode(1,6));
		p4.addLast(new polyNode(-1,9));
		System.out.print("P4 is: ");
		p4.printPoly();

		//P3 + P4
		PolyList sumP3P4 = p3.addPoly(p4);
		System.out.print("There sum is: ");
		sumP3P4.printPoly();
	}
}
