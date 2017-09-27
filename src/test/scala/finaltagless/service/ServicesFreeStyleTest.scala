package finaltagless.service

import finaltagless.BaseTest
import finaltagless.domain.User
import cats.data._
import cats.implicits._
import monix.cats._
import freestyletagless.interpreter.user.{ FutureInterpreter, TaskInterpreter, TryInterpreter }
import freestyletagless.service.user.UserServiceFreeStyle
import monix.eval.Task
import monix.execution.CancelableFuture
import org.scalatest.Failed

import scala.concurrent.Future
import scala.util.{ Success, Try }

class ServicesFreeStyleTest extends BaseTest with FutureInterpreter with TryInterpreter with TaskInterpreter {

  import UserServiceFreeStyle._

  "Freestyle algebra and service test" must {

    "Ejecutar el interprete Task" in {

      import monix.execution.Scheduler.Implicits.global

      val task: Task[User] = addPoints[Task](1, 10)
      val executedTask: CancelableFuture[User] = task.runAsync
      whenReady(executedTask.failed) { _ => assert(true) }

    }

    "Ejecutar el interprete Future" in {

      val future: Future[User] = addPoints[Future](1, 10)
      whenReady(future.failed) { _ => assert(true) }

    }

    "Ejecutar el interprete Try" in {

      val tryResult = addPoints[Try](1, 10)
      tryResult match {
        case Success(user) => Failed("Esto debia fallar! que paso?")
        case _             => assert(true)
      }

    }
  }

}
