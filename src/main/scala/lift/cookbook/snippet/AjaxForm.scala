package lift.cookbook.snippet

import java.util.regex.Pattern

import net.liftweb._
import http._
import js.JsCmds
import util.BindHelpers._
import lift.cookbook.lib.SendEmail

class AjaxForm {

  def form = {
    var from = "hongwei"//if you set it to the value, it will be the default string.
    var subject = ""
    var message = ""
    val emailRegex: Pattern = Pattern.compile("\\b[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\\b")
    
    def process() = {
      if (!emailRegex.matcher(from).matches()) {

        S.warning("From is not a valid e-mail")
        JsCmds.Noop
      } else if (subject.trim.length < 10 || subject.trim.length > 140) {

        S.warning("Subject should have at least 10 chars and have no more than 140.")
        JsCmds.Noop
      } else if (message.trim.length < 20 || message.trim.length > 400) {

        S.warning("Message should have at least 20 chars and have no more than 400.")
        JsCmds.Noop
      } else {
        //Here is the tasks, when the user click the `submit` button.
        //database, email... all kinds of tasks you want to do here.
        //SendEmail.send_!(from, "to_email@example.com", subject, message)
        //revert the form fields to ""
        //      JsCmds.Alert("Message sent") & 
        JsCmds.SetValById("from", "") &
          JsCmds.SetValById("subject", "") &
          JsCmds.SetValById("message", "")
      }
    }

    //this mean, get the string value from it, and assign it to the from variable, that is all...
    val a: String => Unit = from = _  //TODO, what is this???
    
    
    
    //from the html page, find the following ids and prepare the data.  
    //1st: value: String               --> is the variable, will be shown in the HTML, 
    //2rd: func: String => Any         --> takes a string as an argument and can return anything. //TODO ,what is this for?                    
    //3rd: attrs: SHtml.this.ElemAttr* --> a list of attr-value pairs that will be applied to the element.
    
    // from = _ , -->
    //The string that is passed is the one containing the value entered into the form by the user. 
    //In other words, we've created a function 
    //that takes the value from the form and assigns it to the from variable.
    "#from" #>         SHtml.text(from,        from = _,    "id" -> "from") &  //This will generate the 
      "#subject" #>   (SHtml.text(subject,     subject = _, "id" -> "subject")) &
        "#message" #> (SHtml.textarea(message, message = _, "id" -> "message") 
          //create a hidden field bound to the process method.
//          <input type="hidden" name="F525418990340V1BAXG" value="true">.
//          lift will bind this `name(F525418990340V1BAXG)` to the process method.
//          So when the form is submitted, Lift knows what has to be executed.
          ++ SHtml.hidden(process) //TODO, not sure how this hidden work here. 
          )
  }


}