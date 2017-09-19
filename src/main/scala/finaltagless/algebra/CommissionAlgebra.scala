package finaltagless.algebra

abstract class CommissionAlgebra[F[_]] extends Algebra[F] {

  def giveCommission(sourceUser: Long): F[Unit]

}
