package freestyletagless.service.user

import cats.Monad
import cats.data.OptionT
import finaltagless.utils.UserUtils
import freestyletagless.algebra.user.UserAlgebraFreeStyle

object UserServiceFreeStyle {
  import UserUtils._

  def addPoints[F[_]: Monad](userId: Long, pointsToAdd: Int)(implicit userAlgebra: UserAlgebraFreeStyle[F]) = {
    val result = for {
      userFound <- OptionT(userAlgebra.findUser(userId))
      update <- OptionT.liftF(userAlgebra.updateUser(copyUser(userFound, pointsToAdd)))
    } yield update
    result.value
  }

}
