package code.comet
//http://brianhsu.moe/blog/2013-04-12-LiftRestExample.html

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.OkResponse
import net.liftweb.json.JValue
import net.liftweb.util.Helpers.AsInt

object BlogAPI extends RestHelper {

  def getArticleJSON(postID: Int): Option[JValue] = {
    Article.getArticle(postID).map(_.toJSON)
  }

  def deleteArticle(postID: Int) = {
    Article.deleteArticle(postID)
    new OkResponse
  }

  def postArticle(jsonData: JValue): JValue = {
    Article.addArticle(
      title = (jsonData \ "title").extract[String],
      content = (jsonData \ "content").extract[String]
    ).toJSON
  }

  serve {
    case "api" :: "blog" :: AsInt(postID) :: Nil JsonGet req => getArticleJSON(postID)
    case "api" :: "blog" :: AsInt(postID) :: Nil JsonDelete req => deleteArticle(postID)
    case "api" :: "blog" :: Nil JsonPost ((jsonData, req)) => postArticle(jsonData)
  }
}

case class Article(id: Int, title: String, content: String) {
  def toJSON = {
    import net.liftweb.json._
    import net.liftweb.json.JsonDSL._

    ("id" -> id) ~ ("title" -> title) ~ ("content" -> content)
  }
}

object Article {

  var store: List[Article] = Article(12, "qqq", "sss") :: Nil

  def addArticle(title: String, content: String): Article = {
    val nextID = store.map(_.id).max + 1
    val newArticle = new Article(nextID, title, content)
    store ::= newArticle
    newArticle
  }

  def getArticle(id: Int): Option[Article] = store.filter(_.id == id).headOption
  def deleteArticle(id: Int) { store = store.filterNot(_.id == id) }
}