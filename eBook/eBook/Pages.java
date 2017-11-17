package eBook;

public class Pages
{
	public static int pageCount = 0;
	public static Pages[] pageList = new Pages[50];
	
	public String text;
	public int pageNumber;
	
	//The only constructor
	public Pages() {}
	
	public Pages(String text)
	{
		this.text = text;
		pageNumber = pageCount;
		pageList[pageCount++] = this;
	}
	
	//getter methods
	public void readPage() {System.out.println(text);}
	public int getPageNumber() {return pageNumber;}
	public void addText(String text) {this.text = text;}

	
	
	

}
