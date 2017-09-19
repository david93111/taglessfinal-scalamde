package finaltagless.service

import finaltagless.BaseTest
import finaltagless.interpreter.{ UserExternalInterpreter, UserFutureInterpreter }
import finaltagless.service.user.UserService
import org.scalatest.Failed
import cats.data._
import cats.implicits._
import finaltagless.infrastructure.MockServerProvider
import finaltagless.interpreter.user.{ UserDBInterpreter, UserExternalInterpreter, UserFutureInterpreter }

import scala.concurrent.Future
import scala.util.{ Failure, Success }

class UserServiceTest extends BaseTest {

  val dataBaseInterpreter = new UserDBInterpreter

  val futureInterpreter = new UserFutureInterpreter

  val externalServiceInterpreter = new UserExternalInterpreter

  "User Service and Algebra test" must {

    "No encontrar el usuario al usar FutureInterpreter" in {

      val user = Long.MaxValue

      val loyal: UserService[Future] = new UserService(futureInterpreter)

      whenReady(loyal.addPoints(user, 10))(_ shouldBe None)

    }

    "Encontrar el usuario al usar DBInterpreter" in {

      val userService = new UserService(dataBaseInterpreter)

      val result = userService.addPoints(1, 10)

      whenReady(result) {
        case None =>
          Failed("El usuario no fue encontrado o no pudo ser actualizado")
        case Some(user) =>
          user.id shouldEqual 1
          user.loyaltyPoints shouldEqual 20
      }

    }

    "No encontrar el usuario al usar DBInterpreter" in {

      val userService = new UserService(dataBaseInterpreter)

      val result = userService.addPoints(11, 10)

      whenReady(result)(_ shouldBe None)

    }

    "Adicionar puntos al usuario con ExternalServiceInterpreter" in {

      val userService = new UserService(externalServiceInterpreter)

      val result = userService.addPoints(1, 10)
      result match {
        case Success(Some(user)) =>
          user.loyaltyPoints shouldEqual 65
        case _ =>
          Failed("El usuario 11 no deberia existir")
      }

    }

    "No encontrar el usuario al usar ExternalServiceInterpreter" in {

      val userService = new UserService(externalServiceInterpreter)

      val result = userService.addPoints(11, 10)
      result match {
        case Success(user) =>
          user shouldBe None
        case _ =>
          Failed("El usuario 11 no deberia existir")
      }

    }

    "Fallar comunicacion con servicio al apagar servicio mock ExternalServiceInterpreter" in {

      MockServerProvider.shutDownServer()

      val userService = new UserService(externalServiceInterpreter)

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
