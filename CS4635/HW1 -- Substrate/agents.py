#-------------------------------------------------------------------------------
# Name:        agents
# Purpose:     provides classes for the tic-tac-toe playing agents
#
# Author:      Nathan
#
# Created:     02/09/2013
# Copyright:   (c) Nathan 2013
# Licence:     MIT
#-------------------------------------------------------------------------------
import random
import ai
class Agent(object):
    def __init__(self):
        pass

    def warning(self, message):
        print self.name + ' [WARN]: ' + message

'''
    NaiveAgent always tries to win without regard to the other player
'''
class NaiveAgent(Agent):
    def __init__(self, label='X'):
        self.label = label
        self.name = 'NaiveAgent'

    def play(self, gamestate):
        chains = gamestate.getChains(self.label)
        while not chains.empty():
            chain = chains.get()
            point = chain[1]
            if chain[2] == 0:
                point = (point[0] + 1, point[1])
            elif chain[2] == 1:
                point = (point[0], point[1] + 1)
            else:
                point = (point[0] + 1, point[1] + 1)

            if gamestate.isPointLegal(point):
                return gamestate.play(point, self.label)

        self.warning('No suitable chains -- moving at random.')
        i = random.randrange(0, len(gamestate.legalMoves))
        point = gamestate.legalMoves[i]
        return gamestate.play(point, self.label)


'''
    ThoughtfulAgent uses Minimax to make its moves
'''
class ThoughtfulAgent(Agent):
    def __init__(self, label='O'):
        self.label = label
        self.name = 'ThoughtfulAgent'

    def play(self, gamestate):
        random.shuffle(gamestate.legalMoves)
        dummy = NaiveAgent()

        # Ugly Hack
        if self.label == dummy.label:
            dummy.label = 'O'

        state = ai.minimaxPlayer(gamestate, self, dummy, 0, ai.inf)
        move = state['move']
        return gamestate.play(move, self.label)

