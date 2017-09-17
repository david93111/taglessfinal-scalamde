package finaltagless.service

import finaltagless.interpreter.{ UserDBInterpreter, UserFutureInterpreter }
import finaltagless.service.user.UserServices
import cats.data._
import cats.implicits._
import finaltagless.infrastructure.BaseExecutionContext
import finaltagless.infrastructure.persistence.DataBaseProvider

import scala.concurrent.Future

object Services extends App with BaseExecutionContext {

  DataBaseProvider

  val user = Long.MaxValue

  val loyal: UserServices[Future] = new UserServices(new UserFutureInterpreter)

  def addPoints() = {
    loyal.addPoints(user, 10)
  }

  println(s"Este es el futuro -> ${addPoints()}")

  val userService = new UserServices(new UserDBInterpreter)

  val result = userService.addPoints(1, 10)

  println(s"Este es la llamada a BD -> $result")

}
