import kotlinx.browser.document
import kotlinx.browser.window
import net.codinux.kotlin.example.domain.dataaccess.BankFinderClient
import react.dom.render

fun main() {
    window.onload = {
        render(document.getElementById("root")) {
            child(MainForm::class) {
                attrs {
                    client = BankFinderClient()
                }
            }
        }
    }
}