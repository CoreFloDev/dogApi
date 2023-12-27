package io.coreflodev.common.arch

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface UseCaseAggregator<A : DomainAction, R : DomainResult> {

    fun execute(scope: CoroutineScope) : (Flow<A>) -> Flow<R>
}
