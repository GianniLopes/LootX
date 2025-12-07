Game Store: LootX

Autor: Gianni Lopes Disciplina: Programação para Dispositivos Móveis
Tecnologias: Kotlin e Jetpack Compose

- Sobre o Projeto

Este projeto é a base de uma app de loja de jogos, onde é possível ver jogos, itens e DLCs de uma empresa fictícia.Tudo foi feito seguindo o que a unidade curricular pediu.

- Arquitetura e Organização (MVC)

A app foi construída usando o padrão Model-View-Controller (MVC), como solicitado, para manter tudo organizado e mais fácil de mexer no futuro.Aqui está como ficou dividido:

Model: Totalmente contido no pacote “pt.iade.ei.lootx.model”.

Inclui:

Game.kt e PurchasableItem.kt — as classes que guardam os dados;

GameData.kt — funciona como uma “base de dados” simples que fornece os jogos e os itens.

View & Controller (Interface e Lógica): Implementado nas Activities e nos Composables. 

Parte visual + parte da lógica:

MainActivity.kt: funciona como ecrã principal e faz a navegação para os detalhes dos jogos.

GameDetailActivity.kt: recebe o objeto “Game” vindo da Intent e trata de toda a interação (cliques, modal, compra, etc.).

- Funcionalidades Implementadas

A app inclui tudo o que foi pedido ao nível da interface, navegação e interação:

1. Navegação com Intents

A app passa sempre pela MainActivity > GameDetailActivity

O objeto “Game” é enviado como Parcelable, garantindo que tudo chega corretamente.

2. Interface feita em Jetpack Compose

- Ecrã Principal

TopAppBar com o título "LootX"

BottomNavigationBar com 3 opções (Destaque, Histórico, Perfil)

Lista de jogos feita com LazyColumn e GameCard

Ecrã de Detalhes

Mostra a imagem do jogo

Descrição

Lista de itens compráveis do jogo

O design segue um estilo moderno, simples e inspirado em lojas digitais.

3. Interação e Feedback ao Utilizador

ModalBottomSheet: Ao clicar num item, abre um modal com mais detalhes e o botão de compra.

Toast: Quando o utilizador compra um item, aparece uma mensagem a confirmar, com o preço formatado.
