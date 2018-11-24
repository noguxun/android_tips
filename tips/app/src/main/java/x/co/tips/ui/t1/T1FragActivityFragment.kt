package x.co.tips.ui.t1

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import x.co.tips.R

class T1FragActivityFragment : Fragment() {

    companion object {
        fun newInstance() = T1FragActivityFragment()
    }

    private lateinit var fragActivityViewModel: T1FragActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.t1_frag_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragActivityViewModel = ViewModelProviders.of(this).get(T1FragActivityViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
