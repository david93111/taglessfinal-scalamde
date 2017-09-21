package finaltagless.interpreter.commission

import finaltagless.algebra.CommissionAlgebra
import monix.eval.Task

class CommissionTaskInterpreter extends CommissionAlgebra[Task] {
  override def giveCommission(sourceUser: Long): Task[Unit] = {
    Task(println("Se ha otorgado la comision eventualmente"))
  }
}
