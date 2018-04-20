package code
package snippet

import net.liftweb._
import net.liftweb.http._
import net.liftweb.util.Helpers._

/**
 * A snippet that lists the name of the current page
 */
object Embedded {
  def from = "*" #> S.location.map(_.name)
}

