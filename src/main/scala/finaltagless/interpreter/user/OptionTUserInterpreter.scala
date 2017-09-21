package finaltagless.interpreter.user

import cats.data.OptionT
import cats.data._
import cats.implicits._
import finaltagless.adt.User
import finaltagless.algebra.AdvancedUserAlgebra
import finaltagless.infrastructure.BaseExecutionContext

import scala.concurrent.Future
import scala.util.Try

class OptionTUserInterpreter extends AdvancedUserAlgebra[Future] with BaseExecutionContext {
  override def findUser(id: Long): OptionT[Future, User] =
    OptionT.none[Future, User]

  override def updateUser(u: User): OptionT[Future, String] =
    // OptionT.fromOption[Future].apply(Option(u))
    OptionT.some[Future, String]("")
}

class OptionTUserInterpreterTry extends AdvancedUserAlgebra[Try] with BaseExecutionContext {
  override def findUser(id: Long): OptionT[Try, User] =
    OptionT.none[Try, User]

  override def updateUser(u: User): OptionT[Try, String] =
    // OptionT.fromOption[Future].apply(Option(u))
    OptionT.some[Try, String]("")
}
