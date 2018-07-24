package models

import javax.inject._
import java.time.ZonedDateTime
import java.util.UUID

import scala.concurrent.Future

import play.api.libs.json.Json
import play.api.libs.ws._

class AuthorizationChecker @Inject()(
  ws: WSClient
) {
  def check(
    method: String,
    url: String,
    uuid: UUID,
    timestamp: ZonedDateTime,
    signature: String
  ): Future[WSResponse] = {
    ws
      .url(s"url/check")
      .post(Json.obj(
        "method" -> method,
        "url" -> url,
        "userId" -> uuid.toString,
        "date" -> timestamp.toString,
        "signature" -> signature
      ))
  }
}