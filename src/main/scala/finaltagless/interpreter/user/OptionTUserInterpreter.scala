package finaltagless.interpreter.user

import cats.data.OptionT
import cats.data._
import cats.implicits._
import finaltagless.adt.User
import finaltagless.algebra.AdvancedUserAlgebra
import finaltagless.infrastructure.BaseExecutionContext

import scala.concurrent.Future

class OptionTUserInterpreter extends AdvancedUserAlgebra[Future] with BaseExecutionContext {
  override def findUser(id: Long): OptionT[Future, User] =
    OptionT.none[Future, User]

  override def updateUser(u: User): OptionT[Future, User] =
    // OptionT.fromOption[Future].apply(Option(u))
    OptionT.some[Future, User](u)
}
