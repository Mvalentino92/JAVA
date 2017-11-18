package eBook;

public class Book
{
	public Chapters[] bookChapters = new Chapters[15]; //Keeps a list of all the chapters in the book
	public int currentChapter = 0; //Keeps track of the current chapter the book is reading from
	public int currentPage = 0;    //Keeps track of the current page the book is reading.
	public static int bookChapterCount = -1; //By design
	public int chapterNumberStart;
	public int chapterNumberEnd;
	
    //Basic constructor
	public Book() {}
	
	//Constructor that adds a specified range of chapters
	public Book(int chapterNumberStart, int chapterNumberEnd)
	{
		for(int i = chapterNumberStart; i <= chapterNumberEnd; i++)
		{
			bookChapters[++bookChapterCount] = Chapters.chapterList[i];
		}
		this.chapterNumberStart = chapterNumberStart;
		this.chapterNumberEnd = chapterNumberEnd;
	}
	
	//adds a chapter, and updates everything accordingly
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
	
	//Opens the book and reads the first page
	public void openBook() {this.bookChapters[currentChapter].chapterPages[currentPage].readPage();}
	
	//turns and read the next page. Reads from the CHAPTERS.
	//if the max index of one chapter is reached, it updates the next chapter at index 0
	public void turnPage()
	{
		try
		{
			this.bookChapters[currentChapter].chapterPages[++currentPage].readPage();
		}
		catch(Exception ex)
		{
			if(currentChapter + 1 <= bookChapterCount)
			{
				currentChapter++;
			    currentPage = 0;
			    this.bookChapters[currentChapter].chapterPages[currentPage].readPage();
			}
			else System.out.println("End of book");
		}
	}
}
