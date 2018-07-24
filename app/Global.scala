package global

import javax.inject.Inject
import javax.inject.Singleton

import play.api._

import com.google.inject.AbstractModule

@Singleton
class Global @Inject() (
) {
  Logger.info("Starting ADDON-API-MYSQL...")
}

class GlobalModule extends AbstractModule {
  def configure() = {
    bind(classOf[Global]).asEagerSingleton
  }
}
