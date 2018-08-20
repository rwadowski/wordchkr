package com.morgenrete.search.engine.load

import java.io.File

import scala.io.Source

class FileLoader(file: File) extends DataLoader {

  override lazy val load: Map[File, Seq[String]] = {
    if(file.isDirectory) {
      loadDirectory(file)
    } else {
      Map(file -> loadFile(file))
    }
  }

  override val summary: String = {
    val names = load.map { case (f, _) => f.getAbsolutePath }.mkString("\n")
    s"""
       |${load.size} files loaded from ${file.getAbsolutePath}:
       |$names
     """.stripMargin
  }

  //TODO - add accumulator in recursion
  private def loadDirectory(directory: File): Map[File, Seq[String]] = {
    val children = directory.listFiles().toSeq
    val (directories, files) = children.partition(_.isDirectory)
    val wordsByFile = files.map(f => f -> loadFile(f)).toMap
    val wordsByDir = directories.map(d => loadDirectory(d))
    wordsByFile ++ wordsByDir.foldLeft(Map.empty[File, Seq[String]])(_ ++ _)
  }

  private def loadFile(file: File): Seq[String] = {
    require(file.isFile, "Can't open directory as file")
    val src = Source.fromFile(file, "UTF-8")
    val lines = src.getLines().flatMap(_.split(" ")).toSeq
    src.close()
    lines
  }
}
