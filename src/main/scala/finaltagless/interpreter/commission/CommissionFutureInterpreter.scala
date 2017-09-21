package finaltagless.interpreter.commission

import finaltagless.algebra.CommissionAlgebra
import monix.eval.Task

import scala.concurrent.Future

class CommissionFutureInterpreter extends CommissionAlgebra[Future] {
  override def giveCommission(sourceUser: Long): Future[Unit] = {
    Future.successful(println("Se ha otorgado la comision eventualmente"))
  }
}
