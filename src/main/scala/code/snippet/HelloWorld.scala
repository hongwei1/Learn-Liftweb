package code
package snippet

import java.util.Date

import code.lib._
import net.liftweb.common._
import net.liftweb.util.Helpers._

import scala.xml.NodeSeq


//If this class is called by Html file, so it is a Liftweb Snippet. 
//It will call the `render` method, NodeSeq => NodeSeq method.
class HelloWorld {
  lazy val date: Box[Date] = DependencyFactory.inject[Date] // inject the date
  val dateString: Box[String] = date.map(_.toString)

  //The render is a convention. 
  //lift:HelloWorld will call render automatically. 
  def render: (NodeSeq) => NodeSeq = {
    // Creates a `CSS Selector Transform` that inserts the String value of the injected Date into the markup, 
    // in this case the <span> that invoked the snippet.
    "* *" #> dateString
  }

  private val toString1: String = None.map(_.toString).toString

  //You can design your own method, and tell Html to call it :
  //eg: lift:HelloWorld.hongweiOwnRender
  def hongweiOwnRender: (NodeSeq) => NodeSeq = "* *" #> toString1
}

