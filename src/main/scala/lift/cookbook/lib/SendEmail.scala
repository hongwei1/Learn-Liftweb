package lift.cookbook.lib

import net.liftweb.util.Mailer
import net.liftweb.util.Mailer._

object SendEmail {

  def send_!(from: String, recipient: String, subject: String, body: String) {
    val mailTypes = List(PlainMailBodyType(body), To(recipient))

    Mailer.msgSendImpl(From(from), Subject(subject), mailTypes)

  }

}