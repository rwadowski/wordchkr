package com.morgenrete.search.engine.calc.score

import java.io.File

case class Score(file: File, value: Double) extends Ordered[Score] {
//  require(value >= 0.0 && value <= 1.0)

  def +(v: Double): Score = Score(file, value + v)

  def +(other: Score): Score = {
    require(file == other.file, "Can not add different score for different files")
    Score(file, value + other.value)
  }

  override def compare(that: Score): Int = that.value.compareTo(value)
}
