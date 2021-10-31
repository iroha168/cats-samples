import scala.concurrent.Future

trait Database[F[_]] {
  def find(id: Long): F[Account]
}

object LiveDatabase extends Database[Future] {
  def find(id: Long): Future[Account] = ???

}
//object LiveDatabase2 extends Database[Either[Throwable, _]] {
//  def find(id: Long): Either[Throwable, Account] = ???
//}


object HigherKindedDemo extends App {

}
