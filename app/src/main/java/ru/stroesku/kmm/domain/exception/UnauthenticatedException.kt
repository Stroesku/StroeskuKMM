package ru.stroesku.kmm.domain.exception

import ru.stroesku.kmm.domain.model.ErrorModel

data class UnauthenticatedException(
    val model: ErrorModel,
) : ApsException(model)