package finaltagless.interpreter.user.optionT

import cats.data.OptionT
import cats.implicits._
import finaltagless.algebra.AdvancedUserAlgebra
import finaltagless.domain.User
import finaltagless.infrastructure.BaseExecutionContext

import scala.concurrent.Future

class OptionTfutureInterpreter extends AdvancedUserAlgebra[Future] with BaseExecutionContext {
  override def findUser(id: Long): OptionT[Future, User] =
    OptionT.none[Future, User]

  override def updateUser(u: User): OptionT[Future, String] =
    OptionT.some[Future, String] {
      ""
    }
}
