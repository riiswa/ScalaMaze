package com.github.scalamaze.mazegenerationalgorithm

import com.github.scalamaze.maze.Maze
import com.github.scalamaze.maze.cells.Cell

import scala.collection.mutable

class PrimsAlgorithm(protected val width: Int, protected val height: Int, protected val seed: Option[Long]= None, protected val cameraOn: Boolean = false) extends MazeGenerationAlgorithm {
  private val frontier = mutable.Set[Cell](cellAt(startX, startY))
  private val visited = mutable.Set[Cell]()


  @annotation.tailrec
  private def go(): Unit = {
    if (frontier.nonEmpty) {
      val cell = random.shuffle(frontier).head
      visited += cell
      frontier -= cell

      val visitedNeighbors = validNeighbors(cell.x, cell.y)
        .filter(v => visited contains cellAt(cell.x + v._2._1, cell.y + v._2._2))

      if (visitedNeighbors.nonEmpty) {
        val (direction, (x, y)) = random.shuffle(visitedNeighbors).head
        cell.knockDownWall(cellAt(cell.x + x, cell.y + y), direction)
        if (cameraOn) camera.capture(makeMaze())
      }

      frontier ++= validNeighbors(cell.x, cell.y)
        .filterNot(visitedNeighbors contains _)
        .map(v => cellAt(cell.x + v._2._1, cell.y + v._2._2))
      go()
    }
  }

  def build(): Maze = {
    go()
    if (cameraOn) camera.createGif()
    makeMaze()
  }

}
