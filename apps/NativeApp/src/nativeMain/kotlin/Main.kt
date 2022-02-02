import kotlinx.coroutines.runBlocking
import net.codinux.kotlin.example.domain.service.BankFinderService
import platform.posix.exit

fun main(args: Array<String>) {
  if (args.isEmpty()) {
    println("Bitte geben Sie einen Suchbegriff ein, z. B.:\n./BankFinder.kexe \"Sparkasse Berlin\"")
    exit(0)
  }

  runBlocking {
    BankFinderService().findAndLogBanks(args.first())
  }
}