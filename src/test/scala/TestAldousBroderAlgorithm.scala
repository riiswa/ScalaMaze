import com.github.scalamaze.mazegenerationalgorithm.AldousBroderAlgorithm

object TestAldousBroderAlgorithm extends App{
  val t0 = System.nanoTime()
  val aba = new AldousBroderAlgorithm(20, 20, cameraOn = true)
  aba.build.writeSVG("test.svg")
  val t1 = System.nanoTime()
  println("Elapsed time: " + (t1 - t0) + "ns")
}