package com.github.scalamaze.mazegenerationalgorithm

import com.github.scalamaze.maze.Maze
import com.github.scalamaze.utils.Tree

import scala.collection.mutable

/** Kruskal's Algorithm for Perfect Maze generation
  *
  *  This is Kruskal's algorithm to produce a minimum spanning tree. It's interesting because it doesn't "grow" the Maze
  *  like a tree, but rather carves passage segments all over the Maze at random, but yet still results in a perfect
  *  Maze in the end. It requires storage proportional to the size of the Maze, along with the ability to enumerate each
  *  edge or wall between cells in the Maze in random order (which usually means creating a list of all edges and
  *  shuffling it randomly). Label each cell with a unique id, then loop over all the edges in random order. For each
  *  edge, if the cells on either side of it have different id's, then erase the wall, and set all the cells on one side
  *  to have the same id as those on the other. If the cells on either side of the wall already have the same id, then
  *  there already exists some path between those two cells, so the wall is left alone so as to not create a loop. This
  *  algorithm yields Mazes with a low "river" factor, but not as low as Prim's algorithm. Merging the two sets on
  *  either side of the wall will be a slow operation if each cell just has a number and are merged by a loop. Merging
  *  as well as lookup can be done in near constant time by using the union-find algorithm: Place each cell in a tree
  *  structure, with the id at the root, in which merging is done quickly by splicing two trees together. Done right,
  *  this algorithm runs reasonably fast, but is slower than most because of the edge list and set management.
  *
  * @param width    Width of the Maze.
  * @param height   Height of the Maze.
  * @param seed     Generate the Maze from a seed, leave empty for a random seed.
  * @param cameraOn On the camera: Allow gif creation.
  */
class KruskalsAlgorithm(protected val width: Int, protected val height: Int, protected val seed: Option[Long]= None, protected val cameraOn: Boolean = false) extends MazeGenerationAlgorithm {
  private val sets = Array.fill[Tree](width, height){new Tree}

  private val edges: mutable.ArrayStack[(Int, Int, Char)] =
    random.shuffle(mutable.ArrayStack.tabulate[(Int, Int, Char)](width, height) { (x, y) => (x, y, 'N')}.flatten ++
      mutable.ArrayStack.tabulate[(Int, Int, Char)](width, height) { (x, y) => (x, y, 'W')}.flatten)

  @annotation.tailrec
  private def go(): Unit = {
    if (edges.nonEmpty) {
      val (x, y, direction) = edges.pop()
      val f = neighbors.toMap
      val (nx, ny) = (x + f(direction)._1, y + f(direction)._2)
      if (validCoords(nx, ny)) {
        val (set1, set2) = (sets(x)(y), sets(nx)(ny))

        if (!set1.isConnectedTo(set2)) {
          set1.connectTo(set2)
          cellAt(x, y).knockDownWall(cellAt(nx, ny), direction)
          if (cameraOn) camera.capture(makeMaze())
        }
      }
      go()
    }
  }

  def build: Maze = {
    go()
    if (cameraOn) camera.createGif()
    makeMaze()
  }


}
