package finaltagless.service.commission

import cats.Monad
import cats.implicits._
import finaltagless.domain.User
import finaltagless.algebra.{ CommissionAlgebra, UserAlgebra }
import finaltagless.utils.UserUtils

class CommissionWithUserService[F[_]: Monad](userAlgebra: UserAlgebra[F], commissionAlgebra: CommissionAlgebra[F]) {

  import UserUtils._

  def addPointWithCommission(userId: Long, pointsToAdd: Int): F[User] = {
    for {
      userFound <- userAlgebra.findUser(userId)
      _ <- commissionAlgebra.giveCommission(userFound.id)
      update <- userAlgebra.updateUser(copyUser(userFound, pointsToAdd))
    } yield update
  }
}
