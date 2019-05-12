import com.github.scalamaze.mazegenerationalgorithm.KruskalsAlgorithm

object TestKruskalsAlgorithm extends App{
  val t0 = System.nanoTime()
  val ka = new KruskalsAlgorithm(20, 20, cameraOn = true)
  ka.build().writeSVG("test.svg")
  val t1 = System.nanoTime()
  println("Elapsed time: " + (t1 - t0) + "ns")

}
