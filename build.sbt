import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import scalariform.formatter.preferences._

name := "taglesfinal-scalamde"

parallelExecution in test := false

version := "0.1.0"

scalaVersion := "2.12.2"

coverageEnabled := true

addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M9" cross CrossVersion.full)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "com.typesafe.slick" %% "slick" % "3.2.1",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1",
  "com.h2database" % "h2" % "1.4.196",
  "org.typelevel" %% "cats-core" % "0.9.0",
  "com.github.tomakehurst" % "wiremock" % "2.8.0",
  "org.scalaj" %% "scalaj-http" % "2.3.0",
  "com.typesafe.play" % "play-json_2.12" % "2.6.5",
  "io.frees" %% "freestyle-tagless" % "0.3.1",
  "io.monix" %% "monix-eval" % "2.3.0",
  "io.monix" %% "monix-cats" % "2.3.0"
)

val preferences =
  ScalariformKeys.preferences := ScalariformKeys.preferences.value
    .setPreference(AlignSingleLineCaseStatements, true)
    .setPreference(DoubleIndentConstructorArguments, true)
    .setPreference(DanglingCloseParenthesis, Preserve)

Seq(preferences)

