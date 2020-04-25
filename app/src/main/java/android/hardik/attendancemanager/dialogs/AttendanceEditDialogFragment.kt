package android.hardik.attendancemanager.dialogs

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.hardik.attendancemanager.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import timber.log.Timber

class AttendanceEditDialogFragment : DialogFragment() {
    private lateinit var attendedClassesEditText: EditText
    private lateinit var totalClassesEditText: EditText
    private lateinit var dialog: AlertDialog

    override fun onStart() {
        super.onStart()
        updatePositiveButton()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_edit_attendance, null)

        val builder = AlertDialog.Builder(activity)
                .setView(view)
                .setTitle("Edit Attendance ")
                .setPositiveButton("Edit") { _: DialogInterface?, _: Int -> sendResult(Activity.RESULT_OK) }
                .setNegativeButton("Cancel", null)
        dialog = builder.create()

        attendedClassesEditText = view.findViewById(R.id.attended_classes_edittext)
        attendedClassesEditText.setText(requireArguments().getInt(ARG_ATTENDED_CLASSES).toString())
        attendedClassesEditText.addTextChangedListener(object : TextWatcher {
            //TODO("when string is empty show original attendance and when user changes,
            // change it from starting")
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                updatePositiveButton()
            }

            override fun afterTextChanged(s: Editable) {}
        })

        totalClassesEditText = view.findViewById(R.id.total_classes_edittext)
        totalClassesEditText.setText(requireArguments().getInt(ARG_TOTAL_CLASSES).toString())
        totalClassesEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                Timber.i("before changed total classes $s")
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Timber.i("on text changed total $s start $start before $before count $count")
                updatePositiveButton()
            }

            override fun afterTextChanged(s: Editable) {
                Timber.i("after changed total classes $s")
            }
        })

        return dialog
    }

    private fun updatePositiveButton() {
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = validateInput()
    }

    private fun sendResult(resultCode: Int) {
        if (targetFragment == null) {
            return
        }
        val attendedClasses = Integer.valueOf(attendedClassesEditText.text.toString())
        val totalClasses = Integer.valueOf(totalClassesEditText.text.toString())
        val i = Intent()
        i.putExtra(EXTRA_ATTENDED_CLASSES, attendedClasses)
        i.putExtra(EXTRA_TOTAL_CLASSES, totalClasses)
        i.putExtra(EXTRA_SUBJECT_TITLE, requireArguments().getString(ARG_SUBJECT_TITLE))
        targetFragment!!.onActivityResult(targetRequestCode, resultCode, i)
    }

    private fun validateInput(): Boolean {
        if (attendedClassesEditText.text.isNotBlank() && totalClassesEditText.text.isNotBlank()) {
            val attendedClasses = attendedClassesEditText.text.toString().toInt()
            val totalClasses = totalClassesEditText.text.toString().toInt()
            return attendedClasses <= totalClasses
        }
        return false
    }

    companion object {
        private const val ARG_SUBJECT_TITLE = "subject_title"
        private const val ARG_ATTENDED_CLASSES = "com.android.mahendra.attendancemanager.subject.attendedClasses"
        private const val ARG_TOTAL_CLASSES = "com.android.mahendra.attendancemanager.subject.totalClasses"

        const val EXTRA_SUBJECT_TITLE = "subject_title"
        const val EXTRA_ATTENDED_CLASSES = "attended"
        const val EXTRA_TOTAL_CLASSES = "total"

        fun newInstance(subjectTitle: String?, attendedClasses: Int, totalClasses: Int): AttendanceEditDialogFragment {
            val args = Bundle()
            args.putInt(ARG_ATTENDED_CLASSES, attendedClasses)
            args.putInt(ARG_TOTAL_CLASSES, totalClasses)
            args.putString(ARG_SUBJECT_TITLE, subjectTitle)
            val fragment = AttendanceEditDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}