package finaltagless.algebra

import cats.data.OptionT
import finaltagless.adt.User

trait AdvancedUserAlgebra[M[_]] {

  type optionTUser[T] = OptionT[M, T]

  def findUser(id: Long): optionTUser[User]
  def updateUser(u: User): optionTUser[String]
}

trait AdvancedUserAlgebra2[M[_[_], _]] {

  def findUser[F[_]](id: Long): M[F, _]

}

class test extends AdvancedUserAlgebra2[OptionT] {
  override def findUser[F[_]](id: Long): OptionT[F, User] = ???
}
