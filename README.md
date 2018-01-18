# JAVA
Linear Algebra functions, scripts from my class, personal files, and a game!!

## Linear_Algebra:
**EigenValues.java:** Finds all the eigenvalues of a matrix within certain constraints. The *constraints* are as follows:
1. Cannot compute complex or imaginary eigenvalues
2. The minimum and maximum eigenvalues in absolute value cannot have positive or negative counterpart.
3. Any eigenvalues cannot be within 1e-4 of eachother in value.

**rref.java:** Uses Gaussian Elimination to return a matrix in reduced row echelon form.

**linsolve.java:** Utilizes Gaussian Elimination, the inverse, and matrix multiplication to return the coefficients for a systems of a equations.

## Intro_To_Programming
**EncodeAndDecodeJavaFiles.java && EncryptAndDecrypt.java:** Encodes and decodes .java files using the following algorithm. Counts the number of letters in each word and shifts each of the letters in that word by that number. Only letters of the alphabet (both upper and lowercase) count as letters, and words are any characters seperated by either spaces, periods, brackets, or parenthesis. Returns a new file which represents either the encoded and decoded .java file.

**FinalTax.java:** Calculates how much of a clients income will be taxed, based on their filing status. Returns their tax bracket, ID, and tax.

**FlashCards.java:** Reads a file provided by the user with questions and answers. Randomly shuffles the questions, and displays the text to the user. After the answer is shown, the user specifies if they answered the question correctly. All incorrectly answered questions will be asked again next round (shuffled as well). Will keep repeating this process until all questions are answered correctly.

**RecursiveInterpolation.java:** Uses recursion to linearly interpolate halfway between all the supplied data points. Continues to do so until the new array length reaches the size specified by the user. The larger the desired array compared to the original, the less accurate the final returned array will be, as each subsequent method call is interpolating previously interpolated points.

**Rocket_Valentino.java:** Simulates a rocket trying to escape the earths atmosphere. Updated values using Eulers Method.

**Subtract_Quiz.java:** Runs a quiz that asks the user simple interger subtraction.

**TimeOfDeath.java:** Classical Newtons Law of Cooling problem. You need to find what time someone died. All you have is the temperature of the environment, and some times with corresponding body temperatures. It is an automated script, and prompts you to enter values. It can handle environmental temperatures that are not constant, by solving a systems of equations to generete the coefficients of the polynomial of best fit. Will output the time of death.

**Valentino_SIR_MODEL.java:** Prompts the user for information about a population to simulate an infectious epidemic. Opts to implement a quarantine option, and utilizes a Plotting Tool (Credited to my professor) in order to plot the results.

**changeString.java:** Returns a new string, with the specified pattern replaced by the given new pattern. Recursively calls itself until all occurences of the pattern are replaced.

**interpolation.java:** Uses linear interpolation to return a value at the specified point.

**interview.java:** Conducts an interview with the user. Creates a file containing the questions and their responses, and then reads the file back if the file creation was successful. Does not let an interview be conducted with the same person twice, and will throw an exception during attempted readback if the file was created succuessfully. 

**timeOfDeathDiffApproach.java:** Much like the TimeOfDeath file, but done in a bit of a different way. Just exploring the possibilities for solving the same problem.

## My_Games
**ConnectFour.java:** A text-based Connect Four game to be enjoyed at the terminal. You as the player, face off against the computer. The computer uses a series of algorithms to determine its best course of action. 

**LastChance.java:** Let the fun begin! This is a game involving rolling the given number of dice combinations on a card, in a certain amount of allotted turns. The catch it, you can either bet for yourself, or against yourself! Read the instructions at the top of the file for the full set of rules!

**Othello.java:** This is a text based version of the game Othello, (or you may know it as Reversi). Same sort of set up as the ConnectFour.java game above. You have the option to either to play against another player, or the computer. The computer tries to access which move is the best course of action every turn using an algorithm I wrote. Have fun.

**STEMSurvivalRules.pdf:** Contains information on how to play the game, as well as rules for within the game. 

**StemSurvival.zip:** Contains all the .java files necessary for playing the game.

**SudokuSolver.java:** Solves 3 selected Sudoku puzzles I have in the file. From the website I got them from, two were described as "Easiest", and one was described as "Intermediate". Unfortunately, I hit a stack overflow at "Difficult". First time learning/writing a backtracking algorithm.
