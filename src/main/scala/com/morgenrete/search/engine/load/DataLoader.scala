package com.morgenrete.search.engine.load

import java.io.File

trait DataLoader {

  def load: Map[File, Seq[String]]

  def summary: String
}
