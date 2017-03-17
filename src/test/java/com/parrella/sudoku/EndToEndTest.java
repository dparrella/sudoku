package com.parrella.sudoku;

import com.parrella.sudoku.exception.InvalidLocationException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

@Test
public class EndToEndTest {

   // Regions of 3x3 cells 1-9
   private SudokuBoard board;

   @BeforeMethod
   public void doBeforeMethod() {
      board = new SudokuBoard();

      {
         Integer region[][] = {
               { null, null, null },
               { null, 9, 3 },
               { null, 6, null }
         };

         board.setRegion(0, region);
      }

      {
         Integer region[][] = {
               { null, null, null },
               { 6, 2, 8 },
               { null, null, null }
         };

         board.setRegion(1, region);
      }

      {
         Integer region[][] = {
               { null, null, null },
               { 1, 4, null },
               { null, 5, null }
         };

         board.setRegion(2, region);
      }

      {
         Integer region[][] = {
               { null, 3, null },
               { null, 5, null },
               { null, 4, null }
         };

         board.setRegion(3, region);
      }

      {
         Integer region[][] = {
               { null, 1, null },
               { 8, null, 2 },
               { null, 7, null }
         };

         board.setRegion(4, region);
      }

      {
         Integer region[][] = {
               { null, 9, null },
               { null, 7, null },
               { null, 6, null }
         };

         board.setRegion(5, region);
      }

      {
         Integer region[][] = {
               { null, 8, null },
               { null, 1, 7 },
               { null, null, null }
         };

         board.setRegion(6, region);
      }

      {
         Integer region[][] = {
               { null, null, null },
               { 5, 9 , 3 },
               { null, null, null }
         };

         board.setRegion(7, region);
      }

      {
         Integer region[][] = {
               { null, 3, null },
               { 4, 2, null },
               { null, null, null }
         };

         board.setRegion(8, region);
      }
   }

   public void verifyInitialBoardDigits() {
      for (int region = 0; region < 9; region++) {

         for (int x = 0; x < 3; x++) {

            for (int y = 0; y < 3; y++) {
               Cell cell = board.getCell(region, x, y);
               assertThat(cell, notNullValue());

               if (cell.getDigit() != null) {
                  assertThat(cell.getColor(), is(Color.GREEN));
               } else {
                  assertThat(cell.getColor(), is(Color.BLACK));
               }
            }

         }

      }
   }

   public void userCanAddCorrectDigitToBoard() {
      Cell cell = board.addDigit(1, 0, 1, 5);
      assertThat(cell.getColor(), is(Color.GREEN));
      assertThat(cell.getDigit(), is(5));
   }

   public void userCanAddIncorrectDigitToBoardThatIsInTheSameRegion() {
      Cell cell = board.addDigit(0, 1, 0, 6);
      assertThat(cell.getColor(), is(Color.RED));
      assertThat(cell.getDigit(), is(6));
   }

   public void userCanAddIncorrectDigitToBoardThatIsInTheSameColumn() {
      Cell cell = board.addDigit(3, 0, 0, 4);
      assertThat(cell.getColor(), is(Color.RED));
      assertThat(cell.getDigit(), is(4));
   }

   public void userCanAddIncorrectDigitToBoardThatIsInTheSameRow() {
      Cell cell = board.addDigit(5, 1, 1, 7);
      assertThat(cell.getColor(), is(Color.RED));
      assertThat(cell.getDigit(), is(7));
   }

   @Test(expectedExceptions = InvalidLocationException.class)
   public void userCannotAddDigitToBoardAtInvalidRegion() {
      board.addDigit(-1, 1, 1, 7);
   }

   @Test(expectedExceptions = InvalidLocationException.class)
   public void userCannotAddDigitToBoardAtInvalidXCoordinateInRegion() {
      board.addDigit(1, 1, 3, 7);
   }

   @Test(expectedExceptions = InvalidLocationException.class)
   public void userCannotAddDigitToBoardAtInvalidYCoordinateInRegion() {
      board.addDigit(1, 3, 1, 7);
   }

   public void canQueryPositionOnBoardWhereNoNumberIsPresent() {
      Cell cell = board.getCell(7, 0, 1);
      assertThat(cell.getDigit(), nullValue());
      assertThat(cell.getColor(), is(Color.BLACK));
   }

   public void canQueryPositionOnBoardWhereInitialCorrectNumberIsPresent() {
      Cell cell = board.getCell(1, 1, 1);
      assertThat(cell.getDigit(), is(2));
      assertThat(cell.getColor(), is(Color.GREEN));
   }

   public void canQueryPositionOnBoardWhereUserAddedCorrectNumberIsPresent() {
      Cell userAddedCell = board.addDigit(1, 0, 1, 5);
      assertThat(userAddedCell.getColor(), is(Color.GREEN));

      Cell retrievedCell = board.getCell(1, 0, 1);
      assertThat(retrievedCell, notNullValue());
      assertThat(retrievedCell.getDigit(), is(5));
      assertThat(retrievedCell.getColor(), is(Color.GREEN));
   }

   public void canQueryPositionOnBoardWhereUserAddedIncorrectNumberIsPresent() {
      Cell userAddedCell = board.addDigit(5, 1, 1, 7);

      Cell retrievedCell = board.getCell(5, 1, 1);
      assertThat(retrievedCell, notNullValue());
      assertThat(retrievedCell.getDigit(), is(7));
      assertThat(retrievedCell.getColor(), is(Color.RED));
   }

   @Test(expectedExceptions = InvalidLocationException.class)
   public void cannotQueryCellFromInvalidRegion() {
      board.getCell(9, 0, 0);
   }

   @Test(expectedExceptions = InvalidLocationException.class)
   public void cannotQueryOutOfBoundsPositionOnBoardOnXAxis() {
      board.getCell(1, 0, 3);
   }

   @Test(expectedExceptions = InvalidLocationException.class)
   public void cannotQueryOutOfBoundsPositionOnBoardOnYAxis() {
      board.getCell(8, 3, 0);
   }
}
