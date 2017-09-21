package finaltagless.algebra

import finaltagless.adt.User

trait UserAlgebra[F[_]] {

  def findUser(id: Long): F[User]
  def updateUser(u: User): F[User]

}
