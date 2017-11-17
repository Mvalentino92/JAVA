package eBook;

public class Book
{
	public Chapters[] bookChapters = new Chapters[15];
	public int currentChapter = 0;
	public int currentPage = 0;
	public static int bookChapterCount = -1; //Im changing these all to 0
	public int chapterNumberStart;
	public int chapterNumberEnd;
	

	public Book() {}
	
	public Book(int chapterNumberStart, int chapterNumberEnd)
	{
		for(int i = chapterNumberStart; i <= chapterNumberEnd; i++)
		{
			bookChapters[++bookChapterCount] = Chapters.chapterList[i];
			//chapterPageCount++;
		}
		this.chapterNumberStart = chapterNumberStart;
		this.chapterNumberEnd = chapterNumberEnd;
	}
	
	public void addChapter(Chapters newChapter, Pages newPage, String textOfPage)
	{
		newPage.addText(textOfPage);
		newChapter.pageNumberStart = Pages.pageCount;
		newChapter.pageNumberEnd = Pages.pageCount;
		newChapter.chapterPages[++newChapter.chapterPageCount] = newPage;
		Pages.pageList[Pages.pageCount++] = newPage;
		Pages.pageList[Pages.pageCount -1].pageNumber = Pages.pageCount -1; //Changed this
		Chapters.chapterList[Chapters.chapterCount++] = newChapter;
		bookChapters[++bookChapterCount] = newChapter; //Might need to be changed NVM
		chapterNumberEnd++;
	}
	
	public void openBook() {this.bookChapters[currentChapter].chapterPages[currentPage].readPage();}
	
	public void turnPage()
	{
		try
		{
			this.bookChapters[currentChapter].chapterPages[++currentPage].readPage();
		}
		catch(Exception ex)
		{
			currentChapter++;
			currentPage = 0;
			this.bookChapters[currentChapter].chapterPages[currentPage].readPage();
		}
	}
	
	
	
	
	
}
