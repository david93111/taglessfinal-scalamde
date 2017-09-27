package finaltagless.interpreter.user.optionT

import cats.data.OptionT
import cats.data._
import cats.implicits._
import finaltagless.domain.User
import finaltagless.algebra.AdvancedUserAlgebra
import finaltagless.infrastructure.BaseExecutionContext
import scala.util.Try

class OptionTtryInterpreter extends AdvancedUserAlgebra[Try] with BaseExecutionContext {
  override def findUser(id: Long): OptionT[Try, User] =
    OptionT.none[Try, User]

  override def updateUser(u: User): OptionT[Try, String] =
    OptionT.some[Try, String]("")
}