from src.Card import Card

# define cards
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
for card in cards:
    print "Karta: " + card.get_name() + " boja = ", card.get_colour()


