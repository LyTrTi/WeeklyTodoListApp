package com.example.weeklytodolist.ui.home.fragment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ContentFragment(
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier.padding(
            top = contentPaddingValues.calculateTopPadding(),
            bottom = contentPaddingValues.calculateBottomPadding()
        )
    ) {
        content()
    }
}