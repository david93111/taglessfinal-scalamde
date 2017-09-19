package finaltagless.infrastructure.persistence

import finaltagless.adt.User
import slick.jdbc.H2Profile
import slick.jdbc.H2Profile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object DataBaseProvider {
  import finaltagless.persistence.UsersTable._

  def initDB = {
    val setup = DBIO.seq(
      usersTable.schema.create,
      usersTable ++= Seq(
        User(1, "user1", "", 10),
        User(2, "user2", "", 25),
        User(3, "user3", "", 15),
        User(4, "user4", "", 0)
      )
    )
    db.run(setup)
  }

  val db: H2Profile.backend.Database = Database.forConfig("app.h2")

  Await.result(initDB, Duration.Inf)

}
