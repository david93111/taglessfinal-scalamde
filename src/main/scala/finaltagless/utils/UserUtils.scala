package finaltagless.utils

import finaltagless.adt.User

object UserUtils {

  def copyUser(user: User, pointsToAdd: Int): User = {
    user.copy(loyaltyPoints = user.loyaltyPoints + pointsToAdd)
  }

}
