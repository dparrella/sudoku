# Sudoku

This code models a game of Sudoku.

The board background is black. The game starts with some cells filled in 
with digits which are green, since we know they're correct. The player adds digits 
through some UI that we're not going to worry about. If a digit is obviously 
incorrect (i.e. the same digit is already in the same row, column or 3x3 square), the 
game displays it in red. If a digit is not obviously incorrect the game displays it in 
white.

# Build 

## Requirements

* Java 1.8
* Maven 3.3.3

Maven and Java are included with Mac OS X, but the versions may require an update.

### Test your maven version:

    mvn -v
    
### Test your Java version:

    java -version
    
Maven can be installed/updated through [Homebrew](https://brew.sh/).

Java can be downloaded from [here](https://www.java.com/en/download/).

## Command

    mvn clean install
   
This will build the code and run all of the tests.