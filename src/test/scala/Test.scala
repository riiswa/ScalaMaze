import com.github.scalamaze.mazegenerationalgorithm.RecursiveBacktracker

object Test extends App {
  val mga = new RecursiveBacktracker(100, 100)
  mga.build().writeSVG("test.svg")
}
