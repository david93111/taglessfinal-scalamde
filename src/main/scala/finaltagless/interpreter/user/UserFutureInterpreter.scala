package finaltagless.interpreter.user

import finaltagless.adt.User
import finaltagless.algebra.UserAlgebra

import scala.concurrent.Future

class UserFutureInterpreter extends UserAlgebra[Future] {

  override def findUser(id: Long): Future[Option[User]] =
    Future.successful(None)

  override def updateUser(u: User): Future[User] =
    Future.successful(u)
}
