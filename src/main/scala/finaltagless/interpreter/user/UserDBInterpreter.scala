package finaltagless.interpreter.user

import finaltagless.domain.User
import finaltagless.algebra.UserAlgebra
import finaltagless.infrastructure.BaseExecutionContext
import finaltagless.infrastructure.persistence.DataBaseProvider
import slick.jdbc.H2Profile.api._

import scala.concurrent.Future

class UserDBInterpreter extends UserAlgebra[Future] with BaseExecutionContext {

  import DataBaseProvider._
  import finaltagless.persistence.UsersTable._

  override def findUser(id: Long): Future[User] = {

    val query = for {
      result <- usersTable filter (_.id === id)
    } yield result

    db.run(query.result).map(future =>
      future.head
    )
  }

  override def updateUser(user: User): Future[User] = {
    val query = for {
      result <- usersTable filter (_.id === user.id) update user
    } yield result

    db.run(query).map(_ => user)
  }
}
