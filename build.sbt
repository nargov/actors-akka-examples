name := "actors-akka"

version := "1.0"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.5",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.5",
  "org.scalatest" %% "scalatest" % "2.2.1",
  "com.typesafe.akka" %% "akka-slf4j" % "2.3.5" )