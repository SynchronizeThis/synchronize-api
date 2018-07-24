package models

import java.time.Duration
import java.util.UUID

import play.api.libs.json._

import models.tag_module._
import models.file_module._
import models.user_module._

object song_module {
  implicit val songReads = Json.reads[Song]
  implicit val songWrites = Json.writes[Song]

  case class Song(
    id: UUID,
    title: String,
    label: String,
    tags: Option[List[Tag]],
    size: Double,
    duration: Duration,
    uploadedBy: User,
    file: File,
  )
}