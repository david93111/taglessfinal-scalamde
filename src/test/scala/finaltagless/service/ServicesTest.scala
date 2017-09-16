package finaltagless.service

import finaltagless.BaseTest
import finaltagless.interpreter.UserFutureInterpreter
import cats.data._
import cats.implicits._
import finaltagless.service.user.UserServices

import scala.concurrent.Future

class ServicesTest extends BaseTest {

  "Validar implementacion" must {

    "No encontrar un usuario" in {

      val user = Long.MaxValue

      val loyal: UserServices[Future] = new UserServices(new UserFutureInterpreter)

      whenReady(loyal.addPoints(user, 10)) { result =>
        result shouldEqual Left("User not found")
      }

    }

  }

}
