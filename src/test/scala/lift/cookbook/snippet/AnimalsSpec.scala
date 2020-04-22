package lift.cookbook.snippet

import org.specs2.mutable._

import scala.xml.Elem

class AnimalsSpec extends Specification {
  "Animals list" should {
    "contains 5 animals" in {
      val result: Elem = <ul>
        <li>
          <span class="name">Dog</span>
          <span class="sname">(Canis lupus familiaris)</span>
        </li> <li>
          <span class="name">Cat</span>
          <span class="sname">(Felis catus)</span>
        </li> <li>
          <span class="name">Giraffe</span>
          <span class="sname">(Giraffa camelopardalis)</span>
        </li> <li>
          <span class="name">Lion</span>
          <span class="sname">(Panthera leo)</span>
        </li> <li>
          <span class="name">Horse</span>
          <span class="sname">(Equus ferus caballus)</span>
        </li>
      </ul>

      val nodeSeq = Animals.list(<ul>
        <li>
          <span class="name"></span>
          <span class="sname"></span>
        </li>
      </ul>)

      nodeSeq.toString must ==/(result.toString())

    }

  }

}