package com.github.scalamaze.mazegenerationalgorithm

import com.github.scalamaze.maze._

import scala.util.Random

/** Ancestor of Maze Generation Algorithm */
trait MazeGenerationAlgorithm {
  protected type Neighbors = Seq[(Char, (Int, Int))]
  protected final val neighbors: Neighbors = Seq('W' -> (-1, 0),
    'E' -> (1, 0),
    'S' -> (0, 1),
    'N' -> (0, -1)
  )
  protected val width: Int
  protected val height: Int
  if (width <= 1 | height <= 1) throw new IllegalArgumentException("width and height should be > 1.")
  protected val seed: Option[Long]
  protected val random: Random = if (seed.isDefined) new Random(seed.get) else new Random()
  protected val grid: Array[Array[Cell]] = Array.tabulate[Cell](width, height) {(x, y) => new Cell(x, y)}

  protected val startX: Int = random.nextInt(width - 1)
  protected val startY: Int = random.nextInt(height - 1)

  protected val endX: Int = random.nextInt(width - 1)
  protected val endY: Int = random.nextInt(height - 1)

  /** Return the cell at the x, y pos. */
  protected def cellAt(x: Int, y: Int): Cell = grid(x)(y)

  /** Return the neighbors that who are in grid limits. */
  protected def validNeighbors(x: Int, y: Int): Neighbors  = random.shuffle(neighbors).filter(
    neighbor => (0 until width contains  (x + neighbor._2._1)) && (0 until height contains (y + neighbor._2._2)))

  /** Return the not visited neighbors */
  protected def notVisitedNeighbors(x: Int, y: Int): Neighbors = validNeighbors(x, y).filter(
    neighbor => cellAt(x + neighbor._2._1, y + neighbor._2._2).hasAllWalls)

  def build(): Maze

  def makeMaze(): Maze = Maze(grid, width, height, seed, (startX, startY), (endX, endY))

  def validCoords(x: Int, y: Int): Boolean = (0 until width contains x) & (0 until height contains y)

}
