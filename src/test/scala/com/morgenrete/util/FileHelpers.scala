package com.morgenrete.util

import java.io.File

trait FileHelpers {

  protected val dataPath: String = getClass.getResource("/data").getPath

  protected val file1Path: String = s"$dataPath/dir1/file1.txt"
  protected val file2Path: String = s"$dataPath/dir1/file2.txt"
  protected val file3Path: String = s"$dataPath/file3.txt"

  protected val data: File = new File(dataPath)
  protected val file1: File = new File(file1Path)
  protected val file2: File = new File(file2Path)
  protected val file3: File = new File(file3Path)

  protected val file1Content: Seq[String] = Seq(
    "to", "be", "or", "not", "to", "be", "Hi", "I", "am", "Robert",
    "Warsaw", "London", "New", "York", "One", "Two"
  )

  protected val file2Content: Seq[String] = Seq(
    "Aaa", "bbb", "ccc", "ddd", "School", "police", "two", "eggs", "be", "not"
  )

  protected val file3Content: Seq[String] = Seq(
    "I", "am", "file", "I", "am", "Groot", "I'll", "be", "back", "Luke,", "I'm",
    "your", "father", "be", "be"
  )
}
