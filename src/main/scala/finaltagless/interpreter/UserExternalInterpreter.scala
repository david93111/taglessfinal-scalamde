package finaltagless.interpreter

import finaltagless.adt.User
import finaltagless.algebra.UserAlgebra
import finaltagless.infrastructure.services.UserExternalService

import scala.util.Try

class UserExternalInterpreter extends UserAlgebra[Try] with UserExternalService {

  override def findUser(id: Long): Try[Option[User]] = Try {
    lookForUser(id)
  }

  override def updateUser(u: User): Try[User] = Try {
    u
  }

}
