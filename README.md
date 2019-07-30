# JAVA
Linear Algebra functions, notable scripts from my computer science courses, personal files, and some games!!

## Linear_Algebra:
**EigenValues.java:** Finds all the eigenvalues of a matrix within certain constraints. The *constraints* are as follows:
1. Cannot compute complex or imaginary eigenvalues
2. The minimum and maximum eigenvalues in absolute value cannot have positive or negative counterpart.
3. Any eigenvalues cannot be within 1e-4 of eachother in value.

**rref.java:** Uses Gaussian Elimination to return a matrix in reduced row echelon form.

**linsolve.java:** Utilizes Gaussian Elimination, the inverse, and matrix multiplication to return the coefficients for a systems of a equations.

## My_Games
**ConnectFour.java:** A text-based Connect Four game to be enjoyed at the terminal. You as the player, face off against the computer. The computer uses a series of algorithms to determine its best course of action. 

**LastChance.java:** Let the fun begin! This is a game involving rolling the given number of dice combinations on a card, in a certain amount of allotted turns. The catch is, you can either bet for yourself, or against yourself! Read the instructions at the top of the file for the full set of rules!

**Othello.java:** This is a text based version of the game Othello, (or you may know it as Reversi). Same sort of set up as the ConnectFour.java game above. You have the option to either to play against another player, or the computer. The computer tries to access which move is the best course of action every turn using an algorithm I wrote. Have fun.

**STEMSurvivalRules.pdf:** Contains information on how to play the game, as well as rules for within the game. 

**ScrabbleWordFinder.zip:** Inspired from playing a Scrabble app with my girlfriend! The bonus points from using all your letters are very sweet, but, it's often difficult to see if you can make any seven letter words (unless you hit shuffle a million times!). The main file will prompt you to enter your letters, and will return all possible 7 letter words (From the Collins Dictionary). Just intended for our personal use, so it will throw an error if you don't supply the input as "abcdefg". 

**StemSurvival.zip:** Contains all the .java files necessary for playing the game.

**SudokuSolver.java:** Solves 6 Sudoku boards ranging from easy to very difficult. Uses a backtracking algorithm. The backtracking algorithms stacks arent freed until it arrives at a solution. So in order to free the stack and not overflow, each recursive call is tracked, and once it hits a safe threshold it stops and returns the board. The method is continously called (where it last left off) until it arrives at a solution.

**mastermind.java:** Simple Mastermind game. Lets you choose how many numbers you want to guess, as well as how many guesses you are given.

## Programming_Classes/Personal (Mostly personal)
**BinarySearch.java:** Peforms a simple binary search on an array of ints.

**EncodeAndDecodeJavaFiles.java && EncryptAndDecrypt.java:** Encodes and decodes .java files using the following algorithm. Counts the number of letters in each word and shifts each of the letters in that word by that number. Only letters of the alphabet (both upper and lowercase) count as letters, and words are any characters seperated by either spaces, periods, brackets, or parenthesis. Returns a new file which represents either the encoded and decoded .java file.

**FlashCards.java:** Reads a file provided by the user with questions and answers. Randomly shuffles the questions, and displays the text to the user. After the answer is shown, the user specifies if they answered the question correctly. All incorrectly answered questions will be asked again next round (shuffled as well). Will keep repeating this process until all questions are answered correctly.

**KnapSack.java:** Solves the bounded or 0-1 KnapSack problem using Dynamic Programming. Feel like every programmers gotta do it at some point!

**MLCS.java:** The longest commmon substring problem. Can be used to the find the LCS between any number of Strings. The easy LCS problem to solve is the one where you are just comparing 2 Strings. So to compare n Strings, the idea is to keep breaking down the problem in a way where I'm only ever solving the 2 String problem over and over again until I arrive at the solution.

**PolyList.java:** Represents a polynomial as a LinkedList. Supports some basic functions of list, and a function that adds polynomials. Pretty standard.

**Program1_Checker.java:** A program my professor had me write to help them grade a class project. It reads in the student output file and checks it against the professor's "master output file" for correctness with some tolerance for formatting.

**RadiixSort.java:** Performs Radiix sort on ints, with the bounds of negative to positive numbers.

**RecursiveInterpolation.java:** Uses recursion to linearly interpolate halfway between all the supplied data points. Continues to do so until the new array length reaches the size specified by the user. The larger the desired array compared to the original, the less accurate the final returned array will be, as each subsequent method call is interpolating previously interpolated points.

**RecursiveSum.java:** A file created to help teach recursion. Given an array of ints, return all possible sums by combining these ints without repition. Simple recursive function to highlight how recursion can be used to explore all possibilities.

**SecretMessages.java:** A Message class that supports encryption and decryption using a simple Caesar Cipher. 

**SortingCompare.zip:** Compares 16 sorting algorithms against eachother. This includes both the iterative and recursive versions, as well as Java's defualt Arrays.Sort() method.

**Sudoku.java:** Sudoku solver using BackTracking

**TimeOfDeath.java:** Classical Newtons Law of Cooling problem. You need to find what time someone died. All you have is the temperature of the environment, and some times with corresponding body temperatures. It is an automated script, and prompts you to enter values. It can handle environmental temperatures that are not constant, by solving a systems of equations to generete the coefficients of the polynomial of best fit. Will output the time of death.

**Valentino_SIR_MODEL.java:** Prompts the user for information about a population to simulate an infectious epidemic. Opts to implement a quarantine option, and utilizes a Plotting Tool (Credited to my professor) in order to plot the results.

**changeString.java:** Returns a new string, with the specified pattern replaced by the given new pattern. Recursively calls itself until all occurences of the pattern are replaced.

**coinFlips.java:** Represents heads as 0, and tails as 1. Flip a coin N amount of times, and return all possible instances of the results. Used as an exercise educational exercise. Demonstrating two different ways to approach this problem. One method, using binary representation of numbers, and the other using recursion. 

**interpolation.java:** Uses linear interpolation to return a value at the specified point.

**interview.java:** Conducts an interview with the user. Creates a file containing the questions and their responses, and then reads the file back if the file creation was successful. Does not let an interview be conducted with the same person twice, and will throw an exception during attempted readback if the file was created unsuccessfully. 

**knapsack.java:** Solves the unbouned knapsack problem

**timeOfDeathDiffApproach.java:** Much like the TimeOfDeath file, but done in a bit of a different way. Just exploring the possibilities for solving the same problem.
