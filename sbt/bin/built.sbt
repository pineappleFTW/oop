name := "bankapp"

version := "1.0"

scalaVersion := "2.11.8"

offline := true
resolvers += "Local Maven Repository" at "file:///"+Path.userHome+ "/.ivy2/cache"

unmanagedJars in Compile += Attributed.blank(file(System.getenv("JAVA_HOME") + "/jre/lib/ext/jfxrt.jar"))

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)


libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "8.0.40-R8",
  "org.scalafx" %% "scalafxml-core-sfx8" % "0.2.2",
  "com.github.nscala-time" %% "nscala-time" % "2.12.0",
  "org.scalikejdbc" %% "scalikejdbc"       % "2.5.0",
  "com.h2database"  %  "h2"                % "1.4.193",
  "ch.qos.logback"  %  "logback-classic"   % "1.1.7",
  "org.apache.derby" % "derby" % "10.13.1.1"

)

mainClass in assembly := Some("ch.makery.address.MainApp")

EclipseKeys.executionEnvironment := Some(EclipseExecutionEnvironment.JavaSE18)

fork := true
