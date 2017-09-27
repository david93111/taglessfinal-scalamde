package finaltagless.utils

import finaltagless.domain.User

object UserUtils {

  def copyUser(user: User, pointsToAdd: Int): User = {
    user.copy(loyaltyPoints = user.loyaltyPoints + pointsToAdd)
  }

}
