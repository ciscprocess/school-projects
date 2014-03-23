#-------------------------------------------------------------------------------
# Name:        ai
# Purpose:     pure AI routines
#
# Author:      Nathan
#
# Created:     06/09/2013
# Copyright:   (c) Nathan 2013
# Licence:     MIT
#-------------------------------------------------------------------------------
inf = float('inf')
depthLimit = 8
path = []

def naiveHeuristic(gs, player):
    pass

def minimaxPlayer(gs, player, opponent, depth, beta):
    bestState = {'score': -inf, 'move': None, 'path': ()}
    alpha = -inf
    v = gs.hasWon(player)

    if v is True:
        return {'score': 1, 'path': ()}

    if depth == depthLimit or len(gs.legalMoves) == 0:
        return {'score': 0, 'path': ()}

    for move in gs.legalMoves:
        s = gs.play(move, player)
        state = minimaxOpponent(s, player, opponent, depth, alpha)

        if state['score'] > bestState['score']:
            pathinfo = ({'move': move, 'score': state['score'], 'agent': 'player'},) + state['path']
            bestState = {'score': state['score'], 'move': move, 'path': pathinfo}

        if state['score'] > alpha:
            alpha = state['score']

        if bestState['score'] > beta:
            break

    return bestState


def minimaxOpponent(gs, agent, opponent, depth, alpha):
    worstState = {'score': inf, 'path': ()}
    beta = inf
    v = gs.hasWon(opponent)

    if v is True:
        return {'score': -1, 'path': ()}

    for move in gs.legalMoves:
        s = gs.play(move, opponent)
        state = minimaxPlayer(s, agent, opponent, depth + 1, beta)

        if state['score'] < worstState['score']:
            pathinfo = ({'move': move, 'score': state['score'], 'agent': 'opponent'},) + state['path']
            worstState = {'score': state['score'], 'move': move, 'path': pathinfo}

        if state['score'] < beta:
            beta = state['score']

        if worstState['score'] < alpha:
            break

    return worstState
