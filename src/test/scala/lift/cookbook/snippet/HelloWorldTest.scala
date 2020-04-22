package lift.cookbook
package snippet

import net.liftweb._
import http._
import net.liftweb.util._
import net.liftweb.common._
import Helpers._
import org.specs2.mutable.Specification


object HelloWorldTestSpecs extends Specification   {
  val session = new LiftSession("", randomString(20), Empty)

  "HelloWorld Snippet" should {
    "Put the time in the node" in {
      val hello = new HelloWorld
      Thread.sleep(1000) // make sure the time changes

      val str = hello.howdy(<span>Welcome to your Lift app at <span id="time">Time goes here</span></span>).toString

      str must startWith("<span>Welcome to")
    }
  }
}
