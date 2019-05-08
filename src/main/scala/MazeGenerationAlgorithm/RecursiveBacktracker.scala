package com.github.scalamaze.mazegenerationalgorithm

import com.github.scalamaze.maze._

import scala.collection.mutable

/** Recursive Backtracker Algorithm for Perfect Maze generation
  *
  * This is somewhat related to the recursive backtracker solving method, and requires stack up to the size of the Maze.
  * When carving, be as greedy as possible, and always carve into an unmade section if one is next to the current cell.
  * Each time you move to a new cell, push the former cell on the stack. If there are no unmade cells next to the
  * current position, pop the stack to the previous position. The Maze is done when you pop everything off the stack.
  * This algorithm results in Mazes with about as high a "river" factor as possible, with fewer but longer dead ends,
  * and usually a very long and twisty solution. When implemented efficiently it runs fast, with only highly specialized
  * algorithms being faster. Recursive backtracking doesn't work as a wall adder, because doing so tends to result in a
  * solution path that follows the outside edge, where the entire interior of the Maze is attached to the boundary by a
  * single stem.
  *
  * @param width Width of the Maze.
  * @param height Height of the Maze.
  * @param seed Generate the Maze from a seed, leave empty for a random seed
  */
class RecursiveBacktracker(protected val width: Int, protected val height: Int, protected val seed: Option[Long]= None) extends MazeGenerationAlgorithm {
  private val stack: mutable.ArrayStack[Cell] = new mutable.ArrayStack[Cell]()

  /** Recursive Backtracker running.
    *
    * @param x X coords of current Cell.
    * @param y Y coords of current Cell
    * @param firstTime true for the first iteration because the stack is empty.
    */
  private def getNextCell(x: Int, y: Int, neighbors: Neighbors): Cell =
    if (neighbors.nonEmpty) {
      val currentCell = cellAt(x, y)
      stack.push(currentCell)
      val (direction, nextCoords) = neighbors.head
      val nextCell = cellAt(x + nextCoords._1, y + nextCoords._2)
      currentCell.knockDownWall(nextCell, direction)
      nextCell
    } else {
      stack.pop()
    }

  @annotation.tailrec
  private def go(x: Int, y: Int, firstTime: Boolean = false): Unit ={
    if (stack.nonEmpty | firstTime){
      val neighbors: Neighbors = notVisitedNeighbors(x, y)
      /* Définis une autre fonction pour alléger */
/*
      val nextCell: Cell = {
      if (neighbors.nonEmpty) {
        val currentCell = cellAt(x, y)
        stack.push(currentCell)
        val (direction, nextCoords) = neighbors.head
        val nextCell = cellAt(x + nextCoords._1, y + nextCoords._2)
        currentCell.knockDownWall(nextCell, direction)
        nextCell
      } else {
        stack.pop()
      }}
*/
      val nextCell: Cell = getNextCell(x, y, neighbors)

      go(nextCell.x, nextCell.y)
    }
  }

  def build(): Maze = {
    go(startX, startY, firstTime = true)
    makeMaze()
  }
}
