package com.task_compose.home.presentation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.task_compose.home.presentation.HomeViewModel
import com.task_compose.home.presentation.mvi.HomeIntent
import com.task_compose.home.presentation.mvi.HomeState
import com.task_compose.home.presentation.screen.components.CategoriesPager
import com.task_compose.home.presentation.screen.components.ProductItem
import com.task_compose.home.presentation.screen.components.SearchBar
import com.task_compose.ui.R
import com.task_compose.ui.extensions.collectEffects
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoute() {
    val viewModel: HomeViewModel = koinViewModel()
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    viewModel.collectEffects { effect ->
        when (effect) {
            else -> Unit
        }
    }

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        HomeScreen(
            state = state,
            onQueryChange = { viewModel.onIntent(HomeIntent.Search(it)) },
            onFabClick = { viewModel.onIntent(HomeIntent.GetListStats) },
            onCategoryChange = { viewModel.onIntent(HomeIntent.OnCategoryChange(it)) }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    onQueryChange: (String) -> Unit,
    onFabClick: () -> Unit,
    onCategoryChange: (Int) -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showBottomSheet = true
                onFabClick()
            }) {
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                CategoriesPager(categories = state.categories, onCategoryChange = onCategoryChange)
            }

            stickyHeader {
                SearchBar(
                    query = state.query,
                    onQueryChange = onQueryChange
                )
            }

            items(state.products) { product ->
                ProductItem(product = product)
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        ) {
            if (state.isBottomSheetLoading) {
                CircularProgressIndicator()
            } else {
                Text(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = stringResource(
                        R.string.list_items_title_format,
                        state.listStats.totalItems
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                state.listStats.topChars.forEach { topChar ->
                    Text(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        text = "${topChar.char} = ${topChar.count}"
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview(apiLevel = 35)
@Composable
private fun HomeScreenPreview() {

}