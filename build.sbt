import android.Keys._

android.Plugin.androidBuild

scalaVersion := "2.11.4"

buildToolsVersion := Option("21.1.2")

run <<= run in Android

platformTarget in Android := "android-21"

minSdkVersion := "android-7"

libraryDependencies ++= Seq(
  "com.mcxiaoke.volley" % "library" % "1.0.8",
  "org.scalaj" %% "scalaj-http" % "1.1.4"
)