package com.github.scalamaze.mazegenerationalgorithm

import com.github.scalamaze.maze.Maze

/** Aldous Broder Algorithm for Perfect Maze generation
  *
  * The interesting thing about this algorithm is that it's uniform, which means it generates all possible Mazes of a
  * given size with equal probability. It also requires no extra storage or stack. Pick a point, and move to a
  * neighboring cell at random. If an uncarved cell is entered, carve into it from the previous cell. Keep moving to
  * neighboring cells until all cells have been carved into. This algorithm yields Mazes with a low "river" factor, only
  * slightly higher than Kruskal's algorithm. (This means for a given size there are more Mazes with a low "river"
  * factor than high "river", since an average equal probability Maze has low "river".) The bad thing about this
  * algorithm is that it's very slow, since it doesn't do any intelligent hunting for the last cells, where in fact it's
  * not even guaranteed to terminate. However since the algorithm is simple it can move over many cells quickly, so
  * finishes faster than one might think. On average it takes about seven times longer to run than standard algorithms,
  * although in bad cases it can take much longer if the random number generator keeps making it avoid the last few
  * cells. This can be done as a wall adder if the boundary wall is treated as a single vertex, i.e. if a move goes to
  * the boundary wall, teleport to a random point along the boundary before moving again. As a wall adder this runs
  * nearly twice as fast, because the boundary wall teleportation allows quicker access to distant parts of the Maze.
  *
  * @param width    Width of the Maze.
  * @param height   Height of the Maze.
  * @param seed     Generate the Maze from a seed, leave empty for a random seed.
  * @param cameraOn On the camera: Allow gif creation.
  */
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

  def build: Maze = {
    go(startX, startY)
    if (cameraOn) camera.createGif()
    makeMaze()
  }

}
