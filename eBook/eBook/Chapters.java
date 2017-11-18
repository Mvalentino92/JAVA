package eBook;

public class Chapters
{
	public static Chapters[] chapterList = new Chapters[15];  //Keeps a running list of the total chapters in the book
	public static int chapterCount = 0;                       //Indexing for the chapterList
	public Pages[] chapterPages = new Pages[25];              //The pages stored in each chapter
	public int chapterPageCount = -1; //This is by design, to help me differentiate the variables bettter
	
	public int chapterNumber;    //For display purposes in the GUI
	public int pageNumberStart;  //Where the pages for the chapters start in reference to the pageList
	public int pageNumberEnd;    //Where the pages for the chapters end in reference to the pageList
	
	//Constructor method for adding a chapter with a single page
	public Chapters() {chapterNumber = chapterCount;}
	
	//Constructor method for adding a chapter with a specified page range (The pages already exist)
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
	
	//Method for adding a page. If you're adding a page to the last chapter, it simply adds it and updates everything accordingly
	//If you're adding a page to chapter that is NOT the last chapter, it must go through and update everything that is affected.
	public void addPage(Pages newPage, String nextPageText)
	{
		newPage.addText(nextPageText);
		if(Chapters.chapterList[chapterCount-1] == this) Pages.pageList[Pages.pageCount++] = newPage;
		chapterPages[++chapterPageCount] = newPage;
		pageNumberEnd++;
		
		//This executes if you're adding to a NON end chapter
		if(Chapters.chapterList[chapterCount-1] != this)
		{
			//This is shifting down all the pages affected by one index, and inserting the new page
			Pages.pageCount++;
			Pages[] temp = new Pages[50];
			temp[0] = Pages.pageList[pageNumberEnd];
			for(int i = pageNumberEnd-1; i < Pages.pageCount; i++)
			{
				temp[i+1] = Pages.pageList[i+1];
				Pages.pageList[i+1] = temp[i];
			}
		
			Pages.pageList[pageNumberEnd] = newPage;
			 
			//This is updating where the page numbers start and end for each chapter.
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
		    
		    //This is updating the pageNumbers associated with each page in pageList
		    for(int i = pageNumberEnd; i < Pages.pageCount; i++)
		    {
		    	Pages.pageList[i].pageNumber++;
		    }
		    //Original location of line below
		}
		Pages.pageList[pageNumberEnd].pageNumber = Pages.pageList[pageNumberEnd-1].pageNumber + 1;
			
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
		
	
