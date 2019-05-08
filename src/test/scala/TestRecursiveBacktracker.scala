import com.github.scalamaze.mazegenerationalgorithm.RecursiveBacktracker

object TestRecursiveBacktracker extends App {
  val mga = new RecursiveBacktracker(10, 10)
  mga.build().writeSVG("test.svg")
}
