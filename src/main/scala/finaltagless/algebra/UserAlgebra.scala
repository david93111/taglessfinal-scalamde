package finaltagless.algebra

import java.util.UUID

import finaltagless.adt.User

abstract class UserAlgebra[F[_]] extends Algebra[F]{

  def findUser(id: UUID): F[Option[User]]
  def updateUser(u: User): F[Unit]

}
