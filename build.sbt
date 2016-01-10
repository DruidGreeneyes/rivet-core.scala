name := "Rivet.scala"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies += "org.apache.servicemix.bundles" % "org.apache.servicemix.bundles.hbase" % "1.1.2_1" withSources()

libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "1.5.2" withSources()
