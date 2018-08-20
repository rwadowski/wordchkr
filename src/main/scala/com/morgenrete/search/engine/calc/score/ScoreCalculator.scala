package com.morgenrete.search.engine.calc.score

import java.io.File

import com.morgenrete.search.engine.Phrase
import com.morgenrete.search.engine.calc.word.{WordCalculator, WordValue}

import scala.annotation.tailrec

trait ScoreCalculator {

  protected def wordCalculator(phrase: Phrase): WordCalculator

  def scores(phrase: Phrase, filesByWord: Map[String, List[File]]): List[Score] = {
    val calculator = wordCalculator(phrase)
    scores(filesByWord, calculator).values.toList.sorted
  }

  private def scores(wordsByFile: Map[String, List[File]],
                     wordValueCalculator: WordCalculator,
                     result: Map[File, Score] = Map.empty): Map[File, Score] = {
    val partialScores = wordsByFile.map { case (word, files) => scoresForWord(files, wordValueCalculator.value(word)) }
    partialScores.foldLeft(Map.empty[File, Score]) { case (res, map) =>
      add(res, map)
    }
  }

  @tailrec
  private def add(res: Map[File, Score], map: Map[File, Score]): Map[File, Score] =
    if(map.isEmpty) {
      res
    } else {
      val (file, score) = map.head
      val next = add(res, file, score)
      add(next, map - file)
    }


  private def add(res: Map[File, Score], file: File, score: Score): Map[File, Score] = {
    val current = res.getOrElse(file, Score(file, 0.0))
    res + (file -> (current + score))
  }

  @tailrec
  private def scoresForWord(files: List[File],
                            wordValue: WordValue,
                            result: Map[File, Score] = Map.empty): Map[File, Score] = files match {
    case head :: tail =>
      val score = result.getOrElse(head, Score(head, 0)) + wordValue.value
      scoresForWord(tail, wordValue, result + (head -> score))
    case Nil => result
  }
}
