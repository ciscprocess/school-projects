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


def minimaxPlayer(gs, agent, opponent, depth, beta):
    bestState = {'score': -inf, 'move': None}
    alpha = -inf
    v = gs.hasWon(agent.label)

    if v is True:
        return {'score': 1}

    if depth == depthLimit or len(gs.legalMoves) == 0:
        return {'score': 0}

    for move in gs.legalMoves:
        s = gs.play(move, agent.label)
        state = minimaxOpponent(s, agent, opponent, depth, alpha)

        if state['score'] > bestState['score']:
            bestState = {'score': state['score'], 'move': move}

        if state['score'] > alpha:
            alpha = state['score']

        if bestState['score'] > beta:
            break

    return bestState


def minimaxOpponent(gs, agent, opponent, depth, alpha):
    worstState = {'score': inf}
    beta = inf
    v = gs.hasWon(opponent.label)

    if v is True:
        return {'score': -1}

    for move in gs.legalMoves:
        s = gs.play(move, opponent.label)
        state = minimaxPlayer(s, agent, opponent, depth + 1, beta)

        if state['score'] < worstState['score']:
            worstState = {'score': state['score'], 'move': move}

        if state['score'] < beta:
            beta = state['score']

        if worstState['score'] < alpha:
            break

    return worstState
