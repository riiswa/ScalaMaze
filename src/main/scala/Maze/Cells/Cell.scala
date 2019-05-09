package com.github.scalamaze.maze.cells

import scala.collection.{immutable, mutable}

/** Companion object of Cell */
object Cell {
  val WALL_PAIRS: immutable.Map[Char, Char] = Map('N' -> 'S', 'S' -> 'N', 'E' -> 'W', 'W' -> 'E')
}

/** Cell of the Maze grid that may or may not have walls .
  *
  * @param x Current x pos of the cell.
  * @param y Current y pos of the cell.
  */
class Cell(val x: Int, val y: Int){
  private val walls: mutable.Map[Char, Boolean] = mutable.Map('N' -> true, 'S' -> true, 'W' -> true, 'E' -> true)

  /** Return true if a cell has all walls. */
  def hasAllWalls: Boolean = walls.values.forall(_ == true)

  /** Knock down a wall of the cell and his neighbor.
    *
    * @param otherCell Neighbor of cell.
    * @param wall Wall to knock down.
    */
  def knockDownWall(otherCell: Cell, wall: Char): Unit = {
    this.walls(wall) = false
    otherCell.walls(Cell.WALL_PAIRS(wall)) = false
  }

  override def toString: String = "[%s -> %s]".format((x, y).toString(), getNotDownWall.mkString(" "))

  def getNotDownWall: Seq[Char] = walls.filter(_._2 == true).keys.toSeq

  def getDownWall: Seq[Char] = walls.filter(_._2 == false).keys.toSeq
}
