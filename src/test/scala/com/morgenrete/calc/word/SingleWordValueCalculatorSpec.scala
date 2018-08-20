package com.morgenrete.calc.word

import com.morgenrete.search.engine.Phrase
import com.morgenrete.search.engine.calc.word.{SingleWordValueCalculator, WordValue}
import org.scalatest.{FlatSpec, Matchers}

class SingleWordValueCalculatorSpec
  extends FlatSpec
  with Matchers {

  behavior of "SingleWordValueCalculator"

  it should "assign equal value to each word in phrase" in {
    val phrase = Phrase("I am Groot :)")
    val calculator = new SingleWordValueCalculator(phrase)

    calculator.value("I") should be (WordValue("I", 0.25))
    calculator.value("am") should be (WordValue("am", 0.25))
    calculator.value("Groot") should be (WordValue("Groot", 0.25))
    calculator.value(":)") should be (WordValue(":)", 0.25))
    calculator.value("ups") should be (WordValue("ups", 0.0))
  }
}
