package android.hardik.attendancemanager.dialogs

import android.hardik.attendancemanager.R
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DeleteSubjectConfirmationDialog(
        confirmationDialogListener: ConfirmationDialogListener
): ConfirmationDialogFragment(confirmationDialogListener){

    override val dialogTitle: String
        get() {
            val subjectTitle = requireArguments().getString(ARG_SUBJECT_TITLE)
            return getString(R.string.delete_subject, subjectTitle)
        }

    override val dialogMessage: String
        get() = getString(R.string.warning_subject_delete)

    override val dialogPositiveButtonText: String
        get() = getString(R.string.delete)

    companion object {
        private const val ARG_SUBJECT_TITLE = "subjectTitle"

        fun newInstance(
                subjectTitle: String,
                confirmationDialogListener: ConfirmationDialogListener
        ): DialogFragment {
            val args = Bundle()
            args.putString(ARG_SUBJECT_TITLE, subjectTitle)
            val fragment = DeleteSubjectConfirmationDialog(confirmationDialogListener)
            fragment.arguments = args
            return fragment
        }
    }

}