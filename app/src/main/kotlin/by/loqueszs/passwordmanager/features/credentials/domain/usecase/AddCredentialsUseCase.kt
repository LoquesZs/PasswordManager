package by.loqueszs.passwordmanager.features.credentials.domain.usecase

import by.loqueszs.passwordmanager.features.credentials.domain.model.CredentialsUIModel
import by.loqueszs.passwordmanager.features.credentials.domain.model.InvalidCredentialsException
import by.loqueszs.passwordmanager.features.credentials.domain.repository.CredentialsRepository
import kotlin.jvm.Throws
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddCredentialsUseCase(
    private val repository: CredentialsRepository
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    private var titlesList: List<String> = emptyList()

    init {
        scope.launch {
            titlesList = repository.getTitleList()
        }
    }

    @Throws(InvalidCredentialsException::class)
    suspend operator fun invoke(credentials: CredentialsUIModel) {
        if (credentials.title.isBlank()) {
            throw InvalidCredentialsException("Title can not be blank!")
        }
        if (titlesList.contains(credentials.title)) {
            throw InvalidCredentialsException("Title already exists!")
        }
        repository.insertAll(credentials.toCredentialsEntity())
    }
}