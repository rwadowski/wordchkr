package com.morgenrete.search.engine

import java.io.File

import com.morgenrete.search.engine.calc.score.{Score, ScoreCalculator, SingleWordScoreCalculator}
import com.morgenrete.search.engine.database.Database
import com.morgenrete.search.engine.database.inmemory.InmemoryDatabase
import com.morgenrete.search.engine.load.DataLoader

object Engine {

  def withInmemoryDBAndSimpleCalculator(loader: DataLoader): Engine = {
    withInmemoryDB(loader, new SingleWordScoreCalculator)
  }

  def withInmemoryDB(loader: DataLoader, calculator: ScoreCalculator): Engine = {
    val wordsByFile = loader.load
    var database: Database = InmemoryDatabase()
    wordsByFile.foreach { case (file, words) =>
      words.foreach { word =>
        database = database.add(file, word)
      }
    }
    new Engine(database, calculator)
  }
}

class Engine private (database: Database, calculator: ScoreCalculator) {

  def scores(phrase: Phrase, limit: Int): List[Score] =
    scores(phrase, Some(limit))

  def scores(phrase: Phrase, limit: Option[Int] = None): List[Score] = {
    val filesByWord = phrase.words.map { word =>
      word -> findFilesForWord(word)
    }.toMap
    val scores = calculator.scores(phrase, filesByWord)
    limit.map(scores.take).getOrElse(scores)
  }

  private def findFilesForWord(word: String): List[File] =
    database.findFileForWord(word)

}

object Phrase {
  def apply(string: String): Phrase = new Phrase(string.split(" ").toSeq)
}

case class Phrase(words: Seq[String])