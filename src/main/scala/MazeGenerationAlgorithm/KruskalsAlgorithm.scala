package com.github.scalamaze.mazegenerationalgorithm

import com.github.scalamaze.maze.Maze
import com.github.scalamaze.utils.Tree

import scala.collection.mutable

class KruskalsAlgorithm(protected val width: Int, protected val height: Int, protected val seed: Option[Long]= None) extends MazeGenerationAlgorithm {
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
        }
      }
      go()
    }
  }

  def build(): Maze = {
    go()
    makeMaze()
  }


}
