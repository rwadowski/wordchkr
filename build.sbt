name := "wordchkr"

version := "0.1"

scalaVersion := "2.12.6"

// https://mvnrepository.com/artifact/org.scalatest/scalatest
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test

parallelExecution in Test := false
