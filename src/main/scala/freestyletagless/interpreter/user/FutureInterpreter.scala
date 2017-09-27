package freestyletagless.interpreter.user

import finaltagless.domain.User
import finaltagless.exceptions.NotFoundException
import freestyletagless.algebra.user.UserAlgebraFreeStyle

import scala.concurrent.Future

trait FutureInterpreter {

  implicit val userFutureHanlder: UserAlgebraFreeStyle.Handler[Future] {
    def updateUser(u: User): Future[User]

    def findUser(id: Long): Future[User]
  } = new UserAlgebraFreeStyle.Handler[Future] {

    override def findUser(id: Long): Future[User] = {
      Future.failed(new NotFoundException)
    }

    override def updateUser(u: User): Future[User] = {
      Future.successful(u)
    }

  }

}
