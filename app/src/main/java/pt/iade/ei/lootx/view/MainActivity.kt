package pt.iade.ei.lootx.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import pt.iade.ei.lootx.model.Game
import pt.iade.ei.lootx.controller.GameData
import pt.iade.ei.lootx.ui.theme.LootXTheme
import pt.iade.ei.lootx.ui.theme.DarkSurface
import pt.iade.ei.lootx.ui.theme.OnDarkSurface

class MainActivity : ComponentActivity() {

    companion object {
        const val EXTRA_GAME = "extra_game"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LootXTheme {
                GameListScreen(
                    context = this,
                    games = GameData.games
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameListScreen(context: Context, games: List<Game>) {
    Scaffold(
        containerColor = DarkSurface,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkSurface),
                title = { Text("LootX", fontWeight = FontWeight.Bold, color = OnDarkSurface) }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = DarkSurface) {

                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Text("★", fontSize = 20.sp, color = OnDarkSurface) },
                    label = { Text("Destaque", color = OnDarkSurface) }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Text("⌕", fontSize = 20.sp, color = OnDarkSurface) },
                    label = { Text("Histórico", color = OnDarkSurface) }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Text("👤", fontSize = 20.sp, color = OnDarkSurface) },
                    label = { Text("Perfil", color = OnDarkSurface) }
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(games) { game ->
                GameCard(
                    game = game,
                    onClick = {
                        val intent = Intent(context, GameDetailActivity::class.java)
                        intent.putExtra(MainActivity.EXTRA_GAME, game)
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}


@Composable
fun GameCard(game: Game, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (game.imageResId != 0) {
                Image(
                    painter = painterResource(id = game.imageResId),
                    contentDescription = "Imagem do jogo ${game.title}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFE0E0E0))
                ) {
                    Text(
                        text = "Game image as background of card",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Gray
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = game.title,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.7f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGameListScreen() {
    LootXTheme {
        GameListScreen(
            context = LocalContext.current,
            games = GameData.games
        )
    }
}