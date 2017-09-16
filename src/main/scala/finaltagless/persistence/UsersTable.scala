package finaltagless.persistence

import finaltagless.adt.User
import slick.jdbc.H2Profile.api._
import slick.lifted.ProvenShape

object UsersTable {

  class UsersModel(tag: Tag) extends Table[User](tag, "USERS") {
    def id = column[Long]("userid", O.SqlType("BIGINT"), O.PrimaryKey)
    def username = column[String]("username", O.SqlType("VARCHAR2(50)"))
    def email = column[String]("email", O.SqlType("VARCHAR2(50)"))
    def loyaltyPoints = column[Int]("points", O.SqlType("INT"))

    def * : ProvenShape[User] =
      (id, username, email, loyaltyPoints) <> (User.tupled, User.unapply)
  }
  val usersTable = TableQuery[UsersModel]

}