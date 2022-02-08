package com.orchestrator.orchestrator.model

data class TransactionCompletionModel(
    val isTransactionsCompleted: Boolean,
    val isTransactionsCompletedSuccessfully: Boolean? = null
)
