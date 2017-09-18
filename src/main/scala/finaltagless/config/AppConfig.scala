package finaltagless.config

import com.typesafe.config.{ Config, ConfigFactory }

object AppConfig {

  val config: Config = ConfigFactory.load()

  val userServiceHost: String = config.getString("app.services.users.host")

}
