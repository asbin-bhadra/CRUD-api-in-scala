name := "UserManagement"

version := "0.1"

scalaVersion := "2.12.12"

lazy val userManagement = project in file("UserManagement")

// Aggregate calculator module with root project
lazy val root = (project in file(".")).aggregate(userManagement)
