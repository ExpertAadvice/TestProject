package com.roomdb.testproject.ui.fragments.main

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roomdb.testproject.domain.model.PassManager
import com.roomdb.testproject.domain.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentMainViewModel @Inject constructor(
    private val repository: RoomRepository
) : ViewModel() {

    private val _passList = MutableStateFlow<List<PassManager>?>(null)
    val passList = _passList.asStateFlow()

    private val _showToast = MutableSharedFlow<EventType>()
    val showToast = _showToast.asSharedFlow()

    fun addPass(passManager: PassManager) {
        viewModelScope.launch {
            try {
                repository.addPassword(passManager)
                _showToast.emit(EventType.Insert(true))
            } catch (e: SQLiteConstraintException) {
                _showToast.emit(EventType.Insert(false))
            }
        }
    }

    fun getPasswordList() {
        viewModelScope.launch {
            repository.getPasswordList().collectLatest {
                _passList.value = it
            }
        }
    }

    fun deletePassUsingKey(passManager: PassManager) {
        viewModelScope.launch {
            _showToast.emit(EventType.Delete(repository.deletePassByKey(passManager)))
        }
    }

    fun updatePass(passManager: PassManager) {
        viewModelScope.launch {
            _showToast.emit(EventType.Update(repository.updatePass(passManager)))
        }
    }

}

sealed interface EventType {
    data class Delete(val size: Int) : EventType
    data class Update(val size: Int) : EventType
    data class Insert(val bool: Boolean): EventType

}