package eBook;

public class Book
{
	public Chapters[] bookChapters = new Chapters[15];
	public int currentChapter = 0;
	public int currentPage = 0;
	public int bookChapterCount = -1;
	public int chapterNumberStart;
	public int chapterNumberEnd;
	

	
	
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
