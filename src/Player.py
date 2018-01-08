import spade


class Player(spade.Agent.Agent):
    class Ponasanje(spade.Behaviour.OneShotBehaviour):
        def onStart(self):
            print 'Pokrece se ponasanje!'

        def _process(self):
            print 'Izvrsavam ponasanje'

        def onEnd(self):
            print 'Gotov sam :('

    def __init__(self, agentjid, password):
        super(Player, self).__init__(agentjid, password)
        self.cards = []

    def _setup(self):
        ponasanje = self.Ponasanje()
        self.addBehaviour(ponasanje, None)

    def addCard(self, card):
        self.cards.append(card)

    def getAllCards(self):
        for i in range(0, len(self.cards)):
            card = self.cards.__getitem__(i)
            print card.getName() + " " + card.getColour()
