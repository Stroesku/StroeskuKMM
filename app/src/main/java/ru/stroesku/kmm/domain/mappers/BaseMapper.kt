package ru.stroesku.kmm.domain.mappers

interface Mapper<I, O> {

    fun map(input: I): O
}

interface ListMapper<I, O> : Mapper<List<I>, List<O>>

interface NullableInputListMapper<I, O> : Mapper<List<I>?, List<O>>

interface NullableOutputListMapper<I, O> : Mapper<List<I>, List<O>?>

class ListMapperImpl<I, O>(
    private val mapper: Mapper<I, O>
) : ListMapper<I, O> {
    override fun map(input: List<I>): List<O> {
        return input.map { mapper.map(it) }
    }
}

class NullableInputListMapperImpl<I, O>(
    private val mapper: Mapper<I, O>
) : NullableInputListMapper<I, O> {
    override fun map(input: List<I>?): List<O> {
        return input?.map { mapper.map(it) }.orEmpty()
    }
}

class NullableOutputListMapperImpl<I, O>(
    private val mapper: Mapper<I, O>
) : NullableOutputListMapper<I, O> {
    override fun map(input: List<I>): List<O>? {
        return if (input.isEmpty()) null else input.map { mapper.map(it) }
    }
}