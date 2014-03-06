package com.tibidat

import java.util.UUID


case class Note(id: UUID, content: String)

trait NoteComponent { this: Profile =>
  import profile.simple._ 

  class Notes(tag: Tag) extends Table[Note](tag, "NOTES") {
    def id = column[UUID]("ID", O.PrimaryKey)
    def content = column[String]("CONTENT", O.NotNull)

    def * = (id, content) <> (Note.tupled, Note.unapply)
  }

  val notes = TableQuery[Notes]
}