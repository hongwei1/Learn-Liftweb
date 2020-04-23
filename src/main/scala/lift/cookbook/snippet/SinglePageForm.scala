package lift.cookbook.snippet

import net.liftweb.http._
import lift.cookbook.lib._

class SinglePageForm extends LiftScreen {

  
  val from    = field("E-mail", "", "placeholder" -> "Enter your e-mail")
  //just one line, will generate the following html. label, id, placeholder, value, type and name..
//  <tr>
//    <td>
//      <label for="F726442209351LL3XJW">E-mail</label>
//    </td>
//    <td>
//      <input id="F726442209351LL3XJW" placeholder="Enter your e-mail" value="" type="text" name="F726442209347OWOVCM">
//      </td>
//    </tr>


  //We can change the button name here>
  override def finishButton = <button>Save</button>
  
  val subject = field("Subject", "", "placeholder" -> "Enter the subject of your message")
  val body    = field("Message", "", "placeholder" -> "Enter your message")

  protected def finish() {
    //1st: send a notiec
    S.notice("form submitted")

    val a31 = from.toString()
    val a32:String = from
    
    //2rd: send the email
    //SendEmail.send_!(from, "to_email@example.com", subject, body)
  }

}