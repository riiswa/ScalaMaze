package com.github.scalamaze.mazegenerationalgorithm

import com.github.scalamaze.maze.Maze
import com.github.scalamaze.maze.cells.Cell

import scala.collection.mutable

/** Prim's Algorithm for Perfect Maze generation
  *
  *  This is Prim's algorithm to produce a minimum spanning tree, operating over uniquely random edge weights.
  *  It requires storage proportional to the size of the Maze. Start with any vertex (the final Maze will be the same
  *  regardless of which vertex one starts with). Proceed by selecting the passage edge with least weight connecting the
  *  Maze to a point not already in it, and attach it to the Maze. The Maze is done when there are no more edges left to
  *  consider. To efficiently select the next edge, a priority queue (usually implemented with a heap) is needed to
  *  store the frontier edges. Still, this algorithm is somewhat slow, because selecting elements from and maintaining
  *  the heap requires log(n) time. Therefore Kruskal's algorithm which also produces a minimum spanning tree can be
  *  considered better, since it's faster and produces Mazes with identical texture. In fact, given the same random
  *  number seed, identical Mazes can be created with both Prim's and Kruskal's algorithms.
  *
  * @param width    Width of the Maze.
  * @param height   Height of the Maze.
  * @param seed     Generate the Maze from a seed, leave empty for a random seed.
  * @param cameraOn On the camera: Allow gif creation.
  */
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

  def build: Maze = {
    go()
    if (cameraOn) camera.createGif()
    makeMaze()
  }

}
