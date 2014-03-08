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
  def respond(n: Note) = respondWithMediaType(`application/json`) {
    complete(s"{'note': '${n.id}', 'contents': '${n.content}'}")
  }
  val notFound = respondWithMediaType(`application/json`) {
    respondWithStatus(404) {
    	complete("{'status': 404, 'message': 'No note not found'}")
    	}
      }
  val gone = respondWithMediaType(`application/json`) {
    respondWithStatus(410) {
    	complete("{'status': 410, 'message': 'Note no longer available'}")
    	}
      }
  val index : Route = compressResponse() {
    getFromResource("index.html")
  }
  val notes : Route = 
    path(JavaUUID) {id =>
		get {
			m.getNote(id) match {
			  case Some(n @ Note(_,_,None)) => respond(n)
			  case Some(_) => gone
			  case None => notFound
			}
		}
    }~
    get {
      notFound
    }~
    (post) {
		entity(as[String]) { content =>
			respond(m.addNote(content))
		}
    }
  val static : Route = pathPrefix("css") {
    compressResponse() {
    	getFromResourceDirectory("static/css")
    }
  }
  val routes = path("") {index}~pathPrefix("notes") {notes}~pathPrefix("static") {static}
}
