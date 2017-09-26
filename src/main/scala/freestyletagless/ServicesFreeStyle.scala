package freestyletagless

import scala.concurrent.Future
import cats._
import cats.implicits._
import finaltagless.infrastructure.BaseExecutionContext
import freestyle.tagless._
import monix.cats._
import freestyletagless.interpreter.user.{ FutureInterpreter, TaskInterpreter, TryInterpreter }
import freestyletagless.service.user.UserServiceFreeStyle
import monix.eval.Task

import scala.util.Try

class ServicesFreeStyle extends BaseExecutionContext with FutureInterpreter with TaskInterpreter with TryInterpreter {

  def callFuture() = {
    UserServiceFreeStyle.addPoints[Future](1, 10)
  }

  def callTry() = {
    UserServiceFreeStyle.addPoints[Try](1, 10)
  }

  def callTask() = {
    import monix.execution.Scheduler.Implicits.global
    val task = UserServiceFreeStyle.addPoints[Task](1, 10)
    task.runAsync
  }

}
