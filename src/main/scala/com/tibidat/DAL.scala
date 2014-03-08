package com.tibidat

import scala.slick.driver.JdbcProfile
import scala.slick.driver.H2Driver

trait Profile {
  val profile: JdbcProfile
}

class DAL(override val profile: JdbcProfile) extends NoteComponent with Profile {
  import profile.simple._
  
  val ddl = (notes.ddl)
  
  def create(implicit session: Session): Unit = {
    try {
      ddl.create
    } catch {
      case e: Exception => println("Could not create database.... assuming it already exists")
    }
  }
  
  def drop(implicit session: Session): Unit = {
    try {
      ddl.drop
    } catch {
      case e: Exception => println("Could not drop database")
    }
  }

  def purge(implicit session: Session) = { drop; create }
}
