package finaltagless.algebra

import cats.data.OptionT
import finaltagless.adt.User

trait AdvancedUserAlgebra[M[_]] {

  type optionTUser = OptionT[M, User]

  def findUser(id: Long): optionTUser
  def updateUser(u: User): optionTUser
}
