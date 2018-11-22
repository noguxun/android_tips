package x.co.tips.ui.fragactivity1

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import x.co.tips.R

class FragActivity1Fragment : Fragment() {

    companion object {
        fun newInstance() = FragActivity1Fragment()
    }

    private lateinit var viewModel: FragActivity1ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.frag1_activity_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FragActivity1ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
