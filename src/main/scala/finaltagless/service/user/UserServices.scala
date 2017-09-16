package finaltagless.service.user

import cats.Monad
import cats.implicits._
import finaltagless.adt.User
import finaltagless.algebra.UserAlgebra

class UserServices[F[_]: Monad](userAlgebra: UserAlgebra[F]) {
  def addPoints(userId: Long, pointsToAdd: Int): F[Either[String, User]] = {
    userAlgebra.findUser(userId).flatMap {
      case None => implicitly[Monad[F]].pure(Left("User not found"))
      case Some(user) =>
        val updated = user.copy(loyaltyPoints = user.loyaltyPoints + pointsToAdd)
        userAlgebra.updateUser(updated).map(Right(_))
    }
  }
}

