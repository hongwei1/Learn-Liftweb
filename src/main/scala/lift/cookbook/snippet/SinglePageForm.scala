package lift.cookbook.snippet

import net.liftweb.http._
import lift.cookbook.lib._
import java.util.regex.Pattern

import net.liftweb.util.{FieldError, FieldIdentifier}

import scala.xml.Text

class SinglePageForm extends LiftScreen {

  val emailRegex: Pattern = Pattern.compile("\\b[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\\b")
  valRegex(emailRegex, "Invalid e-mail")

  //just one line, will generate the following html. label, id, placeholder, value, type and name..
  //  <tr>
  //    <td>
  //      <label for="F726442209351LL3XJW">E-mail</label>
  //    </td>
  //    <td>
  //      <input id="F726442209351LL3XJW" placeholder="Enter your e-mail" value="" type="text" name="F726442209347OWOVCM">
  //      </td>
  //    </tr>
  val from = field("E-mail", "1@1", "placeholder" -> "Enter your e-mail", valRegex(emailRegex, "Invalid e-mail"), minNumOfWords(3, "The e-mail body should have at least three words"))

  //We can change the button name here>
  override def finishButton = <button>Save</button>

  val subject = field("Subject", "", "placeholder" -> "Enter the subject of your message", valMinLen(10, "Subject too short"), valMaxLen(140, "Subject too long"))
  val body = field("Message", "", "placeholder" -> "Enter your message", valMinLen(20, "Message too short"), valMaxLen(400, "Message too long"), minNumOfWords(3, "The e-mail body should have at least three words"))

  protected def finish() {
    //1st: send a notiec
    S.notice("form submitted")

    val a31 = from.toString()
    val a32: String = from

    //2rd: send the email
    //SendEmail.send_!(from, "to_email@example.com", subject, body)
  }

  //we can define our own rules to validate the input ...
  def minNumOfWords(num: => Int, msg: => String): String => List[FieldError] = 
    s => s match {
      case str if (str != null) && str.split(" ").size >= num => Nil
      case _ => List(FieldError(currentField.box openOr new FieldIdentifier {}, Text(msg)))
    }

}