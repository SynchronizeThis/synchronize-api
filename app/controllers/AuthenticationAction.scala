package controllers

import javax.inject._
import java.time.ZonedDateTime
import java.util.UUID

import scala.concurrent.Future
import scala.concurrent.ExecutionContext

import play.api._
import play.api.mvc._
import play.api.mvc.Results._

import models.AuthorizationChecker

@Singleton
class AuthenticatedAction @Inject()(
  authChecker: AuthorizationChecker,
  parser: BodyParsers.Default,
  env: Environment,
  implicit val ec: ExecutionContext
) extends ActionBuilderImpl(parser) {
  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    if (env.mode == Mode.Dev) {
      block(request)
    } else {
      (for {
        uuid <- request.headers.get("X-SYNCHRONIZE-UserId")
        timestamp <- request.headers.get("X-SYNCHRONIZE-Timestamp")
        signature <- request.headers.get("X-SYNCHRONIZE-Signature")
      } yield {
        val requestAuth = authChecker.check(
          request.method,
          request.uri,
          UUID.fromString(uuid),
          ZonedDateTime.parse(timestamp),
          signature
       )
        requestAuth.flatMap(res => {
          if (res.status == 200) {
            block(request)
          } else {
            Future(Unauthorized(res.body))
          }
        }).fallbackTo(Future(InternalServerError))
      }).getOrElse(Future(Unauthorized))
    }
  }
}