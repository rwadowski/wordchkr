package com.morgenrete

import java.io.File
import java.text.DecimalFormat

import com.morgenrete.search.engine.calc.score.Score
import com.morgenrete.search.engine.load.FileLoader
import com.morgenrete.search.engine.{Engine, Phrase}

import scala.annotation.tailrec
import scala.io.StdIn

object Search {

  private val formatter = new DecimalFormat("#.##")

  def main(args: Array[String]): Unit = {
    if(args.length == 0) {
      println("There is no path for directory with files")
      return
    }
    val directory = new File(args(0))
    if(!directory.exists()) {
      println(s"There is no ${directory.getAbsoluteFile}, stopping")
      return
    }
    val loader = new FileLoader(directory)
    println(loader.summary)
    val engine = Engine.withInmemoryDBAndSimpleCalculator(loader)
    val command = StdIn.readLine("command> ")
    executeCommand(command, engine)
  }

  @tailrec
  private def executeCommand(cmd: String, engine: Engine): Unit = cmd match {
    case ":help" =>
      println(help)
      val command = StdIn.readLine("command> ")
      executeCommand(command, engine)
    case ":quit" => (): Unit
    case ":search" =>
      val input = StdIn.readLine("search> ")
      val phrase = Phrase(input)
      val result = engine.scores(phrase)
      println(formatResult(result))
      val command = StdIn.readLine("command> ")
      executeCommand(command, engine)
    case _ =>
      println(s"$cmd is unknown, use :help command to see details")
      val command = StdIn.readLine("command> ")
      executeCommand(command, engine)
  }

  private def formatResult(scores: List[Score]): String =
    scores.map(s => s"${s.file.getName} - ${formatter.format(s.value * 100)} %").mkString("\n")

  private lazy val help: String =
    s"""
       |Commands
       |:help        prints this help
       |:quit        ends program
       |:search      enters search mode
     """.stripMargin

}
