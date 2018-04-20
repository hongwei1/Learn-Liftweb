package code
package snippet

import net.liftweb.common._
import net.liftweb.sitemap.Menu.{ParamMenuable, WithSlash}
import net.liftweb.sitemap._
import net.liftweb.util.Helpers._

// capture the page parameter information
case class ParamInfo(theParam: String)

// a snippet that takes the page parameter information
class ShowParam(pi: ParamInfo)  {
  def render = "*" #> pi.theParam
}

object Param {
  // Create a menu for /param/somedata
  //  When the URL /param/dogfood or /param/fruitbat is presented, 
  // it matches the Loc and the function (s => Full(ParamInfo(s))) is invoked. 
  // If it returns a Full Box, the value is placed in the Loc’s currentValue.
  val menu: ParamMenuable[ParamInfo] = Menu.param[ParamInfo](  //3.2.7 Parameters
    "Param", 
    "Param",
    s => Full(ParamInfo(s)), // Loc’s currentValue.
    pi => pi.theParam
  )/ "param"
  
  lazy val loc = menu.toLoc

  def render = "*" #> loc.currentValue.map(_.theParam)
}
