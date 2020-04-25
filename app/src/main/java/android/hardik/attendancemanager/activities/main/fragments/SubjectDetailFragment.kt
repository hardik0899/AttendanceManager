package android.hardik.attendancemanager.activities.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.hardik.attendancemanager.R
import android.hardik.attendancemanager.activities.main.viewmodels.SubjectDetailViewModel
import android.hardik.attendancemanager.databinding.SubjectDetailFragmentBinding
import android.hardik.attendancemanager.utilities.InjectorUtils
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

class SubjectDetailFragment : Fragment() {

    companion object {
        private const val ARG_SUBJECT_TITLE = "subjectTitle"
        fun newInstance(subjectTitle: String): SubjectDetailFragment {
            val args = Bundle()
            args.putString(ARG_SUBJECT_TITLE, subjectTitle)
            val fragment = SubjectDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: SubjectDetailViewModel

    private lateinit var binding: SubjectDetailFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<SubjectDetailFragmentBinding>(
                inflater, R.layout.subject_detail_fragment, container, false
        )
        binding.attendedClassesText.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                TODO("Not yet implemented")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory =
                InjectorUtils.provideSubjectDetailViewModelFactory(
                        requireArguments().getString(ARG_SUBJECT_TITLE)!!,
                        requireActivity()
                )
        viewModel = ViewModelProvider(this, factory).get(SubjectDetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

}
