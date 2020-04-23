package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._
import common._
import http._
import js.jquery.JQueryArtifacts
import sitemap._
import Loc.{FailMsg, _}
import mapper._
//import lift.cookbook.model._
import net.liftmodules.JQueryModule


/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {

  //This is the configurations for the google email server.
  def configureMailer() {
    
    import javax.mail.{PasswordAuthentication ,Authenticator}

    //1st: basic JAVA MAIL API to send emails.
    System.setProperty("mail.smtp.starttls.enable", "true")
    System.setProperty("mail.smtp.ssl.enable", "true")
    System.setProperty("mail.smtp.host", "smtp.gmail.com")
    System.setProperty("mail.smtp.auth", "true")

    //2rd: Mailer is a build-in Lift object, contains utilities to send emails.
    Mailer.authenticator =
      for {
        user <- Props.get("mail.user")
        pass <- Props.get("mail.password")
      } yield
        new Authenticator {
          override def getPasswordAuthentication = new PasswordAuthentication(user, pass)
        }
  }
  
  def boot {
    if (!DB.jndiJdbcConnAvailable_?) {
      val vendor =
        new StandardDBVendor(
          Props.get("db.driver") openOr "org.h2.Driver",
          Props.get("db.url") openOr "jdbc:h2:lift_proto.db;AUTO_SERVER=TRUE",
          Props.get("db.user"), 
          Props.get("db.password")
        )

      LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)

      DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)
    }

    // Use Lift's Mapper ORM to populate the database
    // you don't need to use Mapper to use Lift... use
    // any ORM you want
//    Schemifier.schemify(true, Schemifier.infoF _, User)

    // where to search snippet
    LiftRules.addToPackages("lift.cookbook")

    val canManage_? = If(
      () => {true}, //if this return true, it will run the default page!!
      () => RedirectResponse("/") //if it is false, it will run this page!!
    )
    val isAdmin_? = If(
      () => {
        //This  error will store the error messages into the S.errors and when frontend call `renderIdMsgs` it will show 
        //these errors to the webpage
//        S.error("isAdmin is error1");//TODO better know how this S.error work?? how can we show it in web page!!!
//        S.error("isAdmin is error2");
//        val string54: String = S ? ("Can't view now") ; //it seams just return a String value.
//        println(string54);//just for testing
//        println("isAdmin is not !!!");//just for testing
        S.param("admin").flatMap(asBoolean).openOr(false)
      }, 
      () => 
        RedirectWithState( //if it is false, it will run this way, to redirect !!
        "/", 
        MessageState("isAdmin is MessageState ->NoticeType.Warning" -> NoticeType.Error)//This also can be in Msg in frontend
      )
    )

    // write a function by myself, and how the message or ...
    val canShowSometimesPage_? = If(
      () => (millis / 1000L / 60L) % 2 == 0, // 传入一个方法,true or false changed 
      S ? ("Can't view now") // there is a implict method to convert string --> () => LiftwebResponse()
    )

    // Build SiteMap
    def sitemap = SiteMap(
      
      Menu( S ? "menu.home") / "index" ,//>> User.AddUserMenusAfter, // the simple way to declare a menu
      Menu.i("chapter3.2 Creating forms") / "Chapter3.2" , //if False, you do not have the access to the html page!!!
      Menu.i("chapter3.3 Creating an Ajax form") / "Chapter3.3" , //if False, you do not have the access to the html page!!!
//      Menu.i("Send Email") / "send",
//      Menu.i("Danamical Table") / "danamically",
//      Menu.i("Nesting Snippets") / "nesting",
//
//      Menu.i("List Contacts") / "contacts" / "list" ,
//      Menu.i("Create") / "contacts" / "create" >> canManage_?, //if `true` go ahead, if `false` go to other way, in the IF
//      Menu.i("Edit") / "contacts" / "edit" >> canManage_?, 
//      Menu.i("View") / "contacts" / "view" >> canManage_?,
//      Menu.i("Delete") / "contacts" / "delete" >> isAdmin_?,

      // more complex because this menu allows anything in the
      // /static path to be visible
      Menu(
        Loc("Static", 
          Link(
            List("static"), 
            true, 
            "/static/index"
          ),
          S ? "menu.static")
      ),
      //added these links to the website, you can get the accesses there.
//      http://localhost:8080/403
//      http://localhost:8080/404
//      http://localhost:8080/500
      
      Menu(Loc("403", "403" :: Nil, "403", Hidden)), 
      Menu(Loc("404", "404" :: Nil, "404", Hidden)), 
      Menu(Loc("500", "500" :: Nil, "500", Hidden))

//      Menu.i("Home") / "index" >> LocGroup("content"), 
//      Menu("Search") / "search" >> LocGroup("content"),
//      
//      Menu("Admin") / "admin"  >> LocGroup("content")
//        submenus(
//          Menu(Loc("List", List("list"), "List Contacts", isAdmin_?, LocGroup("admin"))),
//          Menu(Loc("Create", List("create"), "Create Contact", isAdmin_?, LocGroup("admin"))),
//          Menu(Loc("Edit", List("edit"), "Edit Contact", isAdmin_?, LocGroup("admin"))),
//          Menu(Loc("Delete", List("delete"), "Delete Contact", isAdmin_?, LocGroup("admin"))),
//          Menu(Loc("View", List("view"), "View Contact", isAdmin_?, LocGroup("admin")))
//        ),
//      
//      Menu("Contact Us") / "contact" >> LocGroup("footer"),
//      Menu("About Us") / "about" >> LocGroup("footer"),
//      // more complex because this menu allows anything in the // /static path to be visible 
//      Menu(
//        Loc(
//          "Static", Link(List("static"), true, "/static/index"),
//          "Static Content",
//          LocGroup("content")))
    )

//    def sitemapMutators = User.sitemapMutator

    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
//    LiftRules.setSiteMapFunc(() => sitemapMutators(sitemap))
    LiftRules.setSiteMapFunc(() => sitemap)

    //Init the jQuery module, see http://liftweb.net/jquery for more information.
    LiftRules.jsArtifacts = JQueryArtifacts
    JQueryModule.InitParam.JQuery=JQueryModule.JQuery172
    JQueryModule.init()

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    
    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // What is the function to test if a user is logged in?
//    LiftRules.loggedInTest = Full(() => User.loggedIn_?)

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

    // Make a transaction span the whole HTTP request
    S.addAround(DB.buildLoanWrapper)

    //configure the gmail during the boot.scala
    //We only need to call this once, it it a singleton object. 
    configureMailer()


    //tell liftweb to find the international languages
    LiftRules.resourceNames = "i18n/resources" :: LiftRules.resourceNames
    
    //simulated the error cases
    //    http://localhost:8080/error-500
    //    http://localhost:8080/error-403
    //    http://localhost:8080/dont-exist
    LiftRules.dispatch.append {
      case Req("error-500" :: Nil, _, _) => {
        () => {
          Full(InternalServerErrorResponse())
        }
      }

      case Req("error-403" :: Nil, _, _) => {
        () => {
          Full(ForbiddenResponse())
        }
      }
    }

    
    //redirect the webpages according to the pages
    LiftRules.responseTransformers.append { 
      case r if r.toResponse.code == 403 => 
        RedirectResponse("/403") 
      case r if r.toResponse.code == 404 => 
        RedirectResponse("/404") 
      case r if r.toResponse.code == 500  => 
        RedirectResponse("/500") 
      case r => r
    }
  }
}
