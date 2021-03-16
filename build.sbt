name := "UserManagement"

version := "0.1"

scalaVersion := "2.12.12"

lazy val userManagement = (project in file("UserManagement")).settings(
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.2.3" % Test,
    "org.mockito" %% "mockito-scala" % "1.5.12" % Test
  )
)

// Aggregate calculator module with root project
lazy val root = (project in file(".")).aggregate(userManagement)
