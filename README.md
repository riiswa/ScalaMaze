# ScalaMaze
Implementation of some procedural maze generation algorithm in Scala.

![test](https://user-images.githubusercontent.com/24685602/57581816-4a8e6e00-74bd-11e9-837e-4c288d382a47.gif)



## Available Algorithms

- Recursive Backtracker
- Kruskal's Algorithm
- Prim's Algorithm
- Aldous Broder Algorithm

## Deployments

```
sbt publish-local
```

## How to generate a Maze

```scala
import com.github.scalamaze.mazegenerationalgorithm.KruskalsAlgorithm

object TestKruskalsAlgorithm extends App{
  val ka = new KruskalsAlgorithm(40, 40, cameraOn = true)
  ka.build().writeSVG("test.svg")
}
```

## Authors

- **Waris Radji** - *Initial work* - [riiswa](https://github.com/riiswa)

See also the list of [contributors](https://github.com/riiswa/scalamaze/contributors) who participated in this project.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE.md](https://github.com/riiswa/scalamaze/LICENSE) file for details

  
