package com.morgenrete.database

import com.morgenrete.search.engine.database.inmemory.Trie
import com.morgenrete.util.FileHelpers
import org.scalatest.{FlatSpec, Matchers}

class TrieSpec
  extends FlatSpec
  with Matchers
  with FileHelpers {

  behavior of "Trie"

  it should "add words correctly" in {
    var trie = Trie()
    trie = trie + ("armageddon", file1)
    trie = trie + ("arms", file1)
    trie = trie + ("arms", file2)
    trie = trie + ("armory", file1)
    trie = trie + ("alpha", file1)

    val words = trie.search("arm")
    val files = trie.findFilesForWord("arms")

    words should be (Set("armageddon", "arms", "armory"))
    files.toSet should be (List(file1, file2).toSet)
  }
}
