package com.morgenrete.search.engine.database

import java.io.File

trait Database {

  def add(file: File, word: String): Database

  def findFileForWord(word: String): List[File]
}
