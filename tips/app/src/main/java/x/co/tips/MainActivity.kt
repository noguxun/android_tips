package x.co.tips

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // add button click action by code
        val btn : Button = findViewById(R.id.button_fragment_test)

        btn.setOnClickListener { view: View ->
            // start up a new activity
            Log.d("MainActivity", "Action Listener called")
            buttonFragmentTest(view)
            //val intent = Intent(requireContext(), Gu1Activity::class.java)
            //requireContext().startActivity(intent)
        }

        button_csv_test.setOnClickListener {
            Log.d("MainActivity", "button CSV test clicked")
            val intent = Intent(this, T4CsvActivity::class.java)
            startActivity(intent)
        }
    }

    // the action is linked to button in the layout XML
    fun buttonGridViewTest(view: View) {
        Log.d("MainActivity", "button grid view test clicked")
        val intent = Intent(this, T2GridViewActivity::class.java)
        startActivity(intent)
    }

    fun buttonFragmentTest(view: View)
    {
        Log.d("MainActivity", "button fragment test clicked")
        val intent = Intent(this, T1FragActivity::class.java)
        startActivity(intent)
    }

    fun buttonSearchTest(view: View)
    {
        Log.d("MainActivity", "button search test clicked")
        val intent = Intent(this, T3SearchViewActivity::class.java)
        startActivity(intent)
    }


}
