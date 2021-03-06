package code.comet

import net.liftweb._
import http._
import actor._

object ChatServer extends LiftActor with ListenerManager {

  private var msgs = Vector("Welcome") // private state

  /**
   * When we update the listeners, what message do we send?
   * We send the msgs, which is an immutable data structure,
   * so it can be shared with lots of threads without any
   * danger or locking.
   */
  override def createUpdate = msgs // when call updateListeners(), will call this method

  /**
   * process messages that are sent to the Actor.  In
   * this case, we're looking for Strings that are sent
   * to the ChatServer.  We append them to our Vector of
   * messages, and then update all the listeners.
   */
  override def lowPriority = {
    case s: String => {
      msgs :+= s; 
      updateListeners()// BK 3 Server get the news and render to listeners 
    }
  }
}