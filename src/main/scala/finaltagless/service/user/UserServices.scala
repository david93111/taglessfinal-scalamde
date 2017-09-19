package finaltagless.service.user

import cats.{ Monad, ~> }
import cats.data.OptionT
import cats.implicits._
import finaltagless.adt.User
import finaltagless.algebra.UserAlgebra

class UserServices[F[_]: Monad](userAlgebra: UserAlgebra[F]) {
  def addPoints(userId: Long, pointsToAdd: Int): F[Option[User]] = {
    val result: OptionT[F, User] = for {
      userFound <- OptionT(userAlgebra.findUser(userId))
      update <- OptionT.liftF(userAlgebra.updateUser(copyUser(userFound, pointsToAdd)))
    } yield update
    result.value
  }

  private def copyUser(user: User, pointsToAdd: Int): User = {
    user.copy(loyaltyPoints = user.loyaltyPoints + pointsToAdd)
  }
}

