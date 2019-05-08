import com.github.scalamaze.mazegenerationalgorithm.RecursiveBacktracker

object Test extends App {
  val mga = new RecursiveBacktracker(5, 5)
  mga.build().writeSVG("test.svg")
}
