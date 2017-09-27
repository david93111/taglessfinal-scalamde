package freestyletagless.interpreter.user

import finaltagless.domain.User
import finaltagless.exceptions.NotFoundException
import freestyletagless.algebra.user.UserAlgebraFreeStyle

import scala.util.Try

trait TryInterpreter {

  implicit val userTryHanlder = new UserAlgebraFreeStyle.Handler[Try] {

    override def findUser(id: Long): Try[User] = {
      Try { throw new NotFoundException }
    }

    override def updateUser(u: User): Try[User] = {
      Try(u)
    }

  }

}
