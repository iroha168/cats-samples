import cats._
import cats.implicits._

import java.nio.charset.StandardCharsets
import java.security.MessageDigest

case class Person(name: String)

class Secret[A](val value: A) {
  private def hashed: String = {
    val s = value.toString
    val bytes = s.getBytes(StandardCharsets.UTF_8)
    val d = MessageDigest.getInstance("SHA-1")
    val hashBytes = d.digest(bytes)
    new String(hashBytes, StandardCharsets.UTF_8)
  }

  override def toString = hashed
}

object Secret {
  implicit val secretFunctor = new Functor[Secret] {
    override def map[A, B](fa: Secret[A])(f: A => B): Secret[B] =
      new Secret(f(fa.value))
  }
}

object FunctorDeomo extends App {
  val iroha = new Secret("iroha")
  println(iroha)
  println(iroha.value)
  println(Functor[Secret].map(iroha)(_.toUpperCase()))
  println(Functor[Secret].map(iroha)(_.toUpperCase()).value)

  val optionFunctor = new Functor[Option] {
    override def map[A, B](fa: Option[A])(f: A => B): Option[B] = {
      fa match {
        case Some(a) => Some(f(a))
        case None => None
      }
    }
  }

  val listFunctor = new Functor[List] {
    override def map[A, B](as: List[A])(f: A => B): List[B] = {
      def go(as: List[A]): List[B] = {
        as match {
          case Nil => Nil
          case h :: tail => f(h) :: go(tail)
        }
      }

      go(as)
    }
  }

  println(optionFunctor.map(Some(3))(_ + 1))
  println(listFunctor.map(List(1, 2, 3))(_ + 2))
  println(listFunctor.as(List(1, 2, 3),10))
  println(optionFunctor.as(Some(4), 11))
}
