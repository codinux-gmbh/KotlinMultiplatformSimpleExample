import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import net.codinux.kotlin.example.domain.dataaccess.BankFinderClient
import net.codinux.kotlin.example.domain.model.BankInfo
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.*

external interface MainFormProps : RProps {
  var client: BankFinderClient
}

data class MainFormState(val query: String, val foundBanks: List<BankInfo>, val lastFindBanksJob: Job?) : RState

@JsExport
class MainForm(props: MainFormProps) : RComponent<MainFormProps, MainFormState>(props) {

  init {
    state = MainFormState("", listOf(), null)
  }

  override fun RBuilder.render() {
    p {
      +"Suche Banken (nach Name, Ort oder BLZ)"
    }

    input {
      attrs {
        type = InputType.text
        value = state.query
        onChangeFunction = { event ->
          state.lastFindBanksJob?.cancel()

          val query = (event.target as HTMLInputElement).value

          val findBanksJob = GlobalScope.launch {
            val foundBanks = props.client.findBanks(query)

            setState(MainFormState(query, foundBanks, null))
          }

          setState(MainFormState(query, state.foundBanks, findBanksJob))
        }
      }
    }

    ol {
      state.foundBanks.forEach { bank ->
        li {
          div { +"${bank.bankCode} ${bank.name} ${bank.city}" }
        }
      }
    }
  }
}