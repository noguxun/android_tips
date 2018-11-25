package x.co.tips

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.ScrollingMovementMethod
import kotlinx.android.synthetic.main.t4_activity_csv.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset


class T4CsvActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.t4_activity_csv)

        // https://stackoverflow.com/questions/1748977/making-textview-scrollable-on-android
        text_view_station_list.movementMethod = ScrollingMovementMethod()

        readStationData()
    }

    private fun readStationData() {
        val csvStreamReader = resources.openRawResource(R.raw.station20180330free)

        val bufferReader = BufferedReader(InputStreamReader(csvStreamReader, Charset.forName("UTF-8")))

        var stationText = ""

        var count = 0

        val maxCount = 100

        while (true) {
            var line: String? = bufferReader.readLine() ?: break

            val cols = line?.split(",")

            if (count < maxCount) {
                cols?.let {

                    val text =
                        "Coulmn 0 = '" + cols[0] + "', Column 1 = '" + cols[1] + "', Column 2: '" + cols[2] + "'" + "\n"
                    stationText += text
                    println(text)
                }
            } else {
                break
            }

            count += 1
        }

        text_view_station_list.text = stationText
    }
}
