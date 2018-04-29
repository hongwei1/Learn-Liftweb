import code.model.{BigDecimalSerializer, Item}
import net.liftweb.json.{Extraction, Formats, Xml}



val item = Item.randomItem
val items: Seq[Item] = Item.inventoryItems

<item>{Xml.toXml(item)}</item>


implicit val formats: Formats = net.liftweb.json.DefaultFormats + BigDecimalSerializer
Extraction.decompose(items)
