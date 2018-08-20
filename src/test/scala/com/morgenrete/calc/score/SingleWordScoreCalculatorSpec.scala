package com.morgenrete.calc.score

import com.morgenrete.search.engine.Phrase
import com.morgenrete.search.engine.calc.score.{Score, SingleWordScoreCalculator}
import com.morgenrete.util.FileHelpers
import org.scalatest.{FlatSpec, Matchers}

class SingleWordScoreCalculatorSpec
  extends FlatSpec
  with Matchers
  with FileHelpers {

  behavior of "SingleWordScoreCalculator"

  it should "calculate scores with single word value calculator" in {
    val phrase = Phrase("I am Groot :)")
    val filesByWord = Map(
      "I" -> List(file1),
      "am" -> List(file1, file2),
      "Groot" -> List(file1),
      ":)" -> List(file1, file2)
    )

    val calculator = new SingleWordScoreCalculator

    val scores = calculator.scores(phrase, filesByWord)

    val expected = List(
      Score(file1, 1.0),
      Score(file2, 0.5)
    )
    scores should be (expected)
  }
}
