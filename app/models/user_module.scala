package models

import java.util.UUID

import play.api.libs.json._

object user_module {
  implicit val userReads = Json.reads[User]
  implicit val userWrites = Json.writes[User]

  case class User(
    id: UUID
  )
}