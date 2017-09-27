package finaltagless.interpreter.user

import finaltagless.algebra.UserAlgebra
import finaltagless.domain.User
import monix.eval.Coeval

class UserCoevalInterpreter extends UserAlgebra[Coeval] {

  override def findUser(id: Long): Coeval[User] =
    Coeval.evalOnce {
      User(1, "user1", "user@seven4n.com", 20)
    }

  override def updateUser(u: User): Coeval[User] =
    Coeval(u)
}