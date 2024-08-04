package com.fifteen11.checkappversion.screens.history

import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fifteen11.checkappversion.data.local.UserLoginHistoryEntity
import com.fifteen11.checkappversion.navigation.Route
import com.fifteen11.checkappversion.screens.component.AppBar
import com.fifteen11.checkappversion.screens.component.LockScreenOrientation
import com.fifteen11.checkappversion.utils.AppConstant.Companion.DATE_FORMAT
import com.fifteen11.checkappversion.utils.DateUtil.convertDateString
import com.fifteen11.checkappversion.viewmodel.UserHistoryViewModel

@Composable
fun UserLoginHistory(historyViewModel: UserHistoryViewModel) {

    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    Scaffold(
        topBar = {
            AppBar(Route.UserLoginHistoryScreen.title.toString())
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Content(historyViewModel)
        }
    }
}

@Composable
fun Content(viewModel: UserHistoryViewModel) {
    LaunchedEffect(key1 = true, block = {
        viewModel.getUserLoginHistory()
    })

    val contents by viewModel.loginHistory.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val mod = Modifier
        .padding(15.dp)
        .fillMaxWidth()
        .height(80.dp)

    errorMessage?.let { message ->
        Text(text = "Error: $message")
    }

    LazyColumn(
        content = {
            items(contents) {
                val item = ImmutableLoginHistory(it)
                HistoryCard(wrapper = item, modifier = mod)
            }
        }, modifier = Modifier.fillMaxSize()
    )
}

@Immutable
data class ImmutableLoginHistory(val userLoginHistoryEntity: UserLoginHistoryEntity)

@Composable
fun HistoryCard(
    wrapper: ImmutableLoginHistory,
    modifier: Modifier
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(3f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = wrapper.userLoginHistoryEntity.userName,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = convertDateString(
                        format = DATE_FORMAT,
                        dateString = wrapper.userLoginHistoryEntity.createdAt.toString()
                    ),
                    maxLines = 1,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}