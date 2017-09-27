package finaltagless.interpreter.user

import finaltagless.domain.User
import finaltagless.algebra.UserAlgebra
import finaltagless.config.AppConfig
import finaltagless.infrastructure.services.Marshallers
import play.api.libs.json.Json

import scala.util.Try
import scalaj.http.{ Http, HttpResponse }

class UserExternalInterpreter extends UserAlgebra[Try] with Marshallers {

  val host: String = AppConfig.userServiceHost

  override def findUser(id: Long): Try[User] = Try {
    val response: HttpResponse[String] = Http(s"$host/users/find").param("id", s"$id").asString
    val json = Json.parse(response.body)
    val user = json.validate[User]
    user.get
  }

  override def updateUser(u: User): Try[User] = {
    Try(u)
  }

}
