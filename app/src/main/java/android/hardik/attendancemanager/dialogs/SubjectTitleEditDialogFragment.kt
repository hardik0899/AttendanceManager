package android.hardik.attendancemanager.dialogs

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.hardik.attendancemanager.R
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_add_subject.view.*

class SubjectTitleEditDialogFragment : DialogFragment() {

    private lateinit var subjectTitleEditText: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_add_subject, null)

        subjectTitleEditText = view.subjectTitleEditText
        subjectTitleEditText.requestFocus()

        val subjectTitle = arguments?.getString(ARG_SUBJECT_TITLE)
        if (subjectTitle != null) {
            subjectTitleEditText.setText(subjectTitle)
            subjectTitleEditText.setSelection(0, subjectTitle.length)
        }

        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.add_subject)
        builder.setView(view)
        builder.setPositiveButton(R.string.add) { dialog, which -> sendResult(Activity.RESULT_OK, subjectTitleEditText.text.toString().trim()) }

        builder.setNegativeButton(R.string.cancel) { dialog, which -> dismiss() }
        val dialog = builder.create()
        dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        return dialog
    }

    override fun onResume() {
        super.onResume()
        subjectTitleEditText.requestFocus()
    }

    private fun sendResult(resultCode: Int, subjectTitle: String) {
        if (targetFragment == null) {
            return
        }
        val i = Intent()
        i.putExtra(EXTRA_OLD_SUBJECT_TITLE, arguments?.getString(ARG_SUBJECT_TITLE))
        i.putExtra(EXTRA_SUBJECT_TITLE, subjectTitle)
        targetFragment!!.onActivityResult(targetRequestCode, resultCode, i)
    }

    companion object {
        private const val ARG_SUBJECT_TITLE = "subject_title"
        const val EXTRA_OLD_SUBJECT_TITLE = "com.android.mahendra.attendancemanager.subject.old_title"
        const val EXTRA_SUBJECT_TITLE = "com.android.mahendra.attendancemanager.subject.title"

        fun newInstance(subjectTitle: String?): SubjectTitleEditDialogFragment {
            val args = Bundle()
            args.putString(ARG_SUBJECT_TITLE, subjectTitle)

            val fragment = SubjectTitleEditDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}