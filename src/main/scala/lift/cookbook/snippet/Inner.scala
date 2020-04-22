package lift.cookbook.snippet

import net.liftweb.util.Helpers._

object Inner {

  // div * --> only changed the content, not changed the div at all.
  def logged = { "div *" #> "Should only be visible when user is logged in" }

  def nonlogged = { "div *" #> "Should only be visible when user is not logged in" }

}
