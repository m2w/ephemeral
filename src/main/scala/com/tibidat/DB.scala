package com.tibidat

import scala.slick.jdbc.JdbcBackend.Database
import scala.slick.driver.H2Driver

class Model(name: String, dal: DAL, db: Database) {
  import dal._
  import dal.profile.simple._

  implicit val implicitSession = db.createSession

  def createDB = dal.create

  def dropDB = dal.drop
  
  def purgeDB = dal.purge

  def addNote(content: String): Note = {
    val note = Note(java.util.UUID.randomUUID(), content)
    val result = notes.insert(note)
    note
  }
  
  def getNote(id: java.util.UUID): Option[Note] = {
    // TODO add logic to ensure that each message only exists for 1 request
    notes.filter(_.id === id).firstOption
  }
}

trait DB {
	val m = new Model("H2", new DAL(H2Driver),
	    Database.forURL("jdbc:h2:mem:servicetestdb", driver = "org.h2.Driver"))
	  m.createDB
}