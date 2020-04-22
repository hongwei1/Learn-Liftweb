package lift.cookbook.snippet

import net.liftweb.util.BindHelpers._
import net.liftweb.util.CssSel

//object Calculator {
class Calculator {
  //#id 按 id 取值..
  // return the CssSel type: 
  // Function1[NodeSeq, NodeSeq] --> get html --> using css --> return the transformed HTML 
  //gets the content of the div tag and changes it:
  
  // search for the div and replace the tag
  //<div data-lift="Calculator.plus"> 2 + 2 = <span id="result">some number</span></div>
  //-->
  //<div> 2 + 2 = <span id="result">4</span></div>
  
  
  
  def plus: CssSel = "#result *" #> (2 + 2)
  

}
