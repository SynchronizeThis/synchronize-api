package controllers

import javax.inject.Inject
import javax.inject.Singleton

import scala.concurrent.{ExecutionContext, Future}

import play.api.mvc._
import play.api.mvc.MultipartFormData.FilePart

import models.file_module._
import models.file_module.file_type._

@Singleton
class SongController @Inject() (
	authenticatedAction: AuthenticatedAction,
	implicit val ec: ExecutionContext,
	fileManager: FileManager,
) extends InjectedController  {
	def createUploadSong = authenticatedAction.async(parse.multipartFormData) { request =>
		Future {
			request.body.file("file") match {
				case Some(FilePart(key, filename, contentType, ref)) => {
					fileManager.processFile(AUDIO, key, filename, contentType, ref) match {
						case Left(_) => InternalServerError("Can't process file.")
						case Right(_) => Created
					}
				}
				case _ => BadRequest("Can't retrieve file to upload.")
			}
		}
	}
}
