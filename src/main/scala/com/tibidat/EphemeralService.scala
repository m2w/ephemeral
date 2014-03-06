package com.tibidat

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._
import scala.slick.driver.H2Driver.simple._

class EphemeralActor extends Actor with EphemeralService with DB {
  def actorRefFactory = context
  def receive = runRoute(routes)
}

trait EphemeralService extends HttpService { this: DB =>
  val index : Route = complete("index")
  val notes : Route = 
    path(JavaUUID) {id => // TODO: add catch all to throw 404 instead of 405 
		get {
			val n = m.getNote(id)
			// TODO: throw exception if n is None
			complete(s"get note $n")
		}
    }~
    post {
		entity(as[String]) { content =>
			val n : Note = m.addNote(content)
			complete(s"note created with id $n.id")
		}
    }
  val routes = path("") {index}~pathPrefix("notes") {notes}
}