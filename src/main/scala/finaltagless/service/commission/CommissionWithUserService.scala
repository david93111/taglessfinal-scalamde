package finaltagless.service.commission

import cats.Monad
import cats.data.OptionT
import finaltagless.adt.User
import finaltagless.algebra.{CommissionAlgebra, UserAlgebra}
import finaltagless.utils.UserUtils

class CommissionWithUserService[F[_]: Monad](userAlgebra: UserAlgebra[F], commissionAlgebra: CommissionAlgebra[F]) {

  import UserUtils._

  def addPointWithCommission(userId: Long, pointsToAdd: Int): F[Option[User]] = {
    val result: OptionT[F, User] = for {
      userFound <- OptionT(userAlgebra.findUser(userId))
      _ <- OptionT.liftF(commissionAlgebra.giveCommission(userFound.id))
      update <- OptionT.liftF(userAlgebra.updateUser(copyUser(userFound, pointsToAdd)))
    } yield update
    result.value
  }

}
