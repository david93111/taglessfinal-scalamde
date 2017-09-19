package finaltagless.infrastructure

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.{ aResponse, configureFor, stubFor }
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig

object MockServerProvider {

  val wireMockServer = new WireMockServer(wireMockConfig().port(7171))
  configureFor("localhost", 7171)

  def startServer(): Unit = {

    wireMockServer.start()

    stubFor(WireMock.get(WireMock.urlPathMatching("/users/find")).atPriority(1)
      .withQueryParam("id", WireMock.equalTo("1"))
      .willReturn(aResponse()
        .withStatus(200)
        .withHeader("Content-Type", "application/json")
        .withBody("""{"id":1,"username":"user1","email":"hola@email.com","loyaltyPoints":55}""")))

    stubFor(WireMock.get(WireMock.urlPathMatching("/users/find")).atPriority(2)
      .willReturn(aResponse()
        .withStatus(404)
        .withHeader("Content-Type", "application/json")
        .withBody("""{"message":"User not found"}""")))

  }

  def startServerAll(): Unit = {
    startServer()

    stubFor(WireMock.post(WireMock.urlPathMatching("/commissions/give")).atPriority(1)
      .willReturn(aResponse()
        .withStatus(200)
        .withHeader("Content-Type", "application/json")
        .withBody("""{"value":"350","forUser":"99"}""")))

  }

  def shutDownServer(): Unit = {
    wireMockServer.shutdown()
  }

}
