package finaltagless

import finaltagless.infrastructure.{ BaseExecutionContext, MockServerProvider }
import finaltagless.infrastructure.persistence.DataBaseProvider
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{ BeforeAndAfterAll, Matchers, TestSuite, WordSpecLike }

trait BaseTest extends TestSuite with Matchers
  with ScalaFutures with WordSpecLike with BeforeAndAfterAll
  with BaseExecutionContext {

  DataBaseProvider

  override def beforeAll(): Unit = {
    super.beforeAll()
    MockServerProvider.startServer()
    DataBaseProvider.truncateData()
    DataBaseProvider.insertData()
  }

  override def afterAll(): Unit = {
    MockServerProvider.shutDownServer()
    super.afterAll()
  }

}
