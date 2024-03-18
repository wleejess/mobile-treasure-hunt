package com.example.treasurehunt.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.treasurehunt.R
import com.example.treasurehunt.TreasureHuntScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController, viewModel: TreasureHuntViewModel) {
    Scaffold(
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp)
            ) {
                item {
                    Image(
                        painter = painterResource(id = R.drawable.maps),
                        contentDescription = "Pile of maps",
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                item {
                    Text(
                        text = stringResource(id = R.string.rule_title),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                }
                items(11) { index ->
                    Text(
                        text = stringResource(id = R.string::class.java.getField("rule${index + 1}").getInt(null)),
                        fontSize = 15.sp,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                }
                item {
                    Button(
                        onClick = {
                            navController.navigate(route = TreasureHuntScreen.Clue.name)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(text = "Start")
                    }
                }
            }
        }
    )
}
