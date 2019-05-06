package com.github.scalamaze.maze

import java.io.{File, PrintWriter}

import collection.mutable
import collection.immutable

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
  val walls: mutable.Map[Char, Boolean] = mutable.Map('N' -> true, 'S' -> true, 'W' -> true, 'E' -> true)

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
}

/** All methods of a Maze.*/
trait MazeAncestor {
  val grid: Array[Array[Cell]]
  val width: Int
  val height: Int

  /** Graphic representation of the labyrinth as an SVG file/
    *
    * @param filePath The path of the file.
    */
  def writeSVG(filePath: String): Unit = {
    val pw = new PrintWriter(new File(filePath))
    val aspectRatio: Int = width / height
    val padding: Int = 10
    val imageHeight: Int = 10 * height
    val imageWidth: Int = imageHeight * aspectRatio
    val (scy, scx) = (imageHeight / height, imageWidth / width)

    def writeWall(x1: Int, y1: Int, x2: Int, y2: Int): Unit =
      pw.println("""<line x1="%d" y1="%d" x2="%d" y2="%d"/>""".format(x1, y1, x2, y2))

    pw.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
    pw.println("<svg xmlns=\"http://www.w3.org/2000/svg\"")
    pw.println("    xmlns:xlink=\"http://www.w3.org/1999/xlink\"")
    pw.println("    width=\"%f\" height=\"%f\" viewBox=\"%d %d %d %d\">".format(
      (imageWidth + 2*padding).toFloat, (imageHeight + 2*padding).toFloat,
      -padding, -padding, imageWidth + 2*padding, imageHeight + 2*padding))
    pw.println("<defs>\n<style type=\"text/css\"><![CDATA[")
    pw.println("line {")
    pw.println("    stroke: #000000;\n    stroke-linecap: square;")
    pw.println("    stroke-width: 3;\n}")
    pw.println("]]></style>\n</defs>")

    grid.flatten.foreach(cell => {
      val (x, y) = (cell.x, cell.y)
      if (cell.walls('S')) writeWall(x*scx, (y+1)* scy, (x+1)*scx, (y+1)*scy)
      if (cell.walls('E')) writeWall((x+1)*scx, y*scy, (x+1)*scx, (y+1)* scy)
    })


    pw.println("<line x1=\"0\" y1=\"0\" x2=\"%d\" y2=\"0\"/>".format(imageWidth))
    pw.println("<line x1=\"0\" y1=\"0\" x2=\"0\" y2=\"%d\"/>".format(imageHeight))
    pw.println("</svg>")
    pw.close()
  }
}

/** A Maze.
  *
  * @param grid Grid of the Maze.
  * @param width Width of the Maze.
  * @param height Height of the Maze.
  * @param seed Generate the Maze from a seed, leave empty for a random seed
  */
case class Maze(grid: Array[Array[Cell]], width: Int, height: Int, seed: Option[Long]) extends MazeAncestor