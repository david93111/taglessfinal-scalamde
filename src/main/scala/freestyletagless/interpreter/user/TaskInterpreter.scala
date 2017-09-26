package freestyletagless.interpreter.user

import finaltagless.domain.User
import finaltagless.exceptions.NotFoundException
import freestyletagless.algebra.user.UserAlgebraFreeStyle
import monix.eval.Task

trait TaskInterpreter {

  implicit val userTaskHandler = new UserAlgebraFreeStyle.Handler[Task] {

    override def findUser(id: Long): Task[User] = {
      Task.raiseError(new NotFoundException)
    }

    override def updateUser(u: User): Task[User] = {
      Task(u)
    }

  }

}
