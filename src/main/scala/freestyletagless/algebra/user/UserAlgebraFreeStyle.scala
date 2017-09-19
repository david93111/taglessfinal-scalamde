package freestyletagless.algebra.user

import finaltagless.adt.User

import cats._
import cats.implicits._

import freestyle.tagless._

@tagless trait UserAlgebraFreeStyle {

  def findUser(id: Long): FS[Option[User]]
  def updateUser(u: User): FS[User]

}
