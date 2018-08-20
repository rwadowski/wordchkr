package com.morgenrete.search.engine

import com.morgenrete.search.engine.calc.score.Score
import com.morgenrete.search.engine.load.FileLoader
import com.morgenrete.util.FileHelpers
import org.scalatest.{FlatSpec, Matchers}

class EngineSpec
  extends FlatSpec
  with Matchers
  with FileHelpers {

  behavior of "Scoring engine"

  it should "build inmemory database from directory and be able to find score for given phrase with no limit" in {
    val phrase = Phrase("I am Groot :D")

    val fileLoader = new FileLoader(data)
    val engine = Engine.withInmemoryDBAndSimpleCalculator(fileLoader)

    val scores = engine.scores(phrase)

    val expected = List(
      Score(file3, 0.75),
      Score(file1, 0.5)
    )

    scores should be (expected)
  }

  it should "build inmemory database from directory and be able to find score for given phrase with limit" in {
    val phrase = Phrase("I am Groot")

    val fileLoader = new FileLoader(data)
    val engine = Engine.withInmemoryDBAndSimpleCalculator(fileLoader)

    val scores = engine.scores(phrase, 1)

    val expected = List(
      Score(file3, 1.0)
    )

    scores should be (expected)
  }
}
