package finaltagless.algebra

import cats.data.OptionT
import finaltagless.domain.User

trait AdvancedUserAlgebra[M[_]] {

  type optionTUser[T] = OptionT[M, T]

  def findUser(id: Long): optionTUser[User]
  def updateUser(u: User): optionTUser[String]
}

trait AdvanceAlgebra[M[F[_], _]]

trait AdvanceUserAlgebra[F[_]] extends AdvanceAlgebra[OptionT] {
  def findUser(id: Long): OptionT[F, User]
}
