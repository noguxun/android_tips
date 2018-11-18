package x.co.tips

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // add button click action by code
        val btn : Button = findViewById(R.id.button_fragment_test)

        btn.setOnClickListener { view: View ->
            // start up a new activity
            Log.i("MainActivity", "Action Listener called")
            buttonFragmentTest(view)
            //val intent = Intent(requireContext(), Gu1Activity::class.java)
            //requireContext().startActivity(intent)
        }
    }

    // the action is linked to button in the layout XML
    fun buttonGridViewTest(view: View) {
        Log.i("MainActivity", "button grid view test clicked")
    }

    fun buttonFragmentTest(view: View)
    {
        Log.i("MainActivity", "button fragment test clicked")
        val intent = Intent(this, FragActivity1::class.java)
        startActivity(intent)
    }


}
