package eBook;

public class Chapters
{
	public static Chapters[] chapterList = new Chapters[10];
	public static int chapterCount = 0;
	public Pages[] chapterPages = new Pages[10];
	public int chapterPageCount = -1;
	
	public int chapterNumber;
	public int pageNumberStart;
	public int pageNumberEnd;
	
	//Two constructor methods, one for knowing what pages, the other for just adding one page.
	public Chapters() {chapterNumber = chapterCount;} //Added this
	
	public Chapters(int pageNumberStart, int pageNumberEnd)
	{
		for(int i = pageNumberStart; i <= pageNumberEnd; i++)
		{
			chapterPages[++chapterPageCount] = Pages.pageList[i];
		}
		
		chapterNumber = chapterCount; 
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
		if(Chapters.chapterList[chapterCount-1] == this) 
		{
			Pages.pageList[Pages.pageCount++] = newPage;     //changed this
			//Pages.pageList[Pages.pageCount-1].pageNumber++;
		}
		chapterPages[++chapterPageCount] = newPage;
		pageNumberEnd++;
		
		if(Chapters.chapterList[chapterCount-1] != this)
		{
			Pages.pageCount++;
			Pages[] temp = new Pages[50];
			temp[0] = Pages.pageList[pageNumberEnd];
			for(int i = pageNumberEnd-1; i < Pages.pageCount; i++)
			{
				temp[i+1] = Pages.pageList[i+1];
				Pages.pageList[i+1] = temp[i];
			}
		
			Pages.pageList[pageNumberEnd] = newPage;
			 
		    int indexOfChapter = 0;
		    for(int i = 0; i < chapterCount; i++)
		    {
		    	if(chapterList[i] == this) indexOfChapter = i;
		    }
		
		    for(int i = indexOfChapter+1; i < chapterCount; i++)
		    {
		    	chapterList[i].pageNumberStart++;
		    	chapterList[i].pageNumberEnd++;
		    }
		    for(int i = pageNumberEnd; i < Pages.pageCount; i++)
		    {
		    	Pages.pageList[i].pageNumber++;
		    }
		    Pages.pageList[pageNumberEnd].pageNumber = Pages.pageList[pageNumberEnd-1].pageNumber + 1; 
		}
			
	}
	
	public void getPageStart() {System.out.println(pageNumberStart);}
	public void getPageEnd() {System.out.println(pageNumberEnd);}
	
	/*public void addPage(Pages newPage, String nextPageText)
	{
		int chapterIndex = 0;
		for(int i = 0; i < chapterCount; i++)
		{
			if(chapterList[i] == thisChapter) chapterIndex = i;
		}
		
		if(chapterIndex == chapterCount - 1)
		{
			newPage.addText(nextPageText);
			Pages.p*/
}
		
	
