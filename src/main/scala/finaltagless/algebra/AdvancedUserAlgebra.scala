package finaltagless.algebra

import cats.data.OptionT
import finaltagless.adt.User
import finaltagless.infrastructure.BaseExecutionContext
import cats.data._
import cats.implicits._

import scala.concurrent.Future

trait AdvancedUserAlgebra[M[_]] {

  type optionTUser[T] = OptionT[M, T]

  def findUser(id: Long): optionTUser[User]
  def updateUser(u: User): optionTUser[String]
}

trait AdvanceAlgebra[M[F[_], _]]

trait AdvanceUserAlgebra[F[_]] extends AdvanceAlgebra[OptionT] {
  def findUser(id: Long): OptionT[F, User]
}

class AdvanceUserFutureInterpreter extends AdvanceUserAlgebra[Future] with BaseExecutionContext {
  override def findUser(id: Long): OptionT[Future, User] = {
    OptionT.none[Future, User]
  }
}
