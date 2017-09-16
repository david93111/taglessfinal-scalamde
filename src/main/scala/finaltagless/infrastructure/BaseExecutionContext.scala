package finaltagless.infrastructure

import java.util.concurrent.ForkJoinPool

import scala.concurrent.ExecutionContext

trait BaseExecutionContext {

  implicit val execContext = ExecutionContext.fromExecutorService(new ForkJoinPool())

}
