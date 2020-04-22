package lift.cookbook.snippet

import net.liftweb.util.BindHelpers._
import net.liftweb.http.js.JsCmds.{JsCrVar, Script}
import net.liftweb.http.js.JE.{JsFunc, JsRaw}
import net.liftweb.http.js.JsCmds
import xml.Text

object JsCommand {

  def jsCommand = {
    //var fromServer = $("#cmd2").html("this string was sent from the server");
    val command1 = JsCrVar("fromServer", JsRaw("""$("#cmd2").html("this string was sent from the server")"""))
    
    //2rd: JsFunc also can set some parameters: JsFunc("someFunc", JsExp.strToJsExp("someValue") --> someFunc("someValue")
    val command2 = JsFunc("myFunction").cmd //-->myFunction();
    
    
    val command3 = JsCmds.SetHtml("cmd3", Text("changing element content using Lift's JsCmds"))
    //jQuery('#'+"cmd3").html("changing element content using Lift\u0027s JsCmds");


//    val command4 = JsCmds.Alert("Alert sent from Lift")
    val command4 = JsCmds.Run("")
    
    
    val command5 = confirm
    
    
    
    "*" #> Script(command1 & command2 & command3 & command4 &command5 )

  }

  def confirm = {

    val numbers = (1 to 10).toList
    val jsFunc =
      """ var numbers = [""" + numbers.mkString(",") + """]; for (i = 1; i <= """ + numbers.size +
        """; i++) {
      $("#cmd4").append('<button data-number="' + i + '">' +
      
      i + '</button>'); }
      
      $("#cmd4 button").click(function() { confirm('Do you really want to delete number: ' + $(this).data("number")); }); """.stripMargin

    JsRaw(jsFunc).cmd

  }

}