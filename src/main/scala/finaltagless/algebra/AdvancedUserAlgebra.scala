package finaltagless.algebra

import cats.data.OptionT
import finaltagless.adt.User

abstract class AdvancedUserAlgebra[M[_]] extends Algebra[M] {

  type optionTUser = OptionT[M, User]

  def findUser(id: Long): optionTUser
  def updateUser(u: User): optionTUser
}
