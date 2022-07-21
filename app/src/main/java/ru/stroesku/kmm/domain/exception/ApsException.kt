package ru.stroesku.kmm.domain.exception

import ru.stroesku.kmm.domain.model.ErrorModel

abstract class ApsException(model: ErrorModel) : Exception(model.message)