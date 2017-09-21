package finaltagless.algebra

import finaltagless.adt.User

abstract class UserAlgebra[F[_]] extends Algebra[F] {

  def findUser(id: Long): F[User]
  def updateUser(u: User): F[User]

}
