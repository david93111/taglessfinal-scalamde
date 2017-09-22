package finaltagless.service

import finaltagless.BaseTest
import finaltagless.service.user.UserService
import org.scalatest.Failed
import cats.data._
import cats.implicits._
import monix.cats._
import finaltagless.adt.User
import finaltagless.infrastructure.MockServerProvider
import finaltagless.interpreter.user.{ UserDBInterpreter, UserExternalInterpreter, UserTaskInterpreter }

import scala.concurrent.Future
import scala.util.{ Failure, Success, Try }

class UserServiceTest extends BaseTest {

  val dataBaseInterpreter = new UserDBInterpreter

  val futureInterpreter = new UserTaskInterpreter

  val externalServiceInterpreter = new UserExternalInterpreter

  "User Service and Algebra test" must {

    "No encontrar el usuario al usar FutureInterpreter" in {
      import monix.execution.Scheduler.Implicits.global

      val user = Long.MaxValue

      val taskService = new UserService(futureInterpreter)
      whenReady(taskService.addPoints(user, 10).runAsync.failed) { _ => assert(true) }

    }

    "Encontrar el usuario al usar DBInterpreter" in {

      val userService = new UserService(dataBaseInterpreter)

      val result: Future[User] = userService.addPoints(1, 10)

      whenReady(result) { user =>
        user.id shouldEqual 1
        user.loyaltyPoints shouldEqual 20
      }

    }

    "No encontrar el usuario al usar DBInterpreter" in {

      val userService = new UserService(dataBaseInterpreter)

      val result: Future[User] = userService.addPoints(11, 10)

      whenReady(result.failed) { _ => assert(true) }

    }

    "Adicionar puntos al usuario con ExternalServiceInterpreter" in {

      val userService = new UserService(externalServiceInterpreter)

      val result: Try[User] = userService.addPoints(1, 10)
      result match {
        case Success(user) =>
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
          assert(true)
        case _ =>
          Failed("No deberia haber comuniacion con servicio")
      }

      MockServerProvider.startServer()

    }

  }

}
