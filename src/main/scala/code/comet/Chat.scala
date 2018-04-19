package code.comet

import net.liftweb._
import http._
import net.liftweb.common.SimpleActor
import util._
import Helpers._
import net.liftweb.json.JsonAST.JObject

class Chat extends CometActor with CometListener{
  private var msgs: Vector[String] = Vector() // private state

  /**
   * When the component is instantiated, register as
   * a listener with the ChatServer
   */
  def registerWith = ChatServer

  /**
   * The CometActor is an Actor, so it processes messages.
   * In this case, we're listening for Vector[String],
   * and when we get one, update our private state
   * and reRender() the component.  reRender() will
   * cause changes to be sent to the browser.
   */
  override def lowPriority = {
    case  v: Vector[String] => msgs = v; reRender()
  }

  /**
   * Put the messages in the li elements and clear
   * any elements that have the clearable class.
   */
  def render = "li *" #> msgs & ClearClearable
}


