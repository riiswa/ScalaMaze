import com.github.scalamaze.mazegenerationalgorithm.KruskalsAlgorithm

object TestKruskalsAlgorithm extends App{
  val ka = new KruskalsAlgorithm(50, 50)
  ka.build().writeSVG("test.svg")

}
