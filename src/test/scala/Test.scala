import com.github.scalamaze.mazegenerationalgorithm.RecursiveBacktracker

object Test extends App {
  val mga = new RecursiveBacktracker(10, 10)
  mga.build().writeSVG("test.svg")
}
