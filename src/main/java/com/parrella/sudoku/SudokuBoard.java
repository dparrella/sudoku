package com.parrella.sudoku;

import com.parrella.sudoku.exception.InvalidLocationException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Representation of a Sudoku board, which consists of 9 regions containing 3x3 cells.
 *
 * The regions are arranged like this:
 *
 * 0 1 2
 * 3 4 5
 * 6 7 8
 *
 * Each region contains 3x3 cells which are addressed like so:
 *
 * (0, 0) (1, 0) (2, 0)
 * (0, 1) (1, 1) (2, 1)
 * (0, 2) (1, 2) (2, 2)
 */
public class SudokuBoard {

   private static final int NUM_REGIONS = 9;

   private static final int BOARD_SIZE = 9;

   // Rows x Columns configuration.
   private final Cell[][] boardData = new Cell[BOARD_SIZE][BOARD_SIZE];

   private static final int REGION_SIZE = 3;

   private static final Region[] REGIONS = new Region[NUM_REGIONS];
   static {
      REGIONS[0] = new Region(0, 0);
      REGIONS[1] = new Region(3, 0);
      REGIONS[2] = new Region(6, 0);
      REGIONS[3] = new Region(0, 3);
      REGIONS[4] = new Region(3, 3);
      REGIONS[5] = new Region(6, 3);
      REGIONS[6] = new Region(0, 6);
      REGIONS[7] = new Region(3, 6);
      REGIONS[8] = new Region(6, 6);
   }

   // Lists of integer sets that allow us to easily track numbers already seen in a row or column.
   private final List<Set<Integer>> rowDigits;
   private final List<Set<Integer>> columnDigits;

   public SudokuBoard() {
      rowDigits = new ArrayList<Set<Integer>>(BOARD_SIZE);
      initializeSets(rowDigits, BOARD_SIZE);

      columnDigits = new ArrayList<Set<Integer>>(BOARD_SIZE);
      initializeSets(columnDigits, BOARD_SIZE);
   }

   /**
    * Set data for a region of the board. Intended for board setup.
    *
    * @param regionId The region ID (0-8).
    * @param cellData The data for the region.
    */
   public void setRegion(int regionId, Integer[][] cellData) {
      Region region = getRegion(regionId);

      insert(cellData, region.getStartX(), region.getStartY());
   }

   /**
    * Gets the cell at the specified location.
    *
    * @param regionId The region ID (0-8).
    * @param x The x-axis coordinate (0-2).
    * @param y The y-axis coordinate (0-2).
    * @return The cell at the location.
    */
   public Cell getCell(int regionId, int x, int y) {
      Region region = getRegion(regionId);
      validateDimensions(x, y);

      int offsetX = region.getStartX() + x;
      int offsetY = region.getStartY() + y;

      return boardData[offsetY][offsetX];
   }

   /**
    * Adds a digit to the board. This is the entry point for user-defined digits.
    *
    * @param regionId The region ID (0-8).
    * @param x The x-axis coordinate (0-2).
    * @param y The y-axis coordinate (0-2).
    * @param digit The digit to add to the board.
    * @return The updated Cell.
    */
   public Cell addDigit(int regionId, int x, int y, int digit) {
      Region region = getRegion(regionId);
      validateDimensions(x, y);

      int offsetX = region.getStartX() + x;
      int offsetY = region.getStartY() + y;

      Color color = getColor(region, offsetX, offsetY, digit);

      Cell cell = new Cell(digit, color);

      // Keep track of seen digits.
      Cell oldCell = boardData[offsetY][offsetX];
      removeDigit(region, offsetX, offsetY, oldCell.getDigit());
      trackDigit(region, offsetX, offsetY, digit);

      boardData[offsetY][offsetX] = cell;
      return cell;
   }

   private void initializeSets(List<Set<Integer>> list, int numSets) {
      IntStream.range(0, numSets).forEach(n -> list.add(new HashSet<>()));
   }

   private Color getColor(Region region, int x, int y, int digit) {
      if (region.hasDigit(digit) || rowDigits.get(y).contains(digit) || columnDigits.get(x).contains(digit)) {
         return Color.RED;
      }
      return Color.WHITE;
   }

   private void removeDigit(Region region, int x, int y, Integer oldDigit) {
      if (oldDigit == null) {
         return;
      }

      region.removeDigit(oldDigit);
      columnDigits.get(x).remove(oldDigit);
      rowDigits.get(y).remove(oldDigit);
   }

   private void trackDigit(Region region, int x, int y, int digit) {
      region.sawDigit(digit);
      columnDigits.get(x).add(digit);
      rowDigits.get(y).add(digit);
   }

   /**
    * Loads data on to the board by row.
    *
    * @param cellData The data to load.
    * @param offsetX The x offset (based on region).
    * @param offsetY The y offset (based on region).
    */
   private void insert(Integer[][] cellData, int offsetX, int offsetY) {
      for (int y = 0; y < cellData.length; y++) {
         for (int x = 0; x < cellData[y].length; x++) {
            Integer value = cellData[y][x];
            Color color = value != null ? Color.GREEN : Color.BLACK;

            int boardX = offsetX + x;
            int boardY = offsetY + y;
            boardData[boardY][boardX] = new Cell(value, color);

            rowDigits.get(boardY).add(value);
            columnDigits.get(boardX).add(value);
         }
      }
   }

   private Region getRegion(int regionId) {
      if (regionId < 0 || regionId > REGIONS.length - 1) {
         throw new InvalidLocationException();
      }
      return REGIONS[regionId];
   }


   private void validateDimensions(int x, int y) {
      validateDimension(x);
      validateDimension(y);
   }

   private void validateDimension(int dimension) {
      if (dimension < 0 || dimension > REGION_SIZE - 1) {
         throw new InvalidLocationException();
      }
   }
}
