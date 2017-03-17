package com.parrella.sudoku;

import com.parrella.sudoku.exception.InvalidLocationException;

public class SudokuBoard {

   private static final int NUM_REGIONS = 9;

   private final Region[] regions = new Region[NUM_REGIONS];

   public void setRegion(int regionId, Integer[][] cellData) {
      validateRegion(regionId);

      int regionXOffset = getOffsetX(regionId);
      int regionYOffset = getOffsetY(regionId);
      Region region = new Region(cellData, regionXOffset, regionYOffset);
      regions[regionId] = region;
   }

   private int getOffsetX(int regionId) {
      return (regionId % 3) * 3;
   }

   private int getOffsetY(int regionId) {
      if (regionId < 3) {
         return 0;
      } else if (regionId < 6) {
         return 3;
      } else if (regionId < 9) {
         return 6;
      }
      throw new IllegalArgumentException();
   }

   private void validateRegion(int regionId) {
      if (regionId < 0 || regionId > regions.length) {
         throw new InvalidLocationException();
      }
   }

   public Cell getCell(int regionId, int x, int y) {
      validateRegion(regionId);

      Region region = regions[regionId];
      return region.getCell(x, y);
   }

   public Cell addDigit(int regionId, int x, int y, int digit) {
      validateRegion(regionId);

      Region region = regions[regionId];

      Color color = null;
      if (region.hasDigit(digit)) {
         color = Color.RED;
      }

      // Check x-axis.

      // Check y-axis.

      Cell cell = new Cell(digit, color);
      region.setCell(x, y, cell);
      return cell;
   }
}
