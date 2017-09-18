package finaltagless.service

import finaltagless.BaseTest
import finaltagless.interpreter.{ UserDBInterpreter, UserFutureInterpreter }
import finaltagless.service.user.UserServices
import org.scalatest.Failed

import cats.data._
import cats.implicits._

import scala.concurrent.Future

class UserServicesTest extends BaseTest {

  "Validar implementacion" must {

    "No encontrar el usuario al usar FutureInterpreter" in {

      val user = Long.MaxValue

      val loyal: UserServices[Future] = new UserServices(new UserFutureInterpreter)

      whenReady(loyal.addPoints(user, 10)) { result =>
        result shouldEqual Left("User not found")
      }

    }

    "Encontrar el usuario al usar DBInterpreter" in {

      val dataBaseInterpreter = new UserDBInterpreter

      val userService = new UserServices(dataBaseInterpreter)

      val result = userService.addPoints(1, 10)

      whenReady(result) {
        case Left(_) =>
          Failed("El usuario no fue encontrado o no pudo ser actualizado")
        case Right(user) =>
          user.id shouldEqual 1
          user.loyaltyPoints shouldEqual 20
      }

    }

    "No encontrar el usuario al usar DBInterpreter" in {

      val dataBaseInterpreter = new UserDBInterpreter

      val userService = new UserServices(dataBaseInterpreter)

      val result = userService.addPoints(11, 10)

      whenReady(result) {
        case Left(message) =>
          message shouldEqual "User not found"
        case Right(_) =>
          Failed("El usuario 11 no deberia existir")
      }

    }

  }

}
