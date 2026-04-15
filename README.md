# Task Compose

An Android application built with Jetpack Compose that displays a product catalog with real-time search filtering and list statistics. Developed as a test task.

---

## Features

- **Product catalog** — fetches and displays products from a remote REST API, each showing an image, title, and description
- **Real-time search** — filters the product list as the user types, debounced at 300 ms
- **Category pager** — horizontal swipeable pager displaying product categories
- **List statistics** — modal bottom sheet showing the total letter count and top 3 most frequent characters across all visible product titles

---

## Tech Stack

| Category | Library / Tool |
|---|---|
| Language | Kotlin 1.9 |
| UI | Jetpack Compose, Material 3 |
| Architecture | MVI + Clean Architecture |
| Async | Kotlin Coroutines + Flow |
| Networking | Retrofit 2, OkHttp, Kotlin Serialization |
| Image loading | Coil |
| Dependency injection | Koin 3 with KSP annotations |
| Navigation | Jetpack Navigation Compose |
| Testing | JUnit 4, MockK, kotlinx-coroutines-test |
| Build | Gradle convention plugins, Version Catalog |

---

## Architecture

The project follows **Clean Architecture** with an **MVI** (Model–View–Intent) presentation layer, split into Gradle modules for separation of concerns.

### Module graph

```
app
├── feature:home
│   ├── presentation  (MVI ViewModel, Composables)
│   ├── domain        (Use Cases, Repository interface)
│   └── data          (Repository implementation)
├── core:mvi          (MviBaseViewModel, Reducer interface)
├── core:network:api  (Retrofit service interfaces, UrlProvider)
├── core:network:impl (Retrofit/OkHttp setup, Koin module)
├── core:network:entities (DTOs)
├── core:ui-model     (UI data models, DTO→UI mappers)
├── core:ui           (Shared Compose extensions)
├── core:utils        (Flow helpers, Kotlin extensions)
├── core:dispatchers:api  (DispatchersProvider interface)
├── core:dispatchers:impl (Coroutine dispatcher implementation)
├── core:request      (Network request helpers)
├── navigation        (RootNavHost composable)
└── screens           (Screens sealed class)
```

### MVI layer (`feature:home`)

```
HomeIntent  ──▶  HomeViewModel  ──▶  HomeAction  ──▶  HomeReducer  ──▶  HomeState
    (user events)        │                                                    │
                         │                                                    ▼
                    Use Cases                                          HomeScreen (UI)
```

| Class | Role |
|---|---|
| `HomeIntent` | Sealed interface for user events (`Search`, `GetListStats`) |
| `HomeViewModel` | Orchestrates use cases, emits actions, handles debounce |
| `HomeAction` | Sealed interface for state mutations (`UpdateProducts`, `UpdateCategories`, etc.) |
| `HomeReducer` | Pure function: `(action, state) → state` |
| `HomeState` | Immutable snapshot of the entire screen state |
| `HomeEffect` | One-shot side effects (extensible, currently unused) |

`MviBaseViewModel` (in `core:mvi`) wires actions through a `MutableSharedFlow`, applies the reducer via `StateFlow.update`, and exposes effects via a `Channel`.

### Domain layer

| Use Case | Responsibility |
|---|---|
| `GetProductsUseCase` | Fetches products from the repository and filters by search query (case-insensitive) |
| `GetCategoriesUseCase` | Fetches all product categories |
| `GetListStatsUseCase` | Computes total letter count and top 3 most frequent characters from current product titles |

### Data layer

`HomeRepositoryImpl` delegates to `ProductsApi` (Retrofit) and maps `ProductDto` / `CategoryDto` to `ProductUI` / `CategoryUI` using extension functions in `core:ui-model`.

---

## Project structure

```
task_compose/
├── app/                        # Application entry point, DI setup
├── build-logic/                # Gradle convention plugins
│   └── convention/
│       ├── AndroidApplicationConventionPlugin
│       ├── AndroidLibraryConventionPlugin
│       ├── AndroidLibraryComposeConventionPlugin
│       └── AndroidComposeFeatureConventionPlugin
├── core/
│   ├── dispatchers/api|impl    # CoroutineContext providers
│   ├── mvi/                    # MviBaseViewModel, Reducer<A,S>
│   ├── network/api|impl|entities # Retrofit service, OkHttp, DTOs
│   ├── request/                # Network request wrapper
│   ├── ui/                     # collectEffects composable helper
│   ├── ui-model/               # ProductUI, CategoryUI, ListStats
│   └── utils/                  # emitFlow, .orDefault()
├── feature/
│   └── home/
│       ├── data/repository/    # HomeRepositoryImpl
│       ├── domain/
│       │   ├── repository/     # HomeRepository interface
│       │   └── usecase/        # GetProductsUseCase, GetCategoriesUseCase, GetListStatsUseCase
│       └── presentation/
│           ├── mvi/            # HomeState, HomeAction, HomeIntent, HomeEffect, HomeReducer
│           ├── screen/         # HomeRoute, HomeScreen
│           │   └── components/ # SearchBar, ProductItem, CategoriesPager
│           └── HomeViewModel
├── navigation/                 # RootNavHost
└── screens/                    # Screens sealed class
```

---

## Build setup

The project uses **Gradle convention plugins** (in `build-logic/`) to share build configuration across modules without duplication. Key plugins:

- `project.android.library` — base Android library config
- `project.android.library.compose` — adds Compose compiler and tooling
- `project.android.feature` — feature module preset (adds core deps, DI, test bundles)

Dependencies are managed centrally in `gradle/libs.versions.toml`.

### Requirements

- Android Studio Hedgehog or newer
- JDK 17
- Android SDK 35 (compileSdk), minSdk 26

### Running

```bash
./gradlew assembleDebug
```

### Running tests

```bash
./gradlew :feature:home:test
```

---

## Testing

Unit tests live in `feature/home/src/test/` and cover the core business logic:

| Test class | What is tested |
|---|---|
| `HomeReducerTest` | Every `HomeAction` produces the correct `HomeState` transition |
| `GetProductsUseCaseTest` | Product filtering: empty query, case-insensitive match, no match |
| `GetListStatsUseCaseTest` | Char frequency: counting, top-3 limit, non-letter exclusion, multi-product combining, case folding |

Test dependencies used: **JUnit 4**, **MockK**, **kotlinx-coroutines-test** (`UnconfinedTestDispatcher` + `runTest`).