package lift.cookbook.snippet

import net.liftweb.util.BindHelpers._
import net.liftweb.http.js.{JsCmd, JsCmds}
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js.JE.{JsFunc, JsRaw}
import net.liftweb.http.SHtml
import net.liftweb.json._

import scala.xml.Text


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
  
  
  
  //Ajax Example
  implicit val formats = net.liftweb.json.DefaultFormats

//  var persons: List[String] = "Carly" :: "Joe" :: "John" :: "Mary" :: Nil

  def loadData: JsCmd = {

    //Get the json from the browser, and remove it in the js
    def funcBody(json: JValue) = { 
      val p = json.extract[String]
//      persons = persons.filterNot(_ == p)

//      we can do complex here, database or business logic here. 
    //<select id="person">
    //  <option value="Carly">Carly</option>
    //  <option value="Joe">Joe</option>
    //  <option value="John">John</option>
    //  <option value="Mary">Mary</option>
    //</select>
      //("#person  [value='John']").remove() will delete the `<option value="John">John</option>` -->
      //      <select id="person">
      //        <option value="Carly">Carly</option>
      //        <option value="Joe">Joe</option>
      //        <option value="Mary">Mary</option>
      //      </select>
      
      JsRaw( 
        """ $("#person [value='""" + p + """']").remove() """).cmd & 
        JsCmds.Alert(p + " was removed")

    }
    
    //<script type="text/javascript">
    //// <![CDATA[
    //function ajaxDeletePeople(person) {
    //    liftAjax.lift_ajaxHandler('F7625200364944RMKZ3=' + encodeURIComponent(JSON.stringify(person)), null, null, null);
    //    }
    //
    //// ]]>
    //</script>
    
    Function(
      "ajaxDeletePeople", // "ajaxDeletePeople1" if set the different name here. You will see the error from the `Console`.
      List("person"),
      SHtml.jsonCall(
        JsRaw("person"),  //the value to be sent to the serve
        (value: JValue) => funcBody(value) //the function to call when the data is sent
      )
    )

  }

  def jsFunction = { "*" #> Script(loadData) }

}

