package finaltagless.interpreter.commission

import finaltagless.algebra.CommissionAlgebra

import scala.concurrent.Future

class CommissionFutureInterpreter extends CommissionAlgebra[Future] {
  override def giveCommission(sourceUser: Long) = Future.successful {
    println("Se ha otorgado la comision futura")
  }
}
