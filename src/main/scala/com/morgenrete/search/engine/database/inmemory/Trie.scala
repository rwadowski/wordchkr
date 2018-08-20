package com.morgenrete.search.engine.database.inmemory

import java.io.File

import scala.annotation.tailrec

object Trie {
  def apply(): Trie = new Trie(None, Map.empty, isWord = false, Set.empty)
}

case class Trie private (character: Option[Char],
                         children: Map[Char, Trie],
                         isWord: Boolean,
                         files: Set[File]) {

  private val isRoot: Boolean = character.isEmpty

  private def toWordNode(file: File): Trie = if(isWord && files.contains(file)) this else Trie(character, children, isWord = true, files + file)

  def +(word: String, file: File): Trie = {
    val newNode = word.headOption match {
      case Some(ch) => updateNode(ch, word, file)
      case None => createNode(word, file)
    }
    if(isRoot) {
      Trie(None, children + (newNode.character.get -> newNode), isWord = false, files)
    } else {
      Trie(character, children + (newNode.character.get -> newNode), isWord, files + file)
    }
  }

  private def updateNode(ch: Char, word: String, file: File): Trie = children.get(ch) match {
    case Some(node) => updateNode(word, file, node)
    case None => createNode(word, file)
  }

  private def updateNode(word: String, file: File, node: Trie): Trie = word.length match {
    case 1 => node.toWordNode(file)
    case _ => node + (word.tail, file)
  }

  private def createNode(word: String, file: File): Trie = word.length match {
    case 1 => Trie(Some(word.head), Map.empty, isWord = true, files + file)
    case _ =>
      val child = createNode(word.tail, file)
      Trie(Some(word.head), Map(child.character.get -> child), isWord = false, files)
  }

  def search(prefix: String): Set[String] = {
    def loop(node: Trie, word: String, result: Set[String] = Set.empty): Set[String] = {
      val nextResult = if(node.isWord) {
        result + (word + node.toString)
      } else {
        result
      }
      if(node.children.isEmpty) {
        nextResult
      } else {
        node.children.values.flatMap(child => loop(child, word + node.toString, nextResult)).toSet
      }
    }
    val nodeOpt = find(prefix, this)
    nodeOpt.map(n => n.children.values.flatMap(next => loop(next, prefix))).map(_.toSet).getOrElse(Set.empty)
  }

  def findFilesForWord(word: String): List[File] = {
    find(word, this).map(_.files.toList).getOrElse(List.empty)
  }

  @tailrec
  private def find(word: String, node: Trie): Option[Trie] = if(word.isEmpty) {
    Some(node)
  } else {
    node.children.get(word.head) match {
      case Some(next) => find(word.tail, next)
      case None => None
    }
  }

  override lazy val toString: String = character match {
    case Some(ch) => ch.toString
    case _ => ""
  }
}
