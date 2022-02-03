package net.codinux.kotlin.mpp.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.runBlocking
import net.codinux.kotlin.example.domain.service.BankFinderService

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    runBlocking {
      // TODO: set the IP address where you hosted the running BankFinderService // TODO: host it on codinux space
      BankFinderService("192.168.250.75").findAndLogBanks("sparkasse")
    }
  }
}