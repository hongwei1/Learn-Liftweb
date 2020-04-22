package lift.cookbook.snippet

import net.liftweb.util.BindHelpers._
import net.liftweb.http.S

class Localization {
  def dynamic = {
    "*" #> S.?("dynamic.text")
  }
}
