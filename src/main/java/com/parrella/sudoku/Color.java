package com.parrella.sudoku;

/**
 * Colors for digits on a board representing their state of correctness.
 */
public enum Color {
   /**
    * Initial state when no digit is present.
    */
   BLACK,

   /**
    * Value is correct.
    */
   GREEN,

   /**
    * Value is incorrect.
    */
   RED,

   /**
    * Value is not necessarily incorrect.
    */
   WHITE
}
