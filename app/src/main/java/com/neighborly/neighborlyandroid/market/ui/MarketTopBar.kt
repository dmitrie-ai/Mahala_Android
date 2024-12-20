package com.neighborly.neighborlyandroid.market.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neighborly.neighborlyandroid.common.models.Category
import com.neighborly.neighborlyandroid.common.models.SortBy


@Composable
fun MarketTopBar(modifier: Modifier=Modifier, onSearch:(String)->Unit,
                 categoryState:List<CategoryOption>,
                 onToggleCategories:(category:Category, value:Boolean)-> Unit,
                 onToggleSortByOptions:(option: SortBy, value:Boolean)->Unit,
                 activeSortBy: SortBy,sortByOptions:List<SortBy>){
    var isVisibleCategoryAndSortByBar by remember { mutableStateOf(true) }
    Column {
        MarketSearchBar(modifier = modifier, onSearchButtonPress = onSearch,
            toggleCategoryAndSortByVisibility = {value->isVisibleCategoryAndSortByBar = value})
        if (isVisibleCategoryAndSortByBar){
            CategoriesAndSortByBar(modifier = modifier, categoryState=categoryState,
                onToggleCategories = onToggleCategories,
                onToggleSortByOptions = onToggleSortByOptions,
                activeSortBy = activeSortBy,
                sortByOptions = sortByOptions)
        }



    }


}
@Composable
fun CategoriesAndSortByBar(modifier: Modifier, categoryState:List<CategoryOption>,onToggleCategories: (category: Category, value: Boolean) -> Unit, onToggleSortByOptions: (option: SortBy, value: Boolean) -> Unit,activeSortBy: SortBy,sortByOptions: List<SortBy>){
    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth()) {
        CategoriesDropdown(
            options = categoryState, onOptionToggled = onToggleCategories, modifier = Modifier
                .width(LocalConfiguration.current.screenWidthDp.dp / 2)  // Half screen width
        )
        SortByDropdown(
            onOptionToggled = onToggleSortByOptions,
            activeOption = activeSortBy,
            labelText = "Sort By",
            options = sortByOptions
        )
    }
}

