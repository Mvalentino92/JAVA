package eBook;

public class Chapters
{
	public static Chapters[] chapterList = new Chapters[10];
	public static int chapterCount = 0;
	public Pages[] chapterPages = new Pages[10];
	public int chapterPageCount = -1;
	public int pageNumberStart;
	public int pageNumberEnd;
	
	//Two constructor methods, one for knowing what pages, the other for just adding one page.
	
	public Chapters(int pageNumberStart, int pageNumberEnd)
	{
		for(int i = pageNumberStart; i <= pageNumberEnd; i++)
		{
			chapterPages[++chapterPageCount] = Pages.pageList[i];
			//chapterPageCount++;
		}
		this.pageNumberStart = pageNumberStart;
		this.pageNumberEnd = pageNumberEnd;
		chapterList[chapterCount++] = this;
	}
	
	//Getter method to get beginning and end
	public void getFlanks() {System.out.println("The start is "+pageNumberStart+" and the end is "+pageNumberEnd);}
	
	public void addPage(Pages newPage, String nextPageText)
	{
		newPage.addText(nextPageText);
		//Pages holdPage = new Pages(nextPageText);
		Pages.pageList[++Pages.pageCount] = newPage;
		chapterPages[++chapterPageCount] = newPage;
	}
}
		
	
