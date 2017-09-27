package finaltagless.interpreter.user

import finaltagless.exceptions.NotFoundException
import finaltagless.domain.User
import finaltagless.algebra.UserAlgebra
import monix.eval.{Coeval, Task}

class UserTaskInterpreter extends UserAlgebra[Task] {

  override def findUser(id: Long): Task[User] =
    Task.raiseError(new NotFoundException)

  override def updateUser(u: User): Task[User] =
    Task(u)
}




