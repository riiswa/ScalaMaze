package com.github.scalamaze.utils

import java.io.{File, FileOutputStream}

import com.github.scalamaze.maze.Maze
import javax.imageio.ImageIO
import javax.imageio.stream.FileImageOutputStream
import org.apache.batik.transcoder.image.JPEGTranscoder
import org.apache.batik.transcoder.{TranscoderInput, TranscoderOutput}
import org.apache.commons.io.FileUtils


/** Camera to capture the maze generation*/
class Camera {
  private var cpt = 0
  new File("tmp/").mkdir()

  private def getName(i: Int): String = "tmp/" + "0" * {7 - i.toString.length} + i.toString + ".jpg"

  private def svg2jpg(): Unit = {
    val svgURI = new File("tmp/temp.svg").toURI.toString
    val inputSVGImage = new TranscoderInput(svgURI)
    val jpgOStream = new FileOutputStream(getName(cpt))
    val jpgImage = new TranscoderOutput(jpgOStream)
    val converter = new JPEGTranscoder()
    converter.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new java.lang.Float(0.9))
    converter.transcode(inputSVGImage, jpgImage)
    jpgOStream.flush()
    jpgOStream.close()
    cpt += 1
  }

  /** Add one screenshot to gif*/
  def capture(maze: Maze): Unit = {
    maze.writeSVG("tmp/temp.svg")
    svg2jpg()
    new File("tmp/temp.svg").delete()
  }

  /** Generate a gif */
  def createGif(): Unit = {
    val first = ImageIO.read(new File(getName(0)))
    val output = new FileImageOutputStream(new File("maze.gif"))
    val writer = new GifSequenceWriter(output, first.getType, 50, false)
    writer.writeToSequence(first)

    (1 until cpt)
      .map(i => new File(getName(i)))
      .foreach(image => writer.writeToSequence(ImageIO.read(image)))
    writer.close()
    output.close()
    FileUtils.deleteDirectory(new File("tmp/"))
  }




}
