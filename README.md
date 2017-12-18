# JAVA
Linear Algebra functions, as well as scripts from my class.

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

**LastChance.java:** Let the fun begin! This is a game involving rolling the given number of dice combinations on a card, in a certain amount of allotted turns. The catch it, you can either bet for yourself, or against yourself! Read the instructions at the top of the file for the full set of rules!

**Rocket_Valentino.java:** Simulates a rocket trying to escape the earths atmosphere. Updated values using Eulers Method.

**Subtract_Quiz.java:** Runs a quiz that asks the user simple interger subtraction.

**TimeOfDeath.java:** Classical Newtons Law of Cooling problem. You need to find what time someone died. All you have is the temperature of the environment, and some times with corresponding body temperatures. It is an automated script, and prompts you to enter values. It can handle environmental temperatures that are not constant, by solving a systems of equations to generete the coefficients of the polynomial of best fit. Will output the time of death.

**Valentino_SIR_MODEL.java:** Prompts the user for information about a population to simulate an infectious epidemic. Opts to implement a quarantine option, and utilizes a Plotting Tool (Credited to my professor) in order to plot the results.

**changeString.java:** Returns a new string, with the specified pattern replaced by the given new pattern. Recursively calls itself until all occurences of the pattern are replaced. 

**interview.java:** Conducts an interview with the user. Creates a file containing the questions and their responses, and then reads the file back if the file creation was successful. Does not let an interview be conducted with the same person twice, and will throw an exception during attempted readback if the file was created succuessfully. 

**randomCards.java:** Simulates flashcards by randomizing a txt file that contains questions and answers. Asks the user for the name of the txt file with the questions and answers, then prompts the user to press enter in order see the answer and the next question. Not intended to be functional for general public, but as I tool for myself to use if I wish. 

**timeOfDeathDiffApproach.java:** Much like the TimeOfDeath file, but done in a bit of a different way. Just exploring the possibilities for solving the same problem.
