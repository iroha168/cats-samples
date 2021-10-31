import cats._
import cats.implicits._

sealed trait Validated[+A]

case class Valid[+A](a: A) extends Validated[A]

case class Invalid(errors: List[String]) extends Validated[Nothing]

object Validated {

  implicit val applicative: Applicative[Validated] = new Applicative[Validated] {
    override def pure[A](x: A): Validated[A] = Valid(x)

    //    override def ap[A, B](vf: Validated[A => B])(va: Validated[A]): Validated[B] = {
    //      (vf, va) match {
    //        case (Valid(f), Valid(a)) => Valid(f(a))
    //        case (Invalid(e1), Valid(a)) => Invalid(e1)
    //        case (Valid(f),Invalid(e2)) => Invalid(e2)
    //        case (Invalid(e1),Invalid(e2)) => Invalid(e1 ++ e2)
    //      }
    //    }
    override def ap[A, B](vf: Validated[A => B])(va: Validated[A]): Validated[B] =
      map2(vf, va)((f, a) => f(a))

    //    override def map[A, B](va: Validated[A])(f: A => B): Validated[B] = ???

    override def map2[A, B, C](va: Validated[A], vb: Validated[B])(f: (A, B) => C): Validated[C] = {
      (va, vb) match {
        case (Valid(a), Valid(b)) => Valid(f(a, b))
        case (Invalid(a), Valid(_)) => Invalid(a)
        case (Valid(_), Invalid(b)) => Invalid(b)
        case (Invalid(a), Invalid(b)) => Invalid(a ++ b)
      }
    }

    //    override def map2[A, B, C](va: Validated[A], vb: Validated[B])(f: (A, B) => C): Validated[C] = {
    //      //     val g: A => B => C = a => b =>  f(a, b)
    //      val g: A => B => C = f.curried
    //      ap(ap(pure(g))(va))(vb)
    //    }

    def tupled[A, B](va: Validated[A], vb: Validated[B]): Validated[(A, B)] =
      map2(va, vb)((a, b) => (a, b))
  }

  case class Person(name: String, age: Int)

}

object ApplicativeDemo extends App {
  def validateName(name: String): Validated[String] =
    if (name.forall(_.isLetter)) Valid(name)
    else Invalid(List("name can only contains letters"))

  def validateAge(age: Int): Validated[Int] = {
    if (age < 18) Invalid(List("age must be at lease 18"))
    else Valid(age)
  }

  val v1 = Applicative[Validated].pure(1)
  val v2 = Applicative[Validated].pure(2)
  val v3 = Applicative[Validated].pure(3)
  println((v1, v2, v3).mapN((a, b, c) => a + b + c))
  println((v1, v2).mapN((a, b) => a + b))

  val optionApplicative: Applicative[Option] = new Applicative[Option] {
    override def pure[A](x: A): Option[A] = Some(x)

    override def ap[A, B](ff: Option[A => B])(fa: Option[A]): Option[B] = {
      (ff, fa) match {
        case (Some(f), Some(a)) => Some(f(a))
        case _ => None
      }
    }
  }
  println(optionApplicative.map2(Some(3), Some(4))(_ + _))
  println(optionApplicative.map2[Int, Int, Int](None, Some(4))(_ + _))

  val listApplicative: Applicative[List] = new Applicative[List] {
    override def pure[A](x: A): List[A] = List(x)

    override def ap[A, B](ff: List[A => B])(fa: List[A]): List[B] =
      ff match {
        case f :: fs => fa.fmap(f) ++ ap(fs)(fa)
        case _ => Nil
      }
  }
  println(listApplicative.map2(List(1, 2, 3), List(4, 5))(_ + _))
  println(listApplicative.map2[Int, Int, Int](List(), List(4, 5))(_ + _))
}

