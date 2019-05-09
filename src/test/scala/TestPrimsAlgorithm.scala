import com.github.scalamaze.mazegenerationalgorithm.PrimsAlgorithm

object TestPrimsAlgorithm extends App {
  val pa = new PrimsAlgorithm(50, 50)
  pa.build().writeSVG("test.svg")

}
