package finaltagless.service

import finaltagless.interpreter._
import finaltagless.service.user.UserService
import cats.data._
import cats.implicits._
import finaltagless.infrastructure.{BaseExecutionContext, MockServerProvider}
import finaltagless.infrastructure.persistence.DataBaseProvider
import finaltagless.interpreter.commission.{CommissionExternalInterpreter, CommissionFutureInterpreter}
import finaltagless.interpreter.user.{UserDBInterpreter, UserExternalInterpreter, UserFutureInterpreter}
import finaltagless.service.commission.CommissionWithUserService

import scala.concurrent.Future

object Services extends App with BaseExecutionContext {

  DataBaseProvider
  MockServerProvider.startServer()

  val user = Long.MaxValue

  val loyal: UserService[Future] = new UserService(new UserFutureInterpreter)
  println(s"Este es el futuro -> ${loyal.addPoints(user, 10)}")

  val userBDInterpreter = new UserDBInterpreter
  val userService = new UserService(userBDInterpreter)
  val result = userService.addPoints(1, 10)
  println(s"Este es la llamada a BD -> $result")

  val userTry = new UserExternalInterpreter
  val userServiceTry = new UserService(userTry)
  val resultTry = userServiceTry.addPoints(1, 10)
  println(s"Este es la llamada al servicio externo -> $resultTry")

  val commissionFuture = new CommissionFutureInterpreter
  val commissionService = new CommissionWithUserService(userBDInterpreter, commissionFuture)
  val resultCommissionFuture = commissionService.addPointWithCommission(1, 15)
  println(s"Este es la llamada al futuro commission-> $resultCommissionFuture")

  val commissionTry = new CommissionExternalInterpreter
  val commissionServiceTry = new CommissionWithUserService(userTry, commissionTry)
  val resultCommissionTry = commissionServiceTry.addPointWithCommission(1, 15)
  println(s"Este es la llamada al servicio externo commission -> $resultCommissionTry")

  MockServerProvider.shutDownServer()

}
