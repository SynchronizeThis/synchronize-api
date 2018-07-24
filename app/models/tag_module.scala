package models

import java.util.UUID

import play.api.libs.json._

object tag_module {
  implicit val tagReads = Json.reads[Tag]
  implicit val tagWrites = Json.writes[Tag]

  case class Tag(
    id: UUID,
    label: String
  )
}