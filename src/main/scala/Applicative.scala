import cats.{Applicative, Functor}
import cats.data.Nested
import cats.implicits._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
trait ApplicativeA[F[_]] extends Functor[F] {
  def product[A, B](fa: F[A], fb: F[B]): F[(A, B)]

  def pure[A](a: A): F[A]

  val x: Future[Option[Int]] = Future.successful(Some(5))
  val y: Future[Option[Char]] = Future.successful(Some('a'))
  val composed = Applicative[Future].compose[Option].map2(x, y)(_ + _)
  // composed: Future[Option[Int]] = Future(Success(Some(102)))

//  val nested = Applicative[Nested[Future, Option, *]].map2(Nested(x), Nested(y))(_ + _)
  // nested: Nested[Future, Option, Int] = Nested(Future(Success(Some(102))))

}
object ApplicativeMain extends App {
  def traverse[F[_]: Applicative, A, B](as: List[A])(f: A => F[B]): F[List[B]] =
    as.foldRight(Applicative[F].pure(List.empty[B])) { (a: A, acc: F[List[B]]) =>
      val fb: F[B] = f(a)
      Applicative[F].map2(fb, acc)(_ :: _)
    }
  val res1 = List(1, 2, 3).traverse(i => Some(i): Option[Int])
  println(res1)
  val res2 = List(1, 2, 3).traverse(i => if(i % 2 == 0) Some(i) else None)
  println(res2)
  val res3 = traverse(List(1, 2, 3))(i => Some(i): Option[Int])
  println(res3)
}