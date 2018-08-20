package com.morgenrete.loader

import com.morgenrete.search.engine.load.FileLoader
import com.morgenrete.util.FileHelpers
import org.scalatest.{FlatSpec, Matchers}

class FileLoaderSpec
  extends FlatSpec with Matchers with FileHelpers {



  behavior of "File loader"

  it should "load path which is file" in {
    val dir = file3
    val expected = Map(
      dir -> file3Content
    )
    val loader = new FileLoader(dir)
    val words = loader.load

    words should be (expected)
  }

  it should "load path which is directory" in {
    val expected = Map(
      file1 -> file1Content,
      file2 -> file2Content,
      file3 -> file3Content
    )
    val loader = new FileLoader(data)
    val words = loader.load

    words should be (expected)
  }
}
