# MoneyFlow (in repo `productivity-pomodoro-timer-mobile`) — product

## Name/content mismatch

The repository name says "pomodoro timer"; the code is **MoneyFlow, an expense
tracker**. Nothing in this repo is a Pomodoro or focus timer. This document
describes MoneyFlow, because that is what exists. Renaming, moving or archiving
the repo is an open owner decision.

## What exists today

An Android app (Jetpack Compose, Material 3, dark theme) with three tabs:

| Screen  | What it shows / does |
|---------|----------------------|
| Home    | Total balance, total income, total expenses; quick actions that open an "add transaction" dialog (amount, description, income/expense, category); the 10 most recent transactions, each deletable |
| Stats   | Expenses grouped by category, largest first, with amount and % of total |
| History | Every transaction with a count, each deletable |

Built-in categories: 8 expense categories (Food & Drink, Transport, Shopping,
Bills, Entertainment, Health, Education, Other) and 4 income categories
(Salary, Freelance, Investment, Other Income). Amounts are shown with a `$`
prefix; there is no currency setting.

## What it does not do

- No persistence: transactions live in memory (a `ViewModel`) and are lost when
  the process ends.
- No backend, no network access, no accounts, no sync, no analytics.
- No budgets, recurring transactions, monthly history or export, even though
  `Transaction.isRecurring` and `MonthlySummary` exist as model fields/classes;
  the UI does not use them.
- Not published to Google Play or any store. CI builds a debug APK only to
  prove the app compiles.

## Quality

JVM unit tests in `app/src/test/` (3 files) cover the ViewModel: totals,
category breakdown, deletion, unique transaction ids and rejection of invalid
amounts. There are no Compose UI tests.
