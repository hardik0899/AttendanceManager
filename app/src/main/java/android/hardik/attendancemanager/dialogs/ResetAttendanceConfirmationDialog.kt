package android.hardik.attendancemanager.dialogs

import android.hardik.attendancemanager.R
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ResetAttendanceConfirmationDialog(
        confirmationDialogListener: ConfirmationDialogListener
): ConfirmationDialogFragment(confirmationDialogListener) {
    override val dialogTitle: String
        get() = getString(R.string.reset_attendance)

    override val dialogMessage: String
        get() {
            val subjectTitle = requireArguments().getString(ARG_SUBJECT_TITLE)
            return getString(R.string.warning_reset_attendance, subjectTitle)
        }

    override val dialogPositiveButtonText: String
        get() = getString(R.string.reset)

    companion object {
        private const val ARG_SUBJECT_TITLE = "subjectTitle"

        fun newInstance(
                subjectTitle: String,
                confirmationDialogListener: ConfirmationDialogListener
        ): DialogFragment {
            val args = Bundle()
            args.putString(ARG_SUBJECT_TITLE, subjectTitle)
            val fragment = ResetAttendanceConfirmationDialog(confirmationDialogListener)
            fragment.arguments = args
            return fragment
        }
    }
}