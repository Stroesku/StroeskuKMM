package ru.stroesku.kmm.domain.exception

import ru.stroesku.kmm.domain.model.ErrorModel

data class ResponseException(
    val model: ErrorModel,
) : ApsException(model)