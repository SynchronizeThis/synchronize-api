package models

import java.io._
import java.nio.file._
import java.util.UUID
import javax.inject._

import play.api.libs.Files._
import play.api.libs.json._

import models.enums._

object file_module {
  object file_type {
    sealed trait FileType {
      def name = this.toString.toUpperCase
    }

    case object AUDIO extends FileType
    case object IMAGE extends FileType
    case object VIDEO extends FileType

    object FileType {
      val values = List(
        AUDIO,
        IMAGE,
        VIDEO,
      )
      def apply(s: String) = values.find(_.name == s.toUpperCase)
    }

    implicit val fileTypeEnum = new EnumAdt[FileType] {
      val values = FileType.values
      def valueAsString(x: FileType) = x.name
    }
    implicit val fileTypeReads  = jsonReads[FileType]
    implicit val fileTypeWrites = jsonWrites[FileType]

    sealed abstract trait FileTypeError extends FileError
    case object FileTypeNotSupported extends FileTypeError

    def contentTypeChecker(contentType: String): Either[FileError, (FileType, String)] = {
      "(image|(video)/.*)".r.findFirstMatchIn(contentType) match {
        case Some(contentType) => Right((AUDIO, contentType.toString))
        case None => Left(FileTypeNotSupported)
      }
    }
  }

  import file_type._

  implicit val fileReads = Json.reads[File]
  implicit val fileWrites = Json.writes[File]

  sealed abstract trait FileError
  case object FileWithoutContentType extends FileError

  case class File(
    id: UUID,
    fileType: FileType,
    contentType: String
  )

  class FileManager @Inject()(

  ) {
    def processFile(
      `mustBeFileType`: FileType,
      key: String,
      filename: String,
      contentType: Option[String],
      ref: TemporaryFile
    ): Either[FileError, Unit] = ???

    def checkContentType(contentType: Option[String]): Either[FileError, (FileType, String)] = {
      contentType match {
        case None => Left(FileWithoutContentType)
        case Some(contentType) => file_type.contentTypeChecker(contentType)
      }
    }
  }
}