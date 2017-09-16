package finaltagless.interpreter

import finaltagless.adt.User
import finaltagless.algebra.UserAlgebra
import finaltagless.infrastructure.persistence.DataBaseProvider
import slick.jdbc.H2Profile.api._

import scala.concurrent.Future

class UserDBInterpreter extends UserAlgebra[Future] {

  import DataBaseProvider._
  import finaltagless.persistence.UsersTable._

  override def findUser(id: Long): Future[Option[User]] = {

    val query = for {
      result <- usersTable filter (_.id == id)
    } yield result

    db.run(query.result).map(future =>
      future.headOption
    )
  }

  override def updateUser(u: User): Future[User] = {
    Future.successful(u)
  }
}
