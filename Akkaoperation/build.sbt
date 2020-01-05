name := "Akkaoperation"

version := "0.1"

scalaVersion := "2.12.7"

val akkaVersion="2.5.13"

lazy val root = (project in file("."))

libraryDependencies ++=Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "org.scalatest" %% "scalatest" % "3.0.5"
)


val ReleaseCommand = Command.command("release") {
  state =>
    "clean"  :: "compile" :: "test" :: state
}
commands += ReleaseCommand
