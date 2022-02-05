import kotlinx.coroutines.runBlocking
import net.codinux.kotlin.example.favicon.service.FaviconFinderService
import platform.posix.exit

fun main(args: Array<String>) {
  if (args.isEmpty()) {
    println("Bitte geben Sie eine URL ein, z. B.:\n./FaviconFinder.kexe \"heise.de\"")
    exit(0)
  }

  runBlocking {
    FaviconFinderService().extractAndLogFavicons(args.first())
  }
}