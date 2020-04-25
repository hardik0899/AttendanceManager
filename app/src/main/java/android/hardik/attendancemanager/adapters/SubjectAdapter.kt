package android.hardik.attendancemanager.adapters

import android.hardik.attendancemanager.R
import android.hardik.attendancemanager.databinding.ListItemSubjectBinding
import android.hardik.attendancemanager.data.models.Subject
import android.hardik.attendancemanager.activities.main.viewmodels.SubjectsViewModel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class SubjectAdapter(
        private val subjectsViewModel: SubjectsViewModel
) : ListAdapter<Subject, SubjectAdapter.SubjectViewHolder>(SubjectDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        return SubjectViewHolder.from(parent, subjectsViewModel)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SubjectViewHolder private constructor(
            private val binding: ListItemSubjectBinding,
            private val subjectsViewModel: SubjectsViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(subject: Subject) {
            binding.subject = subject
            binding.executePendingBindings()
        }

        init {
            binding.subjectsViewModel = subjectsViewModel
        }

        companion object {
            fun from(
                    parent: ViewGroup,
                    subjectsViewModel: SubjectsViewModel
            ): SubjectViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = DataBindingUtil.inflate<ListItemSubjectBinding>(
                        inflater, R.layout.list_item_subject, parent, false)
                return SubjectViewHolder(binding, subjectsViewModel)
            }
        }
    }
}

class SubjectDiffCallback : DiffUtil.ItemCallback<Subject>() {
    override fun areItemsTheSame(oldItem: Subject, newItem: Subject): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Subject, newItem: Subject): Boolean {
        return oldItem == newItem
    }
}
