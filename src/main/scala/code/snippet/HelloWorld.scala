package code
package snippet

import java.util.Date

import code.lib._
import net.liftweb._
import net.liftweb.common._
import net.liftweb.util.Helpers._

class HelloWorld {
  lazy val date: Box[Date] = DependencyFactory.inject[Date] // inject the date

  def render = "* *" #> date.map(_.toString)
}

