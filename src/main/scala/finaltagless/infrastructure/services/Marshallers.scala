package finaltagless.infrastructure.services

import finaltagless.adt.User
import play.api.libs.json.{ Json, Reads, Writes }

trait Marshallers {

  implicit val userWrites: Writes[User] = Json.writes[User]
  implicit val userReads: Reads[User] = Json.reads[User]

}
