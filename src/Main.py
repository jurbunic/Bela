from src.Card import Card
from src.Player import Player
from random import randint

cards = []
for i in range(1, 5, 1):
    cards.append(Card("Seven", i, 0))
    cards.append(Card("Eight", i, 0))
    cards.append(Card("Nine", i, 0))
    cards.append(Card("Ten", i, 10))
    cards.append(Card("Jack", i, 2))
    cards.append(Card("Dame", i, 3))
    cards.append(Card("King", i, 4))
    cards.append(Card("Ace", i, 11))

agents = []
for i in range(0, 4):
    a = Player('player_%d@127.0.0.1' % i, "tajna")
    agents.append(a)

for player in agents:
    for i in range(0, 8):
        card = cards.__getitem__(randint(0, len(cards) - 1))
        player.addCard(card)
        cards.remove(card)

for player in agents:
    print player.getName()
    player.getAllCards()
    print '---------\n'

