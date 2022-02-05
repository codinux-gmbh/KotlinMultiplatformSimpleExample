package net.codinux.kotlin.mpp.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.EditText
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import net.codinux.kotlin.example.favicon.service.FaviconFinderService
import net.codinux.kotlin.mpp.android.adapter.FaviconsListRecyclerAdapter
import net.dankito.utils.android.GenericTextWatcher

class MainActivity : AppCompatActivity() {

  // TODO: set the IP address where you hosted the running FaviconFinderService // TODO: host it on codinux space
  private val faviconsService = FaviconFinderService("192.168.250.75")

  private val faviconsAdapter = FaviconsListRecyclerAdapter()

  private var previousFindFaviconsJob: Job? = null


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    initView()
  }

  private fun initView() {
    setContentView(R.layout.activity_main)

    val edtxtUrl = findViewById<EditText>(R.id.edtxtUrl)
    edtxtUrl.addTextChangedListener(GenericTextWatcher({ text, _, _, _ -> enteredUrlChanged(text.toString()) }))

    findViewById<RecyclerView>(R.id.rcyvwFoundFavicons).apply {
      layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
      addItemDecoration(DividerItemDecoration(ContextThemeWrapper(this@MainActivity, R.style.AppTheme), (layoutManager as LinearLayoutManager).orientation))
      adapter = faviconsAdapter
    }
  }

  private fun enteredUrlChanged(url: String) {
    previousFindFaviconsJob?.cancel()

    previousFindFaviconsJob = GlobalScope.launch {
      val foundFavicons = faviconsService.extractFavicons(url)

      withContext(Dispatchers.Main) {
        faviconsAdapter.items = foundFavicons
      }
    }
  }

}