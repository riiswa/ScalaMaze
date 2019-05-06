import com.github.scalamaze.mazegenerationalgorithm._


object Test extends App {
  val mga = new RecursiveBacktracker(80, 80)
  mga.build().writeSVG("test.svg")

}
