package com.github.scalamaze.mazegenerationalgorithm

import com.github.scalamaze.maze.Maze

class AldousBroderAlgorithm(protected val width: Int, protected val height: Int, protected val seed: Option[Long]= None, protected val cameraOn: Boolean = false) extends MazeGenerationAlgorithm {

  @annotation.tailrec
  private def go(x: Int, y: Int): Unit = {
    if (grid.flatten.exists(_.hasAllWalls)) {
      val cell = cellAt(x, y)
      val (direction, (nx, ny)) = random.shuffle(validNeighbors(x, y)).head
      val nextCell = cellAt(cell.x + nx, cell.y + ny)
      if (nextCell.hasAllWalls) {
        cell.knockDownWall(nextCell, direction)
        if (cameraOn) camera.capture(makeMaze())}
      go(nextCell.x, nextCell.y)
    }
  }

  def build(): Maze = {
    go(startX, startY)
    if (cameraOn) camera.createGif()
    makeMaze()
  }

}
