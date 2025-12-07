package pt.iade.ei.lootx.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import pt.iade.ei.lootx.R
import pt.iade.ei.lootx.model.Game
import pt.iade.ei.lootx.model.PurchasableItem
import pt.iade.ei.lootx.ui.theme.LootXTheme
import java.text.NumberFormat
import java.util.Locale

class GameDetailActivity : ComponentActivity() {

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val game = intent.getParcelableExtra<Game>(MainActivity.Companion.EXTRA_GAME)

        if (game == null) {
            Toast.makeText(this, "Erro: Jogo não encontrado.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setContent {
            val ptLocale = Locale.forLanguageTag("pt-PT")

            LootXTheme {
                GameDetailScreen(
                    game = game,
                    onBackClick = { finish() },
                    onPurchase = { item ->
                        val priceString =
                            NumberFormat.getCurrencyInstance(ptLocale).format(item.price)
                        Toast.makeText(
                            this,
                            "Acabou de comprar o item ${item.name} por $priceString",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailScreen(
    game: Game,
    onBackClick: () -> Unit,
    onPurchase: (PurchasableItem) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<PurchasableItem?>(null) }

    val ptLocale = remember { Locale.forLanguageTag("pt-PT") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(game.title, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Ação de favorito */ }) {
                        Icon(Icons.Filled.FavoriteBorder, contentDescription = "Favorito")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                GameHeader(game = game)
            }

            item {
                Text(
                    text = "Purchasable Items",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
            }

            items(game.items) { item ->
                PurchasableItemRow(
                    item = item,
                    onClick = {
                        selectedItem = item
                        showBottomSheet = true
                    },
                    locale = ptLocale
                )
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
                selectedItem?.let { item ->
                    ItemModalContent(
                        item = item,
                        onBuyClick = {
                            onPurchase(item)
                            showBottomSheet = false
                        },
                        locale = ptLocale
                    )
                }
            }
        }
    }
}

@Composable
fun GameHeader(game: Game) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
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
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Game\nImage", color = Color.Gray, fontSize = 14.sp)
                }
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = game.description,
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}

@Composable
fun PurchasableItemRow(item: PurchasableItem, onClick: () -> Unit, locale: Locale) {
    val priceString = NumberFormat.getCurrencyInstance(locale).format(item.price)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            if (item.imageResId != 0) {
                Image(
                    painter = painterResource(id = item.imageResId),
                    contentDescription = "Imagem do item ${item.name}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Item\nImage", color = MaterialTheme.colorScheme.onSecondaryContainer, fontSize = 10.sp)
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = item.description,
                fontSize = 13.sp,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = priceString,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun ItemModalContent(item: PurchasableItem, onBuyClick: () -> Unit, locale: Locale) {
    val priceString = NumberFormat.getCurrencyInstance(locale).format(item.price)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Color.LightGray)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )

        Text(
            text = item.name,
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                if (item.imageResId != 0) {
                    Image(
                        painter = painterResource(id = item.imageResId),
                        contentDescription = "Imagem do item ${item.name}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Item\nImage", color = MaterialTheme.colorScheme.onSecondaryContainer, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = item.description,
                modifier = Modifier.weight(1f),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = priceString,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.primary
            )

            Button(
                onClick = onBuyClick,
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Buy with 1-click", color = Color.White, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGameDetail() {
    val dummyIconRes = R.drawable.ic_launcher_background
    val demoGame = Game(
        id = 1,
        title = "LootX",
        description = "Esta é uma descrição de demonstração para o jogo. É longa o suficiente para forçar quebra de linha.",
        imageResId = R.drawable.rainbow_urban_blue,

        items = listOf(
            PurchasableItem(1, "Elite Skin - Ash", "Skin exclusiva para operadora Ash.", 19.49, R.drawable.rainbow_item_elite),
            PurchasableItem(2, "Weapon Cammo - Urban Blue", "Cammo azul escuro para armas automáticas.", 4.49, R.drawable.rainbow_urban_blue),
            PurchasableItem(3, "Cammo - Mini Drone", "Pintura para drone.", 3.99, R.drawable.rainbow_mini_drone)
        )
    )
    LootXTheme {
        GameDetailScreen(
            game = demoGame,
            onBackClick = {},
            onPurchase = {}
        )
    }
}