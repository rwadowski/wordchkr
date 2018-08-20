package com.morgenrete.search.engine.calc.score
import com.morgenrete.search.engine.Phrase
import com.morgenrete.search.engine.calc.word.{SingleWordValueCalculator, WordCalculator}

class SingleWordScoreCalculator extends ScoreCalculator {

  override protected def wordCalculator(phrase: Phrase): WordCalculator =
    new SingleWordValueCalculator(phrase)
}
