import com.github.scalamaze.mazegenerationalgorithm.AldousBroderAlgorithm

object TestAldousBroderAlgorithm extends App{
  val aba = new AldousBroderAlgorithm(50, 50)
  aba.build().writeSVG("test.svg")

}