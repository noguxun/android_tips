package x.co.tips

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import x.co.tips.ui.fragactivity1.FragActivity1Fragment

class Frag1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frag1_activity_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FragActivity1Fragment.newInstance())
                .commitNow()
        }
    }

}
