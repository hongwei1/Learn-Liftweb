package code
package snippet

import net.liftweb._
import net.liftweb.http._
import net.liftweb.util.Helpers._

/**
 * A snippet that lists the name of the current page
 */
object Embedded {
  def from = "*" #> s"This is the name of the path:::${S.location.map(_.name)}"
}

