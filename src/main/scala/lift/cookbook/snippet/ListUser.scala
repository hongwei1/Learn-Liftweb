package lift.cookbook.snippet

import net.liftweb.util._
import net.liftweb.common._
import Helpers._

//Lift will create one logger per class or object. 
class ListUser extends Logger {

  def log(text: String) {
    text match {
      case str if str.length == 0 => error("user with no name")
      case str if str == "Forbidden" => warn("this user shouldn't have access")
      case str => debug("User name: " + str)

    }

  }

  def list = {

    val users = List("John", "Sarah", "Peter", "Sam", "", "Forbidden")
    info("listing users")
    //this will rendered the HTML and then to the browser
    "li .name *" #> users.map { //TODO 这个* 的具体含义是什么? 去掉会怎么样了? 
      user => {
        log(user)
        //        Text(user) //TODO, try to understand the difference between the Text(user) and user 
        user //this will add the string insdie the `li` tag!
      }
    }

  } match {
    case bind: CssBind =>
    case _ =>
  }

}