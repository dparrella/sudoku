package com.parrella.sudoku;

import java.util.HashSet;
import java.util.Set;

/**
 * Representation of a region of a Sudoku board, containing 3x3 cells.
 */
class Region {

   private static final int REGION_WIDTH = 3;
   private static final int REGION_HEIGHT = 3;

   private final Cell[][] cells;
   private final Set<Integer> seenDigits;
   private final int offsetX;
   private final int offsetY;

   Region(Integer[][] cellData, int offsetX, int offsetY) {
      this.cells = new Cell[REGION_HEIGHT][REGION_WIDTH];
      this.seenDigits = new HashSet<Integer>();

      this.offsetX = offsetX;
      this.offsetY = offsetY;

      insert(cellData);
   }

   private void insert(Integer[][] cellData) {
      for (int x = 0; x < 3; x++) {
         for (int y = 0; y < 3; y++) {
            Integer value = cellData[x][y];
            Color color = value != null ? Color.GREEN : Color.BLACK;

            cells[x][y] = new Cell(value, color);

            if (value != null) {
               seenDigits.add(value);
            }
         }
      }
   }

   Cell getCell(int x, int y) {
      return cells[x][y];
   }

   boolean hasDigit(int digit) {
      return seenDigits.contains(digit);
   }

   void setCell(int x, int y, Cell cell) {
      Integer oldValue = cells[x][y].getDigit();

      if (oldValue != cell.getDigit()) {
         seenDigits.remove(oldValue);
      } else if (cell.getDigit() != null) {
         seenDigits.add(cell.getDigit());
      }

      cells[x][y] = cell;
   }

   int getOffsetX() {
      return offsetX;
   }

   int getOffsetY() {
      return offsetY;
   }
}
