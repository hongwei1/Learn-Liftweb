import RunWebApp.servletContextPath
import org.eclipse.jetty.server.handler.ContextHandler
import org.eclipse.jetty.server.{Handler, Server}
import org.eclipse.jetty.webapp.WebAppContext

object RunWebApp extends App {
  val servletContextPath = "/"
  val server = new Server(8080)

  val context = new WebAppContext()
  context.setServer(server)
  context.setContextPath(servletContextPath)
  // current project absolute path
  val basePath = this.getClass.getResource("/").toString .replaceFirst("target[/\\\\].*$", "")
  context.setWar(s"${basePath}src/main/webapp")

  server.setHandler(context)

  try {
    println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP")
    server.start()
    while (System.in.available() == 0) {
      Thread.sleep(5000)
    }
    server.stop()
    server.join()
  } catch {
    case exc : Exception => {
      exc.printStackTrace()
      sys.exit(100)
    }
  }
}
