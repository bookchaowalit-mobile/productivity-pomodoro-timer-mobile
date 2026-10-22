package com.bookchaowalit.moneyflow.viewmodel

import com.bookchaowalit.moneyflow.models.TransactionType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExpensesByCategoryTest {

    private val eps = 1e-9

    @Test
    fun noExpensesGivesEmptyBreakdown() {
        val vm = MoneyViewModel()
        vm.addTransaction(500.0, "Salary", "pay", TransactionType.INCOME)
        assertTrue(vm.expensesByCategory().isEmpty())
    }

    @Test
    fun groupsExpensesSortsByTotalAndIgnoresIncome() {
        val vm = MoneyViewModel()
        vm.addTransaction(30.0, "Food & Drink", "lunch", TransactionType.EXPENSE)
        vm.addTransaction(10.0, "Food & Drink", "coffee", TransactionType.EXPENSE)
        vm.addTransaction(60.0, "Bills", "internet", TransactionType.EXPENSE)
        vm.addTransaction(1000.0, "Salary", "pay", TransactionType.INCOME)

        val breakdown = vm.expensesByCategory()
        assertEquals(listOf("Bills", "Food & Drink"), breakdown.map { it.category })
        assertEquals(60.0, breakdown[0].total, eps)
        assertEquals(40.0, breakdown[1].total, eps)
        assertEquals(60.0, breakdown[0].percentage, eps)
        assertEquals(40.0, breakdown[1].percentage, eps)
        assertEquals(100.0, breakdown.sumOf { it.percentage }, eps)
    }

    @Test
    fun usesCategoryColourAndFallsBackForUnknownCategory() {
        val vm = MoneyViewModel()
        vm.addTransaction(5.0, "Transport", "bus", TransactionType.EXPENSE)
        vm.addTransaction(5.0, "Gifts", "not a built-in category", TransactionType.EXPENSE)

        val byName = vm.expensesByCategory().associateBy { it.category }
        assertEquals(0xFF4ECDC4, byName.getValue("Transport").color)
        assertEquals(0xFF636E72, byName.getValue("Gifts").color)
    }

    @Test
    fun builtInCategoriesHaveUniqueNamesForBothTypes() {
        val vm = MoneyViewModel()
        assertEquals(vm.categories.size, vm.categories.map { it.name }.toSet().size)
        assertTrue(vm.categories.any { it.type == TransactionType.INCOME })
        assertTrue(vm.categories.any { it.type == TransactionType.EXPENSE })
    }
}
