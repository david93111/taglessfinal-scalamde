package finaltagless.interpreter

import java.util.UUID

import finaltagless.adt.User
import finaltagless.algebra.UserAlgebra

import scala.concurrent.Future

class FutureInterpreter extends UserAlgebra[Future] {
  override def findUser(id: UUID): Future[Option[User]] =
    Future.successful(None)

  override def updateUser(u: User): Future[Unit] =
    Future.successful(())
}
