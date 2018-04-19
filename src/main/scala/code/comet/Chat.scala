package code.comet

import net.liftweb.http._
import net.liftweb.util._

class Chat extends CometActor with CometListener{
  //The actor can have its own message, the private state.
  private var msgs: Vector[String] = Vector() // private state

  /**
   * When the component is instantiated, register as
   * a listener with the ChatServer
   */
  override def registerWith = ChatServer //BK 0 register the Chat as a ChatServer listener. 

  /**
   * The CometActor is an Actor, so it processes messages.
   * In this case,
   * 1 we're listening for Vector[String],
   * 2 and when we get one, 
   * 3 update our private state
   * 4 and reRender() the component.  
   * 5 reRender() will cause changes to be sent to the browser.
   */
  override def lowPriority = {
    case  v: Vector[String] => //BK 4 Chat is an Actor, when it get the Vector[String] message, it will run the following things: 
      {
        msgs = v; 
        reRender()
      }
  }

  /**
   * 1 Put the messages in the li elements 
   * 2 and clear any elements that have the clearable class.
   */
  override def render = 
    "li *" #> msgs & ClearClearable 
  
  
}


