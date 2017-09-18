package finaltagless.service

import finaltagless.BaseTest
import finaltagless.interpreter.{ UserDBInterpreter, UserExternalInterpreter, UserFutureInterpreter }
import finaltagless.service.user.UserServices
import org.scalatest.Failed
import cats.data._
import cats.implicits._
import finaltagless.infrastructure.MockServerProvider

import scala.concurrent.Future
import scala.util.{ Failure, Success }

class UserServicesTest extends BaseTest {

  val dataBaseInterpreter = new UserDBInterpreter

  val futureInterpreter = new UserFutureInterpreter

  val externalServiceInterpreter = new UserExternalInterpreter

  "Validar implementacion" must {

    "No encontrar el usuario al usar FutureInterpreter" in {

      val user = Long.MaxValue

      val loyal: UserServices[Future] = new UserServices(futureInterpreter)

      whenReady(loyal.addPoints(user, 10)) { result =>
        result shouldEqual Left("User not found")
      }

    }

    "Encontrar el usuario al usar DBInterpreter" in {

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

      val userService = new UserServices(dataBaseInterpreter)

      val result = userService.addPoints(11, 10)

      whenReady(result) {
        case Left(message) =>
          message shouldEqual "User not found"
        case Right(_) =>
          Failed("El usuario 11 no deberia existir")
      }

    }

    "Adicionar puntos al usuario con ExternalServiceInterpreter" in {

      val userService = new UserServices(externalServiceInterpreter)

      val result = userService.addPoints(1, 10)
      result match {
        case Success(either) =>
          either.right.get.loyaltyPoints shouldEqual 65
        case _ =>
          Failed("El usuario 11 no deberia existir")
      }

    }

    "No encontrar el usuario al usar ExternalServiceInterpreter" in {

      val userService = new UserServices(externalServiceInterpreter)

      val result = userService.addPoints(11, 10)
      result match {
        case Success(either) =>
          either shouldEqual Left("User not found")
        case _ =>
          Failed("El usuario 11 no deberia existir")
      }

    }

    "Fallar comuniacion con servicio al apagar servicio mock" in {

      MockServerProvider.shutDownServer()

      val userService = new UserServices(externalServiceInterpreter)

      val result = userService.addPoints(1, 10)
      result match {
        case Failure(e) =>
          e shouldBe classOf[Exception]
        case _ =>
          Failed("No deberia haber comuniacion con servicio")
      }

      MockServerProvider.startServer()

    }

  }

}
