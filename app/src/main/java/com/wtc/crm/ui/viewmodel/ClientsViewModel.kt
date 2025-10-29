package com.wtc.crm.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wtc.crm.data.Client
import com.wtc.crm.data.ClientRepository
import com.wtc.crm.data.QuickNote
import com.wtc.crm.data.QuickNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientsViewModel @Inject constructor(
    private val clientRepository: ClientRepository,
    private val quickNoteRepository: QuickNoteRepository
) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _selectedFilter = MutableStateFlow<FilterType>(FilterType.ALL)
    val selectedFilter: StateFlow<FilterType> = _selectedFilter.asStateFlow()
    
    private val _clients = MutableStateFlow<List<Client>>(emptyList())
    val clients: StateFlow<List<Client>> = _clients.asStateFlow()
    
    private var currentJob: Job? = null
    
    init {
        observeClients()
    }
    
    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    private fun observeClients() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            combine(
                _searchQuery,
                _selectedFilter
            ) { query, filter ->
                when {
                    query.isNotBlank() -> clientRepository.searchClients(query)
                    else -> when (filter) {
                        FilterType.ALL -> clientRepository.getAllClients()
                        FilterType.VIP -> clientRepository.filterByScore(80)
                        FilterType.PREMIUM -> clientRepository.filterByScore(60)
                        FilterType.ACTIVE -> clientRepository.filterByStatus("active")
                    }
                }
            }
                .flatMapLatest { it }
                .collect { _clients.value = it }
        }
    }
    
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        observeClients()
    }
    
    fun selectFilter(filter: FilterType) {
        _selectedFilter.value = filter
        observeClients()
    }
    
    fun addQuickNote(clientId: String, content: String, authorId: String) {
        viewModelScope.launch {
            val quickNote = QuickNote(
                clientId = clientId,
                content = content,
                authorId = authorId
            )
            quickNoteRepository.insertNote(quickNote)
        }
    }
    
    fun getQuickNotes(clientId: String): Flow<List<QuickNote>> {
        return quickNoteRepository.getNotesByClient(clientId)
    }
}

enum class FilterType {
    ALL, VIP, PREMIUM, ACTIVE
}

