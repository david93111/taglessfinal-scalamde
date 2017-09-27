package finaltagless.service.user

import cats.{ Monad, ~> }
import cats.data.OptionT
import cats.implicits._
import finaltagless.domain.User
import finaltagless.algebra.UserAlgebra
import finaltagless.utils.UserUtils

import scala.concurrent.Future

class UserProgram[F[_]: Monad](userAlgebra: UserAlgebra[F]) /*(implicit m: Monad[F])*/ {
  import UserUtils._

  def addPoints(userId: Long, pointsToAdd: Int): F[User] = {
    for {
      userFound <- userAlgebra.findUser(userId)
      update <- userAlgebra.updateUser(copyUser(userFound, pointsToAdd))
    } yield update
  }
}

