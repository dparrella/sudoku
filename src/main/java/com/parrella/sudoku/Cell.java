package com.parrella.sudoku;

/**
 * Representation of a cell within a region.
 */
public class Cell {

   private Integer digit;
   private Color color;

   public Cell(Integer digit, Color color) {
      this.digit = digit;
      this.color = color;
   }

   public Integer getDigit() {
      return digit;
   }

   public void setDigit(Integer digit) {
      this.digit = digit;
   }

   public Color getColor() {
      return color;
   }

   public void setColor(Color color) {
      this.color = color;
   }
}
