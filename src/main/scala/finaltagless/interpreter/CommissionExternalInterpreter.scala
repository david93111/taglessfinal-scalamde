package finaltagless.interpreter

import finaltagless.algebra.CommissionAlgebra

import scala.util.Try

class CommissionExternalInterpreter extends CommissionAlgebra[Try] {
  override def giveCommission(sourceUser: Long) = Try {
    println("Comision externa otorgada!")
  }
}
