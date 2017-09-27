package finaltagless.service

import finaltagless.service.user.UserProgram
import cats.data._
import cats.implicits._
import finaltagless.domain.User
import finaltagless.infrastructure.{ BaseExecutionContext, MockServerProvider }
import finaltagless.infrastructure.persistence.DataBaseProvider
import finaltagless.interpreter.commission.{ CommissionExternalInterpreter, CommissionFutureInterpreter, CommissionTaskInterpreter }
import finaltagless.interpreter.user.{ UserDBInterpreter, UserExternalInterpreter, UserTaskInterpreter }
import finaltagless.service.commission.CommissionWithUserService
import freestyletagless.ServicesFreeStyle
import monix.cats._
import monix.eval.Task

import scala.concurrent.Future

object Services extends App with BaseExecutionContext {

  DataBaseProvider
  MockServerProvider.startServer()

  val user = Long.MaxValue

  val userTaskInterpreter: UserTaskInterpreter = new UserTaskInterpreter
  val userTaskService: UserProgram[Task] = new UserProgram(userTaskInterpreter)
  val resultTask: Task[User] = userTaskService.addPoints(1, 10)
  println(s"Esta es una Task(User) -> $resultTask")

  val userBDInterpreter: UserDBInterpreter = new UserDBInterpreter
  val userService: UserProgram[Future] = new UserProgram(userBDInterpreter)
  val result: Future[User] = userService.addPoints(1, 10)
  println(s"Este es la llamada a BD -> $result")

  val userTry = new UserExternalInterpreter
  val userServiceTry = new UserProgram(userTry)
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

  val servicesFreeStyle = new ServicesFreeStyle
  val resultFreeStlye = servicesFreeStyle.callFuture()
  println(s"Esta es una llamada a freeStyle -> $resultFreeStlye")

  MockServerProvider.shutDownServer()

}
