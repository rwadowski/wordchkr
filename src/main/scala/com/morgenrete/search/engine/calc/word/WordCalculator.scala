package com.morgenrete.search.engine.calc.word

import com.morgenrete.search.engine.Phrase

trait WordCalculator {

  def phrase: Phrase
  def value(word: String): WordValue
}

case class WordValue(word: String, value: Double)