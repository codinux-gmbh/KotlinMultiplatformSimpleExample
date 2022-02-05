package net.codinux.kotlin.mpp.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import net.codinux.kotlin.example.favicon.service.FaviconFinderService
import net.codinux.kotlin.mpp.android.adapter.FaviconsListRecyclerAdapter
import net.dankito.utils.android.GenericTextWatcher

class MainActivity : AppCompatActivity() {

  private val faviconsAdapter = FaviconsListRecyclerAdapter()

  // TODO: set the IP address where you hosted the running FaviconFinderService // TODO: host it on codinux space
  private val faviconsService = FaviconFinderService("192.168.250.75")

  private var previousFindFaviconsJob: Job? = null


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    initView()
  }

  private fun initView() {
    setContentView(R.layout.activity_main)

    val edtxtUrl = findViewById<EditText>(R.id.edtxtUrl)
    edtxtUrl.addTextChangedListener(GenericTextWatcher({ text, _, _, _ -> enteredUrlChanged(text.toString()) }))

    val rcyvwFoundFavicons = findViewById<RecyclerView>(R.id.rcyvwFoundFavicons)
    rcyvwFoundFavicons.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    rcyvwFoundFavicons.adapter = faviconsAdapter
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