package finaltagless.service

import finaltagless.BaseTest
import finaltagless.interpreter._
import finaltagless.service.user.UserWithCommissionService
import cats.data._
import cats.implicits._
import org.scalatest.Failed

import scala.util.Success

class UserWithCommissionServiceTest extends BaseTest {

  val dataBaseInterpreter = new UserDBInterpreter

  val futureInterpreter = new UserFutureInterpreter

  val externalServiceInterpreter = new UserExternalInterpreter

  val commissionFutureInterpreter = new CommissionFutureInterpreter

  val commissionExternalInterpreter = new CommissionExternalInterpreter

  "User and Commission Algebra and Service Test" must {

    "No encontrar el usuario al usar futureInterpreter" in {
      val service = new UserWithCommissionService(futureInterpreter, commissionFutureInterpreter)
      whenReady(service.addPointWithCommission(1, 25))(_ shouldBe None)
    }

    "Entregar la comision usando BDinterpreter" in {
      val service = new UserWithCommissionService(dataBaseInterpreter, commissionFutureInterpreter)
      whenReady(service.addPointWithCommission(1, 25))(_.get.loyaltyPoints shouldEqual 45)
    }

    "Entregar la comision usando ExternalInterpreter" in {
      val service = new UserWithCommissionService(externalServiceInterpreter, commissionExternalInterpreter)
      service.addPointWithCommission(1, 25) match {
        case Success(Some(user)) =>
          user.loyaltyPoints shouldEqual 80
        case _ =>
          Failed("No se pudo otorgar los puntos")
      }
    }

  }

}
