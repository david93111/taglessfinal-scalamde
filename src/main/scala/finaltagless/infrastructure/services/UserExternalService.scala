package finaltagless.infrastructure.services

import finaltagless.adt.User
import finaltagless.config.AppConfig
import play.api.libs.json.Json

import scalaj.http.{ Http, HttpResponse }

trait UserExternalService extends Marshallers {

  val host: String = AppConfig.userServiceHost

  def lookForUser(id: Long): Option[User] = {
    val response: HttpResponse[String] = Http(s"$host/users/find").param("id", s"$id").asString
    println(s"service response -> ${response.body}")
    val json = Json.parse(response.body)
    val user = json.validate[User]
    user.asOpt
  }
}
