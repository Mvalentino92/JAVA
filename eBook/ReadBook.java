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
		Pages p7 = new Pages(textOfPages.pt7);
		
		Chapters chapterOne = new Chapters(0,2);
		Chapters chapterTwo = new Chapters(3,6);
		chapterTwo.addPage(new Pages(),"Dude this should be the next page.\nHopefully");
		//chapterTwo.chapterPages[chapterTwo.chapterPageCount].readPage();
		chapterTwo.addPage(new Pages(), "This is legit the next next page. Should be 9");
		
		//chapterOne.chapterPages[chapterOne.pageNumberEnd].readPage();
		//chapterTwo.chapterPages[0].readPage();
		//chapterTwo.chapterPages[chapterTwo.chapterPageCount].readPage();
		//System.out.println(chapterTwo.chapterPageCount);
		Book Code = new Book(0,1);
		Code.bookChapters[0].chapterPages[0].readPage();
		Code.turnPage();
		Code.turnPage();
		Code.turnPage();
		Code.turnPage();
		Code.turnPage();
		Code.turnPage();
		Code.turnPage();
		Code.turnPage();
	}

}
