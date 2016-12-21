package io.josephtran.showstowatch.show_form

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.josephtran.showstowatch.R

class ShowFormFragment : Fragment() {

    companion object {
        fun newInstance() = ShowFormFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_show_form, container, false)
        return view
    }
}
