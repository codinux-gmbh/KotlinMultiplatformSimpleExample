import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import net.codinux.kotlin.example.favicon.dataaccess.FaviconFinderClient
import net.codinux.kotlin.example.favicon.model.Favicon
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.Props
import react.State
import react.dom.*
import styled.styledDiv

external interface MainFormProps : Props {
  var client: FaviconFinderClient
}

data class MainFormState(val url: String, val foundFavicon: List<Favicon>, val lastFindFaviconsJob: Job?) : State

@JsExport
class MainForm(props: MainFormProps) : RComponent<MainFormProps, MainFormState>(props) {

  init {
    state = MainFormState("heise.de", listOf(), null)
  }

  override fun RBuilder.render() {
    p {
      +"Für welche URL sollen Favicons gesucht werden?"
    }

    input {
      attrs {
        type = InputType.text
        value = state.url
        onChangeFunction = { event ->
          state.lastFindFaviconsJob?.cancel()

          val url = (event.target as HTMLInputElement).value

          val findFaviconJob = GlobalScope.launch {
            val foundFavicons = props.client.extractFavicons(url)

            setState(MainFormState(url, foundFavicons, null))
          }

          setState(MainFormState(url, state.foundFavicon, findFaviconJob))
        }
      }
    }

    ul {
      state.foundFavicon.forEach { favicon ->
        li {
          styledDiv {
            span { +"${favicon.size} ${favicon.iconType}" }
            img(src = favicon.url) {
              favicon.size?.let { size ->
                attrs {
                  height = "${size.height}px"
                }
              }
            }
          }
        }
      }
    }
  }
}