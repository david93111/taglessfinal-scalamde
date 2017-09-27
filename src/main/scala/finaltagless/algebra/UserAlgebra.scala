package finaltagless.algebra

import finaltagless.domain.User

trait UserAlgebra[F[_]] {

  def findUser(id: Long): F[User]
  def updateUser(u: User): F[User]

}
