package finaltagless.service

import finaltagless.BaseTest
import finaltagless.service.user.UserProgram
import org.scalatest.Failed
import cats.data._
import cats.implicits._
import monix.cats._
import finaltagless.domain.User
import finaltagless.interpreter.user.{ UserDBInterpreter, UserExternalInterpreter, UserTaskInterpreter }
import monix.eval.Task

import scala.concurrent.Future
import scala.util.{ Failure, Success, Try }

class UserProgramTest extends BaseTest {

  val dataBaseInterpreter = new UserDBInterpreter

  val taskInterpreter = new UserTaskInterpreter

  val externalServiceInterpreter = new UserExternalInterpreter

  "User Service and Algebra test" must {

    "No encontrar el usuario al usar FutureInterpreter" in {
      import monix.execution.Scheduler.Implicits.global

      val taskService: UserProgram[Task] = new UserProgram(taskInterpreter)
      val task: Task[User] = taskService.addPoints(1, 10)

      whenReady(task.runAsync.failed) { _ => assert(true) }

    }

    "Encontrar el usuario al usar DBInterpreter" in {

      val userService = new UserProgram(dataBaseInterpreter)

      val result: Future[User] = userService.addPoints(1, 10)

      whenReady(result) { user =>
        user.id shouldEqual 1
        user.loyaltyPoints shouldEqual 20
      }

    }

    "No encontrar el usuario al usar DBInterpreter" in {

      val userService = new UserProgram(dataBaseInterpreter)

      val result: Future[User] = userService.addPoints(11, 10)

      whenReady(result.failed) { _ => assert(true) }

    }

    "Adicionar puntos al usuario con ExternalServiceInterpreter" in {

      val userService = new UserProgram(externalServiceInterpreter)

      val result: Try[User] = userService.addPoints(1, 10)
      result match {
        case Success(user) =>
          user.loyaltyPoints shouldEqual 65
        case _ =>
          Failed("El usuario 11 no deberia existir")
      }

    }

    "No encontrar el usuario al usar ExternalServiceInterpreter" in {

      val userService = new UserProgram(externalServiceInterpreter)

      val result: Try[User] = userService.addPoints(11, 10)
      result match {
        case Success(user) =>
          Failed("El usuario 11 no deberia existir")
        case _ =>
          assert(true)
      }

    }

  }

}
