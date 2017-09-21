package finaltagless.service

import finaltagless.service.user.UserService
import cats.data._
import cats.implicits._
import finaltagless.infrastructure.{ BaseExecutionContext, MockServerProvider }
import finaltagless.infrastructure.persistence.DataBaseProvider
import finaltagless.interpreter.commission.{ CommissionExternalInterpreter, CommissionFutureInterpreter, CommissionTaskInterpreter }
import finaltagless.interpreter.user.{ OptionTUserInterpreter, UserDBInterpreter, UserExternalInterpreter, UserTaskInterpreter }
import finaltagless.service.commission.CommissionWithUserService
import freestyletagless.ServicesFreeStyle
import monix.cats._

object Services extends App with BaseExecutionContext {

  DataBaseProvider
  MockServerProvider.startServer()

  val user = Long.MaxValue

  val userTaskService = new UserService(new UserTaskInterpreter)
  val resultTask = userTaskService.addPoints(user, 10)
  println(s"Esta es una Task(User) -> $resultTask")

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

  val servicesFreeStyle = new ServicesFreeStyle
  val resultFreeStlye = servicesFreeStyle.callTest()
  println(s"Esta es una llamada a freeStyle -> $resultFreeStlye")

  val optionTinterpreter = new OptionTUserInterpreter
  val optRes = optionTinterpreter.findUser(1)
  println(s"Manejo de OptionInterpreter ${optRes.value}")

}
