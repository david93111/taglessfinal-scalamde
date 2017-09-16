package finaltagless

import java.util.UUID

import finaltagless.interpreter.FutureInterpreter
import finaltagless.service.UserServices

object Services extends App{

  val user = UUID.randomUUID()

  val loyal = new UserServices(new FutureInterpreter)
  loyal.addPoints(user, 10)

}
