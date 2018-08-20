package com.morgenrete.search.engine.database.inmemory

import java.io.File

import com.morgenrete.search.engine.database.Database

object InmemoryDatabase {

  def apply(): InmemoryDatabase = new InmemoryDatabase(Trie())
}

class InmemoryDatabase private (trie: Trie) extends Database {

  override def add(file: File, word: String): Database =
    new InmemoryDatabase(trie + (word, file))

  override def findFileForWord(word: String): List[File] =
    trie.findFilesForWord(word)
}
