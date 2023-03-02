package com.sample.crosserror

import org.h2.tools.Server
import org.scalatest.TestSuite

import java.io.File
import java.sql.{Connection, DriverManager}
import scala.util.{Failure, Success, Try}

trait JdbcFixtures {
  this: TestSuite =>

  def withH2Connection(
      setUpScript: String = "",
      cleanUpScript: String = ""
  )(test: Connection => Unit): Unit = {
    val server =
      Server.createTcpServer(
        "-tcpAllowOthers",
        "-baseDir",
        new File("target/h2").getAbsolutePath,
        "-ifNotExists"
      )

    try {
      server.start()
      withJdbcConnection(
        s"jdbc:h2:tcp://localhost:${server.getPort}/database",
        setUpScript,
        cleanUpScript
      )(test)
    } finally {
      server.shutdown()
    }

  }

  def withJdbcConnection(
      url: String,
      setUpScript: String = "",
      cleanUpScript: String = ""
  )(test: Connection => Unit): Unit = {

    val con = DriverManager.getConnection(url)
    try {

      if (setUpScript.nonEmpty) {
        con.prepareStatement(setUpScript).execute()
      }

      Try {
        test(con)
      } match {
        case Failure(ex) =>
          if (cleanUpScript.nonEmpty)
            con.prepareStatement(cleanUpScript).execute()
          throw ex
        case Success(_) =>
          if (cleanUpScript.nonEmpty)
            con.prepareStatement(cleanUpScript).execute()
      }

    } finally {
      con.close()
    }
  }

}
