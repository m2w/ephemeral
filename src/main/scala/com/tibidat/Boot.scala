package com.tibidat

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import scala.slick.driver.{JdbcProfile, H2Driver, SQLiteDriver}

object Boot extends App {
  
  implicit val system = ActorSystem("on-spray-can")

  val service = system.actorOf(Props[EphemeralActor], "ephemeral-service")

  IO(Http) ! Http.Bind(service, interface = "localhost", port = 8080)
}