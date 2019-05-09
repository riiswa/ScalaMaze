package com.github.scalamaze.maze

import java.io.{File, PrintWriter}

import com.github.scalamaze.maze.cells.Cell


/** All methods of a Maze.*/
trait MazeAncestor {
  val grid: Array[Array[Cell]]
  val width: Int
  val height: Int


  private def writeWall(pw: PrintWriter, x1: Int, y1: Int, x2: Int, y2: Int): Unit =
    pw.println(s"""<line x1=\"$x1\" y1=\"$y1\" x2=\"$x2\" y2=\"$y2\"/>""")

  private def initSVG(pw: PrintWriter, imageWidth: Int, imageHeight: Int, padding: Int): Unit = {
    val width = (imageWidth + 2*padding).toFloat
    val height = (imageHeight + 2*padding).toFloat
    val viewWidth = imageWidth + 2*padding
    val viewHeight = imageHeight + 2*padding

    pw.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
    pw.println("<svg xmlns=\"http://www.w3.org/2000/svg\"")
    pw.println("    xmlns:xlink=\"http://www.w3.org/1999/xlink\"")
    pw.println(s"""    width=\"$width\" height=\"$height\" viewBox=\"${-padding} ${-padding} $viewWidth $viewHeight\">""")
    pw.println("<defs>\n<style type=\"text/css\"><![CDATA[")
    pw.println("line {")
    pw.println("    stroke: #000000;\n    stroke-linecap: square;")
    pw.println("    stroke-width: 3;\n}")
    pw.println("]]></style>\n</defs>")

  }

  private def endSVG(pw: PrintWriter, imageHeight: Int, imageWidth: Int): Unit = {
    pw.println(s"""<line x1=\"0\" y1=\"0\" x2=\"$imageHeight\" y2=\"0\"/>""")
    pw.println(s"""<line x1=\"0\" y1=\"0\" x2=\"0\" y2=\"$imageWidth\"/>""")
    pw.println("</svg>")

  }

  def writeSVG(filePath: String): Unit = {
    val pw = new PrintWriter(new File(filePath))
    val aspectRatio: Int = width / height
    val padding: Int = 10
    val imageHeight: Int = 10 * height
    val imageWidth: Int = imageHeight * aspectRatio
    val (scy, scx) = (imageHeight / height, imageWidth / width)

    initSVG(pw, imageWidth, imageHeight, padding)

    grid.flatten.foreach(cell => {
      val (x, y) = (cell.x, cell.y)
      if (cell.getNotDownWall.contains('S')) writeWall(pw, x*scx, (y+1)* scy, (x+1)*scx, (y+1)*scy)
      if (cell.getNotDownWall.contains('E')) writeWall(pw, (x+1)*scx, y*scy, (x+1)*scx, (y+1)* scy)
    })

    endSVG(pw, imageHeight, imageWidth)

    pw.close()
  }
}

/** A Maze.
  *
  * @param grid Grid of the Maze.
  * @param width Width of the Maze.
  * @param height Height of the Maze.
  * @param seed Generate the Maze from a seed, leave empty for a random seed.
  * @param departPoint Coords of depart point.
  * @param arrivalPoint Coords of arrival point.
  */
case class Maze(grid: Array[Array[Cell]], width: Int, height: Int, seed: Option[Long], departPoint: (Int, Int), arrivalPoint: (Int, Int)) extends MazeAncestor