name := "ephemeral"

organization  := "com.tibidat"

version := "0.0.1"

scalaVersion := "2.10.3"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/"
)

libraryDependencies ++= {
  val akkaV = "2.1.4"
  val sprayV = "1.1.0"
  Seq(
    "io.spray"            %   "spray-can"     % sprayV,
    "io.spray"            %   "spray-routing" % sprayV,
    "io.spray"            %   "spray-testkit" % sprayV  % "test",
    "io.spray" 			  %%  "spray-json"    % "1.2.5",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
    "com.typesafe.slick"  %% "slick"          % "2.0.0",
    "org.slf4j"           % "slf4j-nop"       % "1.6.4",
    "org.specs2"          %%  "specs2"        % "2.2.3" % "test"
  )
}

seq(Revolver.settings: _*)