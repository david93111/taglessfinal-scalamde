package freestyletagless.algebra.user

import finaltagless.domain.User

import cats._
import cats.implicits._

import freestyle.tagless._

@tagless trait UserAlgebraFreeStyle {

  def findUser(id: Long): FS[User]
  def updateUser(u: User): FS[User]

}
