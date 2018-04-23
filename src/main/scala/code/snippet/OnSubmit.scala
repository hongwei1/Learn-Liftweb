package code
package snippet

import net.liftweb._
import http._
import net.liftweb.common.Full
import util.Helpers._

import scala.xml.NodeSeq

/**
 * A snippet that binds behavior, functions,
 * to HTML elements
 */
object OnSubmit {
  def render = {
    // define some variables to put our values into
    var name = ""
    var age = "0"

    val whence = S.referer openOr "/"

    // The process() 
    // method closes over the scope of the name and age variables and when that method is lifted to a function, 
    // it still closes over the variables... that means that when the function is applied, 
    // it refers to the same instances of the name and age variables as the other functions in this method.
    // process the form
    def process() {

      asInt(age) match {
        case Full(a) if a < 13 => S.error("Too young!")
        case Full(a) => {
          S.notice("Name: " + name)
          S.notice("Age: " + a)
          S.redirectTo(whence)
        }

        case _ => S.error("Age doesn't parse as a number")
      }

      //      // if the age is < 13, display an error
      //      if (age < 13) S.error("Too young!")
      //      else {
      //        // otherwise give the user feedback and
      //        // redirect to the home page
      //        S.notice("Name: "+name)
      //        S.notice("Age: "+age)
      //        S.redirectTo("/")
      //      }
          }

      // associate each of the form elements
      // with a function... behavior to perform when the
      // for element is submitted --------------------------> func: String => Any
        "name=name" #> SHtml.onSubmit(valueFromHtmlName => name = valueFromHtmlName) & // set the name , SHtml defines a suite of XHTML element generator methods. This will get name value from HTML and set it to var name.
        // set the age variable if we can convert to an Int
        "name=age" #> SHtml.onSubmit(valueFromHtmlAge => age = valueFromHtmlAge) & // here if valueFromHtmlAge is not Int, foreach will not execute. So age = 0.
        // when the form is submitted, process the variable
        "type=submit" #> SHtml.onSubmitUnit(process) //-------> func: () => Any
    }
}
