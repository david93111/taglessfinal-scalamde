package finaltagless.infrastructure

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.{ aResponse, configureFor, stubFor }
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig

object MockServerProvider {

  val wireMockServer = new WireMockServer(wireMockConfig().port(7171))
  configureFor("localhost", 7171)

  wireMockServer.start()

  stubFor(WireMock.post(WireMock.urlEqualTo("/users/find"))
    .willReturn(aResponse()
      .withStatus(200)
      .withHeader("Content-Type", "application/json")
      .withBody("")))

  def shutDownServer(): Unit = {
    wireMockServer.shutdown()
  }

}
