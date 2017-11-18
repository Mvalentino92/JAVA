package eBook;

public class DemonstrationReading
{
	public static void main(String[] args)
	{
		String textOne = "This is the project I was working on.\nIt's going to be an eBook (assuming I can do the GUI stuff)"
				+ "I figured everyone can document something that they learned in class,\nand it could be a cool little memento everyone can have access to.\n"
				+ "Sure, its going to be a little crude, or very crude. But it is still an intro class, so anything is better than nothing!";
		String textTwo = "The structure of the book heavily uses objects. As I wanted practice with them.\n"
				+ "Its most likely overkill (maybe, I dont really know!), but regardless it was fun to make up to this point.\n"
				+ "The three objects are \"Pages\", \"Chapters\", and the actual \"Book\" class.\n"
				+ "The book contains chapters, and the chapters contain pages (just like an actual book of course)\n"
				+ "On the next page I'll explain my plans for the GUI";
		String textThree = "I just plan on the GUI displaying the pages text, with a header of the chapter numbers, and page number.\n"
				+ "There will be a turn page button to click that will display the next pages text,\n"
				+ "and hopefully fields where you can either enter a specific chapter to jump to, or a specific page\n"
				+ "Ill just demonstrate the add pages and add chapter functionality\nEnd of Chapter One";
		
		Pages pageOne = new Pages(textOne);
		Pages pageTwo = new Pages(textTwo);
		Pages pageThree = new Pages(textThree);
		
		Chapters chapterOne = new Chapters(0,2);
        //This is me initializing the book with a first chapter and pages.
		Book Example = new Book(0,0);
		
		Example.addChapter(new Chapters(), new Pages(), "Here is a new chapter I'm adding. Chapter two\n"
				+ "You can simply just drop down to another line too while creating your chapter");
		Example.addChapter(new Chapters(), new Pages(), "I'll even add one more chapter, chapter 3");
		Example.bookChapters[0].addPage(new Pages(), "Think ill go and add a page to chapter one");
		Example.bookChapters[2].addPage(new Pages(), "adding a page to the last chapter now (3)");
		Example.bookChapters[1].addPage(new Pages(), "I think i'll add one more page to chapter 2\n"
				+ "You can do the same thing while adding pages too");
		Example.openBook();
		Example.turnPage();
		Example.turnPage();
		Example.turnPage();
		Example.turnPage();
		Example.turnPage();
		Example.turnPage();
		Example.turnPage();
		//Turning a page when non exists should print "End of book"
		Example.turnPage();
		
		System.out.println("\n\n\nNow I'm printing all the pages from the pageList along with their page numbers\n");
		for(int i = 0; i < Pages.pageCount; i++)
		{
			System.out.print(Pages.pageList[i].pageNumber+": ");
			Pages.pageList[i].readPage();
		}
	}

}
