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
from game import side
class Agent(object):
    def __init__(self):
        pass

    def warning(self, message):
        print self.name + ' [WARN]: ' + message

'''
    NaiveAgent always tries to win without regard to the other player
'''
class NaiveAgent(Agent):
    def __init__(self, label):
        self.label = label
        self.name = 'NaiveAgent'
        self.history = {}

    def scoreState(self, gs):
        dynagrid = gs.generateDynamics(self.label)
        value = -1
        for row in range(1, side + 1):
            for col in range(1, side + 1):
                value = max(value, max(dynagrid[row][col]))
        return value

    def play(self, gs):
        if gs.getCurrentPlayer() != self.label:
            return None
        random.shuffle(gs.legalMoves)
        opt = {'short': True}
        point, trace = ai.getMove(gs, self.label, None, opt)
        self.history['Turn ' + str(gs.turn)] = trace
        return gs.play(point, self.label)

    def printReasoningTrace(self):
        print 'Reasoning trace for ' + self.label + ': ' + str(self.history)


'''
    ThoughtfulAgent uses Minimax to make its moves
'''
class ThoughtfulAgent(Agent):
    def __init__(self, label):
        self.label = label
        self.name = 'ThoughtfulAgent'
        self.history = {}

    def play(self, gs):
        if gs.getCurrentPlayer() != self.label:
            return None

        state = ai.minimaxPlayer(gs, self.label, gs.getOpponent(self.label), 0)
        move = state['move']
        path = state['path']
        self.history['Turn ' + str(gs.turn)] = path
        return gs.play(move, self.label)

    def printReasoningTrace(self):
        print 'Reasoning trace for ' + self.label + ': ' + str(self.history)

