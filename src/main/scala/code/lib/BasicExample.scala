package code
package lib

import model._

import net.liftweb._
import common._
import http._

/**
 * A simple example of a REST style interface
 * using the basic Lift tools
 */
object BasicExample {
  /*
   * Given a suffix and an item, make a LiftResponse
   */
  private def toResponse(suffix: String, item: Item): LiftResponse =
    suffix match {
      case "xml" => XmlResponse(item)
      case _ => JsonResponse(item)
    }

  /**
   * Find /simple/item/1234.json
   * Find /simple/item/1234.xml
   */
    // LiftRules.DispatchPF <---> PartialFunction[Req, () => Box[LiftResponse]];
    // Liftweb will do 
    // 1st : pattern match the `Req`
    // 2rd : run the resulting function: () => Box[LiftResponse], and get the Box[LiftResponse] 
    // 3rd : if Box is full, it will send back to browser.
    
  lazy val findItem: LiftRules.DispatchPF = {
    case Req(
      "simple" :: "item" :: itemId :: Nil, //  path  --->in.path.partPath
      suffix,    //suffix  --->  in.path.suffix
      GetRequest //Types: GET, POST, UPDATE --->in.requestType
    ) => 
      () => Item.find(itemId).map(toResponse(suffix, _))
  }

  /**
   * Find /simple2/item/1234.json
   */
  lazy val extractFindItem: LiftRules.DispatchPF = {
    // path with extractor
    case Req(
      "simple2" :: "item" :: Item(item) :: Nil,  //This is the `path` 
      suffix,            // the suffix value.
      GetRequest   // Request Type: Get, Post, 
    ) =>
      () => Full(toResponse(suffix, item)) 
  }
}
