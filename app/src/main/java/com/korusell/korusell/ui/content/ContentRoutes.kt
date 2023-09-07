package com.korusell.korusell.ui.content

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Contacts
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.korusell.korusell.R
import com.korusell.korusell.ui.RootRoutes
import com.korusell.korusell.ui.content.contacts.Contacts
import com.korusell.korusell.ui.content.maps.Maps
import com.korusell.korusell.ui.content.search.Search

enum class ContentRoutes(
    val route: String,
    @StringRes val labelId: Int,
    val icon: ImageVector,
    val content: @Composable () -> Unit
) {
    CONTACTS(
        route = "${RootRoutes.CONTENT.route}_contacts",
        labelId = R.string.contacts,
        icon = Icons.Outlined.Contacts,
        content = { Contacts()}
    ),
    SEARCH(
        route = "${RootRoutes.CONTENT.route}_search",
        labelId = R.string.search,
        icon = Icons.Outlined.Search,
        content = { Search() }
    ),
    MAP(
        route = "${RootRoutes.CONTENT.route}_map",
        labelId = R.string.map,
        icon = Icons.Outlined.Map,
        content = { Maps() }
    )
}