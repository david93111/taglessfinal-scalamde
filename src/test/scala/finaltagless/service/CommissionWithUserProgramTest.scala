package finaltagless.service

import finaltagless.BaseTest
import cats.data._
import cats.implicits._
import finaltagless.domain.User
import monix.cats._
import finaltagless.interpreter.commission.{ CommissionExternalInterpreter, CommissionFutureInterpreter, CommissionTaskInterpreter }
import finaltagless.interpreter.user.{ UserDBInterpreter, UserExternalInterpreter, UserTaskInterpreter }
import finaltagless.service.commission.CommissionWithUserProgram
import monix.eval.Task
import monix.execution.CancelableFuture
import org.scalatest.Failed

import scala.concurrent.Future
import scala.util.Success

class CommissionWithUserProgramTest extends BaseTest {

  val dataBaseInterpreter = new UserDBInterpreter

  val taskUserInterpreter = new UserTaskInterpreter

  val externalServiceInterpreter = new UserExternalInterpreter

  val commissionFutureinterpreter = new CommissionFutureInterpreter

  val commissionTaskinterpreter = new CommissionTaskInterpreter

  val commissionExternalInterpreter = new CommissionExternalInterpreter

  "Commission and User Algebra and Service Test" must {

    "No encontrar el usuario al usar futureInterpreter" in {
      import monix.execution.Scheduler.Implicits.global

      val service: CommissionWithUserProgram[Task] = new CommissionWithUserProgram(taskUserInterpreter, commissionTaskinterpreter)
      val taskResult: CancelableFuture[User] = service.addPointWithCommission(1, 25).runAsync
      whenReady(taskResult.failed) { _ => assert(true) }
    }

    "Entregar la comision usando BDinterpreter" in {
      val service: CommissionWithUserProgram[Future] = new CommissionWithUserProgram(dataBaseInterpreter, commissionFutureinterpreter)
      whenReady(service.addPointWithCommission(1, 25))(_.loyaltyPoints shouldEqual 35)
    }

    "Entregar la comision usando ExternalInterpreter" in {
      val service = new CommissionWithUserProgram(externalServiceInterpreter, commissionExternalInterpreter)
      service.addPointWithCommission(1, 25) match {
        case Success(user) =>
          user.loyaltyPoints shouldEqual 80
        case _ =>
          Failed("No se pudo otorgar la comision")
      }
    }

  }

}
