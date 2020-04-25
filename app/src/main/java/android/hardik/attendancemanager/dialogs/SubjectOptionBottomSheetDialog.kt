package android.hardik.attendancemanager.dialogs

import android.content.Context
import android.hardik.attendancemanager.R
import android.hardik.attendancemanager.databinding.BottomSheetSubjectBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SubjectOptionBottomSheetDialog : BottomSheetDialogFragment() {
    private var clickListener: OptionClickListener? = null
    private lateinit var subjectTitle: String

    interface OptionClickListener {
        fun onDelete(subjectTitle: String)
        fun onEditTitle(subjectTitle: String)
        fun onEditAttendance(subjectTitle: String)
        fun onResetAttendance(subjectTitle: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        clickListener = try {
            targetFragment as OptionClickListener
        } catch (ex: ClassCastException) {
            throw ClassCastException(context.toString() +
                    "must implement SubjectOptionBottomSheetDialog.SubjectOptionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<BottomSheetSubjectBinding>(
                inflater, R.layout.bottom_sheet_subject, container, false
        )
        subjectTitle = requireArguments().getString(ARG_SUBJECT_TITLE, " ")
        binding.subjectTitle = subjectTitle

        with (binding) {
            deleteSubjectOption.setOptionClickListener(clickListener!!::onDelete)
            editAttendanceOption.setOptionClickListener(clickListener!!::onEditAttendance)
            resetAttendanceOption.setOptionClickListener(clickListener!!::onResetAttendance)
            editTitleOption.setOptionClickListener(clickListener!!::onEditTitle)
        }

        return binding.root
    }

    private fun View.setOptionClickListener(onClick: (subjectTitle: String) -> Unit) {
        setOnClickListener {
            onClick(this@SubjectOptionBottomSheetDialog.subjectTitle)
            this@SubjectOptionBottomSheetDialog.dismiss()
        }
    }

    companion object {
        private const val ARG_SUBJECT_TITLE = "subject title"
        fun newInstance(title: String): SubjectOptionBottomSheetDialog {
            val args = Bundle()
            args.putString(ARG_SUBJECT_TITLE, title)
            val fragment = SubjectOptionBottomSheetDialog()
            fragment.arguments = args
            return fragment
        }
    }
}