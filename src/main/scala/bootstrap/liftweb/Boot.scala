package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._

import code.lib._

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    // where to search snippet
    LiftRules.addToPackages("code")

    // Build SiteMap
    def sitemap() = SiteMap(
      Menu.i("Home") / "index" // the simple way to declare a menu
      )

    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMapFunc(sitemap)

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    
    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))    

    // the stateless REST handlers
    // Hooking up the handlers to Lift,
    // Holds user's DispatchPF functions that will be executed in a stateless context. This means that
    //   * no session will be created and no JSESSIONID cookie will be presented to the user (unless
    //   * the user has presented a JSESSIONID cookie).
    LiftRules.statelessDispatch.append(BasicExample.findItem)
    LiftRules.statelessDispatch.append(BasicExample.extractFindItem)

    // stateful versions of the same
    // LiftRules.dispatch.append(BasicExample.findItem)
    // LiftRules.dispatch.append(BasicExample.extractFindItem)

    LiftRules.statelessDispatch.append(BasicWithHelper)
    LiftRules.statelessDispatch.append(FullRest)

    // stateful versions of the above
    // LiftRules.dispatch.append(BasicWithHelper)
    // LiftRules.dispatch.append(FullRest)

  }
}
