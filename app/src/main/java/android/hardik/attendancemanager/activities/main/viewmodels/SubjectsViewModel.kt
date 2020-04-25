package android.hardik.attendancemanager.activities.main.viewmodels

import android.hardik.attendancemanager.Event
import android.hardik.attendancemanager.data.models.Subject
import android.hardik.attendancemanager.data.source.repositories.SubjectRepository
import androidx.lifecycle.*

import kotlinx.coroutines.launch

import kotlin.collections.List

class SubjectsViewModel internal constructor(
    private val subjectRepository: SubjectRepository
) : ViewModel() {

    private val _optionSelectEvent = MutableLiveData<Event<String>>()
    val optionSelectEvent: LiveData<Event<String>>
         get() = _optionSelectEvent

    private val _subjectDeleteEvent = MutableLiveData<Event<String>>()
    val subjectDeleteEvent: LiveData<Event<String>>
        get() = _subjectDeleteEvent

    private val _resetAttendanceEvent = MutableLiveData<Event<String>>()
    val resetAttendanceEvent: LiveData<Event<String>>
        get() = _resetAttendanceEvent

    private val _newSubjectEvent = MutableLiveData<Event<Unit>>()
    val newSubjectEvent: LiveData<Event<Unit>>
        get() = _newSubjectEvent

    val allSubjects: LiveData<List<Subject>> = subjectRepository.allSubjects

    fun addNewSubject() {
        _newSubjectEvent.value = Event(Unit)
    }

    fun addNewSubject(subjectTitle: String) {
        insert(Subject(subjectTitle))
    }

    private fun insert(subject: Subject) = viewModelScope.launch {
        subjectRepository.insert(subject)
    }

    fun update(subject: Subject) = viewModelScope.launch {
        subjectRepository.update(subject)
    }

    fun delete(subjectTitle: String) = viewModelScope.launch {
        subjectRepository.delete(subjectTitle)
    }

    fun resetAttendance(subjectTitle: String) = viewModelScope.launch {
        subjectRepository.resetAttendance(subjectTitle)
    }

    fun onUpdateTitle(oldTitle: String, newTitle: String) = viewModelScope.launch {
        subjectRepository.updateTitle(oldTitle, newTitle)
    }

    val subjectTitles: LiveData<List<String>> = subjectRepository.allSubjectsTitles

    fun onOptionSelected(subject: Subject) {
        _optionSelectEvent.value = Event(subject.title)
    }

    fun onDeleteSubject(subjectTitle: String) {
        _subjectDeleteEvent.value = Event(subjectTitle)
    }

    fun onResetAttendance(subjectTitle: String) {
        _resetAttendanceEvent.value = Event(subjectTitle)
    }

    fun getSubject(subjectTitle: String): Subject? {
        return subjectRepository.getSubject(subjectTitle).value
    }

    fun onNewSubject(subjectTitle: String) = viewModelScope.launch {
        subjectRepository.insert(Subject(subjectTitle))
    }

    fun onAttended(subject: Subject) = viewModelScope.launch {
        subjectRepository.incrementAttendedClasses(subject.title)
    }

    fun onMissed(subject: Subject) = viewModelScope.launch {
        subjectRepository.incrementMissedClasses(subject.title)
    }

    fun onUpdateAttendance(subjectTitle: String, attendedClasses: Int, totalClasses: Int) = viewModelScope.launch {
        val missedClasses = totalClasses - attendedClasses
        subjectRepository.updateAttendance(subjectTitle, attendedClasses, missedClasses)
    }
}
