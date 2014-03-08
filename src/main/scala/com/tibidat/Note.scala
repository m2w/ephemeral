package com.tibidat

import java.util.UUID


case class Note(id: UUID, content: String, purged: Option[Boolean] = None)

trait NoteComponent { this: Profile =>
  import profile.simple._ 

  class Notes(tag: Tag) extends Table[Note](tag, "NOTES") {
    def id = column[UUID]("ID", O.PrimaryKey)
    def content = column[String]("CONTENT", O.NotNull)
    def purged = column[Option[Boolean]]("PURGED")

    def * = (id, content, purged) <> (Note.tupled, Note.unapply)
  }

  val notes = TableQuery[Notes]
}
