package finaltagless.service

import finaltagless.interpreter.UserFutureInterpreter
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

}
