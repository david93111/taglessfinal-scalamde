package finaltagless.algebra

trait CommissionAlgebra[F[_]] {

  def giveCommission(sourceUser: Long): F[Unit]

}
