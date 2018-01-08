class Card:

    # colour = 1 - heart
    # colour = 2 - diamond
    # colour = 3 - clubs
    # colour = 4 - spades
    def __init__(self, name, colour, value):
        self.name = name
        self.colour = colour
        self.value = value

    def get_name(self):
        return self.name

    def get_value(self):
        return self.value

    def get_colour(self):
        return {
            1: 'heart',
            2: 'diamond',
            3: 'club',
            4: 'spade'
        }[self.colour]