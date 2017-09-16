import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import scalariform.formatter.preferences._


name := "taglesfinal-scalamde"

version := "0.1.0"

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1",
  "com.typesafe.slick" %% "slick" % "3.2.1",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1",
  "org.typelevel" %% "cats-core" % "1.0.0-MF"
)

val preferences =
  ScalariformKeys.preferences := ScalariformKeys.preferences.value
    .setPreference(AlignSingleLineCaseStatements, true)
    .setPreference(DoubleIndentConstructorArguments, true)
    .setPreference(DanglingCloseParenthesis, Preserve)

Seq(preferences)
