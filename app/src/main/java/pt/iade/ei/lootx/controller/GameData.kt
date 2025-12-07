package pt.iade.ei.lootx.controller

import pt.iade.ei.lootx.R
import pt.iade.ei.lootx.model.Game
import pt.iade.ei.lootx.model.PurchasableItem

object GameData {

    val rainbowItems = listOf(
        PurchasableItem(
            id = 1,
            name = "Elite Skin – Ash",
            description = "Skin exclusiva para operadora Ash.",
            price = 19.49,
            imageResId = R.drawable.rainbow_item_elite
        ),
        PurchasableItem(
            id = 2,
            name = "Weapon Camo – Urban Blue",
            description = "Camo azul escuro para armas automáticas.",
            price = 4.49,
            imageResId = R.drawable.rainbow_urban_blue
        ),
        PurchasableItem(
            id = 3,
            name = "Cammo – Mini Drone",
            description = "Pintura para drone.",
            price = 3.99,
            imageResId = R.drawable.rainbow_mini_drone
        )
    )

    val gtaItems = listOf(
        PurchasableItem(
            id = 4,
            name = "Carro Tunado – Street Racer",
            description = "Carro rápido com melhorias de aceleração.",
            price = 12.99,
            imageResId = R.drawable.gta_street_racer
        ),
        PurchasableItem(
            id = 5,
            name = "Arma Especial – SMG Chrome",
            description = "Submetralhadora com bom controlo.",
            price = 6.49,
            imageResId = R.drawable.gta_smg_chrome
        ),
        PurchasableItem(
            id = 6,
            name = "Traje – Estilo Vip",
            description = "Fato elegante para o personagem.",
            price = 3.99,
            imageResId = R.drawable.gta_vip_suit
        )
    )

    val games = listOf(
        Game(
            id = 1,
            title = "Rainbow Six Siege",
            description = "Jogo tático de equipas com foco em estratégia, operadores e destruição de ambiente.",
            imageResId = R.drawable.rainbow_six_siege,
            items = rainbowItems
        ),
        Game(
            id = 2,
            title = "GTA V",
            description = "Ação em mundo aberto com carros, missões, crimes e muita liberdade.",
            imageResId = R.drawable.gta_v,
            items = gtaItems
        )
    )
}