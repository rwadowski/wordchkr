package com.morgenrete.search.engine.calc.word

import com.morgenrete.search.engine.Phrase

class SingleWordValueCalculator(override val phrase: Phrase)
  extends WordCalculator {

  private val wordValue : Double = 1.0 / phrase.words.size

  def value(word: String): WordValue = {
    val value = if(phrase.words.contains(word)) wordValue else 0.0
    WordValue(word, value)
  }
}

