package com.bookchaowalit.moneyflow.viewmodel

import com.bookchaowalit.moneyflow.models.MonthlySummary
import com.bookchaowalit.moneyflow.models.TransactionType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TotalsTest {

    private val eps = 1e-9

    @Test
    fun emptyLedgerIsAllZero() {
        val vm = MoneyViewModel()
        assertEquals(0.0, vm.totalBalance, eps)
        assertEquals(0.0, vm.totalIncome, eps)
        assertEquals(0.0, vm.totalExpenses, eps)
    }

    @Test
    fun balanceIsIncomeMinusExpenses() {
        val vm = MoneyViewModel()
        vm.addTransaction(3000.0, "Salary", "October", TransactionType.INCOME)
        vm.addTransaction(450.5, "Freelance", "logo", TransactionType.INCOME)
        vm.addTransaction(120.25, "Food & Drink", "groceries", TransactionType.EXPENSE)
        vm.addTransaction(80.0, "Transport", "fuel", TransactionType.EXPENSE)

        assertEquals(3450.5, vm.totalIncome, eps)
        assertEquals(200.25, vm.totalExpenses, eps)
        assertEquals(3250.25, vm.totalBalance, eps)
    }

    @Test
    fun deletingATransactionUpdatesTotals() {
        val vm = MoneyViewModel()
        vm.addTransaction(100.0, "Salary", "pay", TransactionType.INCOME)
        vm.addTransaction(40.0, "Bills", "power", TransactionType.EXPENSE)
        vm.deleteTransaction(vm.transactions.value.first { it.type == TransactionType.EXPENSE }.id)

        assertEquals(100.0, vm.totalBalance, eps)
        assertEquals(0.0, vm.totalExpenses, eps)
    }

    @Test
    fun zeroNegativeAndNonFiniteAmountsAreRejected() {
        val vm = MoneyViewModel()
        // The add dialog parses free text with toDoubleOrNull(), so "0" or "0.00" reach the ViewModel.
        vm.addTransaction(0.0, "Other", "zero", TransactionType.EXPENSE)
        vm.addTransaction(-5.0, "Other", "negative", TransactionType.EXPENSE)
        vm.addTransaction(Double.NaN, "Other", "nan", TransactionType.EXPENSE)
        vm.addTransaction(Double.POSITIVE_INFINITY, "Other", "inf", TransactionType.INCOME)
        assertTrue(vm.transactions.value.isEmpty())

        vm.addTransaction(0.01, "Other", "a cent", TransactionType.EXPENSE)
        assertEquals(1, vm.transactions.value.size)
        assertEquals(-0.01, vm.totalBalance, eps)
    }

    @Test
    fun monthlySummaryBalance() {
        assertEquals(-25.0, MonthlySummary("2026-10", income = 75.0, expenses = 100.0).balance, eps)
    }
}
