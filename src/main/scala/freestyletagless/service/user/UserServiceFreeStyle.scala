package freestyletagless.service.user

import cats.Monad
import cats.implicits._
import finaltagless.domain.User
import finaltagless.utils.UserUtils
import freestyletagless.algebra.user.UserAlgebraFreeStyle

object UserServiceFreeStyle {
  import UserUtils._

  def addPoints[F[_]: Monad](userId: Long, pointsToAdd: Int)(implicit userAlgebra: UserAlgebraFreeStyle[F]): F[User] = {
    // val find = userAlgebra.findUser(userId)
    for {
      userFound <- userAlgebra.findUser(userId)
      update <- userAlgebra.updateUser(copyUser(userFound, pointsToAdd))
    } yield update
  }

}
