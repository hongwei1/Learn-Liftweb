package lift.cookbook.snippet

import lift.cookbook.lib.SendEmail
import net.liftweb.http.SHtml
import net.liftweb.util.Helpers._

import xml.Text
import net.liftweb.util.Props

class EmailSnippet {
  //
  def sendNow() = {
//    If you want this work
//    https://myaccount.google.com/lesssecureapps
    SendEmail.send_!(
      Props.get("mail.user").get,
      "zhw110@hotmail.com", 
      "Sending e-mail using GMail", 
      "Here is the body content."
    )
  }

  def sendEmail = {
    <span data-lift="EmailSnippet.sendEmail"><!-- there will be a button here --></span>
//    --> totally clean the <span tag, now we have the <a there
    <a  href="?F1116140292205WMK1DF=_#">Send e-mail over Liftweb!</a> //TODO, you can think about how to call backend, just from this line!
    "*" #> SHtml.link( // this will create the <a element in html, and when you click the link, it will call the `sendNow()` method
      "#", 
      () => sendNow(), //
      Text("Send e-mail over Liftweb!")
    )
  }

}