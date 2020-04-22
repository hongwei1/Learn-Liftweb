package lift.cookbook.snippet

import net.liftweb.util.BindHelpers._
import net.liftweb.util.CssSel
import net.liftweb.util.BindHelpers._

import xml.{Elem, Text}
//object Calculator {
object Animals {

  // this method accept the html and transfer and return the new html page.
  def listsca = {
    val animals = List(
      ("Dog", "(Canis lupus familiaris)"), 
      ("Cat", "(Felis catus)"), 
      ("Giraffe", "(Giraffa camelopardalis)"), 
      ("Lion", "(Panthera leo)"), 
      ("Horse", "(Equus ferus caballus)")
    )

    //scala support the div file directly.
    val html19: Elem =
      <div data-lift="Animals.list">
        <ul>
          <li>
            <span class="name"></span>-<span class="sname"></span>
          </li>
        </ul>
      </div>
    
    "li *" #> //li * 只选中一行,然后开始循环. #> 会把选中的 HTMl 按规则转化成新的 HTML
      animals.map {
        animal => // 每一行开始生成新的一行.
          ".name *" #> Text(animal._1) &  //if use *, just replace the content, it will keep the <Span
            ".sname " #> Text(animal._2)// if not * there, replace everything, it will replace all with string. 会寻找所有的.sname 类,然后都全部替代他们...
      }

  }

}
