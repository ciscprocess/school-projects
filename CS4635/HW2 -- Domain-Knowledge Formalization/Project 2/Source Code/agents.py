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
        self.history = []

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

        best = 0
        point = random.choice(gs.legalMoves)
        entry = {'point': point, 'score': best, 'random': True, 'turn': gs.turn}

        for move in gs.legalMoves:
            t = gs.play(point, self.label)
            score = self.scoreState(gs)
            if score > best:
                best = score
                point = move
                entry['random'] = False
                entry['point'] = move
                entry['score'] = score
            elif score == best:
                entry['random'] = True

        self.history.append(entry)
        return gs.play(point, self.label)

    def printReasoningTrace(self):
        for item in self.history:
            s = '[' + self.label + ']: '
            s += 'In turn ' + str(item['turn']) + ', I played at ' + str(item['point'])
            s += ' because it had a heuristic score of ' + str(item['score'])
            if item['random']:
                s += ', although it was randomly chosen from among ties.'
            else:
                s += ', the absolute best of all the legal moves that turn.'

            print s


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

        state = ai.minimaxPlayer(gs, self.label, gs.getOpponent(self.label), 0, ai.inf)
        move = state['move']
        path = state['path']
        self.history[gs.turn] = path
        return gs.play(move, self.label)

    def printReasoningTrace(self):
        for key in self.history:
            path =  self.history[key]
            s = '[' + self.label + ']: '
            s += 'In turn ' + str(key) + ', I chose to play at ' + str(path[0]['move'])
            s += ' because '
            for i, p in enumerate(path[1:]):
                if p['agent'] == 'opponent':
                    s += 'a smart opponent would have played at ' + str(p['move'])
                    s += ' with a score of ' + str(p['score'])
                    if i < len(path[1:]) - 1:
                        s += ', in response to '
                else:
                    s += 'my ideal play at ' + str(p['move']) + '. '
                    s += 'I moved there because '
            print s
