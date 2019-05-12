import com.github.scalamaze.mazegenerationalgorithm.PrimsAlgorithm

object TestPrimsAlgorithm extends App {
  val t0 = System.nanoTime()
  val pa = new PrimsAlgorithm(20, 20, cameraOn = true)
  pa.build.writeSVG("test.svg")
  val t1 = System.nanoTime()
  println("Elapsed time: " + (t1 - t0) + "ns")
}
