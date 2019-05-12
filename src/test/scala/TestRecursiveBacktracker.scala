import com.github.scalamaze.mazegenerationalgorithm.RecursiveBacktracker

object TestRecursiveBacktracker extends App {
  val t0 = System.nanoTime()
  val mga = new RecursiveBacktracker(20, 20, cameraOn = true)
  mga.build.writeSVG("test.svg")
  val t1 = System.nanoTime()
  println("Elapsed time: " + (t1 - t0) + "ns")
}
