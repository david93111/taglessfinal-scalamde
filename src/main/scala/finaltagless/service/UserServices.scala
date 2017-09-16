package finaltagless.service

import java.util.UUID

import cats.Monad
import cats.implicits._
import finaltagless.algebra.UserAlgebra

class UserServices[F[_]: Monad](userAlgebra: UserAlgebra[F]) {
  def addPoints(userId: UUID, pointsToAdd: Int): F[Either[String, Unit]] = {
    userAlgebra.findUser(userId).flatMap {
      case None => implicitly[Monad[F]].pure(Left("User not found"))
      case Some(user) =>
        val updated = user.copy(loyaltyPoints = user.loyaltyPoints + pointsToAdd)
        userAlgebra.updateUser(updated).map(_ => Right(()))
    }
  }
}

