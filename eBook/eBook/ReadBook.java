package eBook;

public class ReadBook
{
	public static void main(String[] args)
	{
		Pages p1 = new Pages(textOfPages.pt1);
		Pages p2 = new Pages(textOfPages.pt2);
		Pages p3 = new Pages(textOfPages.pt3);
		Pages p4 = new Pages(textOfPages.pt4);
		Pages p5 = new Pages(textOfPages.pt5);
		Pages p6 = new Pages(textOfPages.pt6);
      
		
		Chapters chapterOne = new Chapters(0,2);
		Chapters chapterTwo = new Chapters(3,5);
		
		
		Book Code = new Book(0,1);
	    //Book Code = new Book();
	    Code.addChapter(new Chapters(), new Pages(), "Lets see if adding a new chapter works");
	    Code.addChapter(new Chapters(), new Pages(), "Lets see if adding a new chapter worksss");
	    Code.addChapter(new Chapters(), new Pages(), "Lets see if adding a new chapter workssssssssssss");
	    /*for(int i = 0; i < Pages.pageCount; i++)
	    {
	    	System.out.println(Pages.pageList[i]);
	    }*/
	    System.out.println();
	    Code.bookChapters[0].addPage(new Pages(), "Adding to chapter one");
	    Code.bookChapters[2].addPage(new Pages(), "Adding to chapter three");
	    Code.bookChapters[1].addPage(new Pages(), "addig to two bro");
	    /*for(int i = 0; i < Pages.pageCount; i++)
	    {
	    	System.out.println(Pages.pageList[i]);
	    }*/
	    Code.addChapter(new Chapters(), new Pages(), "Last Chapter");
	    Code.bookChapters[3].addPage(new Pages(), "adding a quick final page to last chapter");
	    Code.bookChapters[2].addPage(new Pages(), "This is another addition");
	    Code.addChapter(new Chapters(), new Pages(), "GRAND FINALE");
	   
	    Code.openBook();
	    Code.turnPage();
	    Code.turnPage();
	    Code.turnPage();
	    Code.turnPage();
	    Code.turnPage();
	    Code.turnPage();
	    Code.turnPage();
	    Code.turnPage();
	    Code.turnPage();
	    Code.turnPage();
	    Code.turnPage();
	    Code.turnPage();
	    Code.turnPage();
	    Code.turnPage();
	    Code.turnPage();
	   System.out.println();
	    for(int i = 0; i < Pages.pageCount; i++)
	    {
	    	System.out.print(Pages.pageList[i].pageNumber);
	    	Pages.pageList[i].readPage();
	    }
	    
	    for(int i = 0; i < Chapters.chapterCount; i++)
	    {
	    	System.out.println(Chapters.chapterList[i].chapterNumber);
	    }
	    
		/*chapterOne.addPage(new Pages(), "I'm adding this to chapter one. Should be after page 3");
		chapterOne.addPage(new Pages(), "I'm adding this to chapter one again, should be below the other one I wrote");
		chapterTwo.addPage(new Pages(), "Adding this to chapter two, will be the last page in the book");
		chapterOne.addPage(new Pages(), "Okay, last one being added to chapter one. Promise. onto chapter two!");
		chapterTwo.addPage(new Pages(), "Last page being added to the end. Final");
		Code.addChapter(new Chapters(), new Pages(), "Lets see if adding a new chapter works");
		chapterTwo.addPage(new Pages(), "This should be before the new chapter");
		Code.bookChapters[Book.bookChapterCount].addPage(new Pages(), "I'm trying to add this as the FINAL text");
		Code.bookChapters[0].addPage(new Pages(), "CHAPTER ONE END");
		Code.openBook();
		Code.turnPage();
		Code.turnPage();
		Code.turnPage();
		Code.turnPage();
		Code.turnPage();
		Code.turnPage();
		Code.turnPage();
		Code.turnPage();
		Code.turnPage();
		Code.turnPage();
		Code.turnPage();
		Code.turnPage();
		Code.turnPage();
		Code.turnPage();
	    Pages.pageList[Pages.pageCount-1].readPage();
		System.out.println(chapterOne.pageNumberEnd);*/
	}

}
