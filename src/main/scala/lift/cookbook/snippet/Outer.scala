package lift.cookbook.snippet

import net.liftweb.util.Helpers._
import net.liftweb.http.S
import net.liftweb.common.{Box, Full}

object Outer {

  def choose = {
    // you can get the parameters from the S object.
//    S is an object that holds the HTTP requests/responses and HTTP session state.
    val loggedIn: Box[Boolean] = S.param("loggedin").flatMap(asBoolean)
    loggedIn match {
        //nesting lift function there.
//      case Full(b) if b => ".inner-div" #> "lift:Inner.logged"          --> <div> lift:Inner.logged </div>
      case Full(b) if b => ".inner-div [class+]" #> "lift:Inner.logged" //--><div class="inner-div lift:Inner.logged"></div>
      // --> <div class="inner-div">Should only be visible when user is logged in</div>
      //  How does Lift know that we want to append something to the class attribute of a tag? By using the [attr+] selector, 
      //  we tell Lift to append the result of the right-hand side of the #> operator to the tag attribute. Since we 
      //  used [class+], we told Lift to append lift:... to the class attribute.
      case _ => ".inner-div [class+]" #> "lift:Inner.nonlogged"
    }

  }

}
