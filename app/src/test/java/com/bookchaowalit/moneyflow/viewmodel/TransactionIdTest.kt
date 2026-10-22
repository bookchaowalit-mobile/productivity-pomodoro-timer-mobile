package com.bookchaowalit.moneyflow.viewmodel

import com.bookchaowalit.moneyflow.models.TransactionType
import org.junit.Assert.assertEquals
import org.junit.Test

class TransactionIdTest {

    @Test
    fun transactionsAddedBackToBackGetDistinctIds() {
        val vm = MoneyViewModel()
        // Added within the same millisecond, as a fast double tap would.
        repeat(50) { i -> vm.addTransaction(1.0 + i, "Food & Drink", "item $i", TransactionType.EXPENSE) }

        val ids = vm.transactions.value.map { it.id }
        assertEquals(50, ids.toSet().size)
    }

    @Test
    fun deletingOneTransactionKeepsTheOthers() {
        val vm = MoneyViewModel()
        vm.addTransaction(10.0, "Food & Drink", "lunch", TransactionType.EXPENSE)
        vm.addTransaction(20.0, "Transport", "taxi", TransactionType.EXPENSE)

        val taxi = vm.transactions.value.first { it.description == "taxi" }
        vm.deleteTransaction(taxi.id)

        assertEquals(listOf("lunch"), vm.transactions.value.map { it.description })
    }

    @Test
    fun newestTransactionIsFirstAndRecentRespectsLimit() {
        val vm = MoneyViewModel()
        (1..12).forEach { vm.addTransaction(it.toDouble(), "Other", "t$it", TransactionType.EXPENSE) }

        assertEquals("t12", vm.transactions.value.first().description)
        assertEquals(10, vm.recentTransactions().size)
        assertEquals(listOf("t12", "t11", "t10"), vm.recentTransactions(3).map { it.description })
    }
}
