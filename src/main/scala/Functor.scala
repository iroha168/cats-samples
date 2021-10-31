import cats.Functor
import cats.implicits._
import cats.data.Nested

object FunctorMain extends App {
  val listOption = List(Some(1), None, Some(2))
  // listOption: List[Option[Int]] = List(Some(1), None, Some(2))

  // Through Functor#compose
  val res1 = Functor[List].compose[Option].map(listOption)(_ + 1)
  // res1: List[Option[Int]] = List(Some(2), None, Some(3))
  println(res1)
  def needsFunctor[F[_]: Functor, A](fa: F[A]): F[Unit] = Functor[F].map(fa)(_ => ())

  def foo: List[Option[Unit]] = {
    val listOptionFunctor = Functor[List].compose[Option]
    type ListOption[A] = List[Option[A]]
    needsFunctor[ListOption, Int](listOption)(listOptionFunctor)
  }
  val nested: Nested[List, Option, Int] = Nested(listOption)
  // nested: Nested[List, Option, Int] = Nested(List(Some(1), None, Some(2)))

  val res2 = nested.map(_ + 1)
  // res2: Nested[List, Option, Int] = Nested(List(Some(2), None, Some(3)))
  println(res2)
}
