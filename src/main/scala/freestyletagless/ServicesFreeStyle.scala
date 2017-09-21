package freestyletagless

import scala.concurrent.Future
import cats._
import cats.implicits._
import finaltagless.adt.User
import finaltagless.exceptions.NotFoundException
import finaltagless.infrastructure.BaseExecutionContext
import freestyle.tagless._
import freestyletagless.algebra.user.UserAlgebraFreeStyle
import freestyletagless.service.user.UserServiceFreeStyle

class ServicesFreeStyle extends BaseExecutionContext {

  implicit val userHanlder = new UserAlgebraFreeStyle.Handler[Future] {

    override def findUser(id: Long): Future[User] = {
      Future.failed(new NotFoundException)
    }

    override def updateUser(u: User): Future[User] = {
      Future.successful(u)
    }

  }

  def callTest() = {
    UserServiceFreeStyle.addPoints[Future](1, 10)
  }

}
