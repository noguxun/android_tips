package x.co.tips

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import x.co.tips.ui.t1.T1FragActivityFragment

class T1FragActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frag1_activity_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, T1FragActivityFragment.newInstance())
                .commitNow()
        }
    }
}
