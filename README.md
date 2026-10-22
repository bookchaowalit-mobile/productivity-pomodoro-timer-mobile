# productivity-pomodoro-timer-mobile — contains "MoneyFlow" (expense tracker)

> **Name/content mismatch.** The repository is named *pomodoro timer*, but the
> code in it is **MoneyFlow, a Jetpack Compose expense tracker**. There is no
> Pomodoro / focus-timer code here. The repo has not been renamed; whether to
> rename it, move MoneyFlow elsewhere, or archive it is an owner decision
> (lifecycle is currently unknown in the repository catalog).

## What is here

A single-module Android app (`app/`, package `com.bookchaowalit.moneyflow`):

- `MainActivity.kt` — Compose UI: Home (balance, income, expenses, recent
  transactions, add dialog), Stats (spending by category) and History (all
  transactions) behind a bottom navigation bar.
- `viewmodel/MoneyViewModel.kt` — in-memory ledger: add/delete transactions,
  totals, expenses grouped by category with percentages.
- `models/Models.kt` — `Transaction`, `Category`, `CategoryTotal`,
  `MonthlySummary`.

Data lives in memory only and is lost when the app process ends. See
[PRODUCT.md](PRODUCT.md) for exactly what the app does and does not do.

## Tech stack

- Kotlin 1.9.20, Jetpack Compose (BOM 2024.02.00, compiler extension 1.5.5), Material 3
- Android Gradle Plugin 8.2.0, min SDK 26, target/compile SDK 34
- Gradle 8.10.2 wrapper (committed, `distributionSha256Sum` pinned), JDK 17

## Build and test

Requires JDK 17 and the Android SDK (`ANDROID_HOME` or `local.properties`).

```bash
./gradlew :app:testDebugUnitTest   # JVM unit tests for the ViewModel (app/src/test)
./gradlew :app:assembleDebug       # debug APK
```

CI (`.github/workflows/build.yml`) runs both on `ubuntu-24.04` plus the shared
`security.yml` scan (gitleaks, Semgrep, OSV-Scanner, Trivy) from
`bookchaowalit/platform-ci-workflows` v1.1.1. Any failing step fails the
workflow (no `|| true`).

## License

MIT — see [LICENSE](LICENSE).
