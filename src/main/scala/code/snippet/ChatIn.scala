package code.snippet

import code.comet.ChatServer
import net.liftweb.http._
import net.liftweb.http.js.JsCmds._

import scala.xml.NodeSeq

object ChatIn {
  /**
   * The render method in this case returns a function
   * that transforms NodeSeq => NodeSeq.  In this case,
   * the function transforms a form input element by attaching
   * behavior to the input.  The behavior is to send a message
   * to the ChatServer and then returns JavaScript which
   * clears the input.
   */
  def render: (NodeSeq) => NodeSeq = 
    SHtml.onSubmit(
      s => {
        ChatServer ! s            //BK 2 send message to the Actor(ChatServer)
        SetValById("chat_in", "") //2 clear the input
      }
    )
}