package com.parrella.sudoku;

import java.util.HashSet;
import java.util.Set;

/**
 * Representation of a region of a Sudoku board, containing 3x3 cells.
 */
class Region {

   private final int startX;
   private final int startY;
   private final Set<Integer> seenDigits;

   Region(int startX, int startY) {
      this.startX = startX;
      this.startY = startY;
      this.seenDigits = new HashSet<>();
   }

   public int getStartX() {
      return startX;
   }

   public int getStartY() {
      return startY;
   }

   boolean hasDigit(int digit) {
      return seenDigits.contains(digit);
   }

   void removeDigit(int digit) {
      seenDigits.remove(digit);
   }

   void sawDigit(int digit) {
      seenDigits.add(digit);
   }
}
