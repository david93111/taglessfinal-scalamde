package finaltagless

import finaltagless.infrastructure.BaseExecutionContext
import finaltagless.infrastructure.persistence.DataBaseProvider
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{ Matchers, TestSuite, WordSpecLike }

trait BaseTest extends TestSuite with Matchers
  with ScalaFutures with WordSpecLike with BaseExecutionContext {

  DataBaseProvider

}
