package finaltagless.service

import finaltagless.BaseTest
import cats.data._
import cats.implicits._
import monix.cats._
import finaltagless.adt.User
import finaltagless.interpreter.commission.{ CommissionExternalInterpreter, CommissionTaskInterpreter }
import finaltagless.interpreter.user.{ UserDBInterpreter, UserExternalInterpreter, UserTaskInterpreter }
import finaltagless.service.commission.CommissionWithUserService
import monix.eval.Task
import org.scalatest.Failed

import scala.util.Success

class CommissionWithUserServiceTest extends BaseTest {

  val dataBaseInterpreter = new UserDBInterpreter

  val taskUserInterpreter = new UserTaskInterpreter

  val externalServiceInterpreter = new UserExternalInterpreter

  val commissionTaskinterpreter = new CommissionTaskInterpreter

  val commissionExternalInterpreter = new CommissionExternalInterpreter

  "Commission and User Algebra and Service Test" must {

    "No encontrar el usuario al usar futureInterpreter" in {
      import monix.execution.Scheduler.Implicits.global

      val service = new CommissionWithUserService(taskUserInterpreter, commissionTaskinterpreter)
      val taskResult = service.addPointWithCommission(1, 25).runAsync
      whenReady(taskResult.failed) { case _ => assert(true) }
    }

    "Entregar la comision usando ExternalInterpreter" in {
      val service = new CommissionWithUserService(externalServiceInterpreter, commissionExternalInterpreter)
      service.addPointWithCommission(1, 25) match {
        case Success(user) =>
          user.loyaltyPoints shouldEqual 80
        case _ =>
          Failed("No se pudo otorgar los puntos")
      }
    }

  }

}
