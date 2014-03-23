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
tempTrace = {}
def getMove(gs, player, constraints, optimizations):
    global tempTrace
    tempTrace = {}
    v = getTreePath(gs, player, player, (), constraints, optimizations, 0)
    return v['path'][0], tempTrace

# The Central Algorithm:
def getTreePath(gs, originalPlayer, player, path, constraints, optimizations, depth):
    if len(gs.legalMoves) == 0:
        condition = lambda gs, p: gs.hasWon(p)
        if condition(gs, originalPlayer) == True:
            return {'path': path, 'short': optimizations['short']}
        else:
            return {'path': path, 'short': False}
    val = None
    for move in gs.legalMoves:
        nextPlayer = None
        if not depth in tempTrace:
            tempTrace[depth] = {'depth': depth, 'pruned-edges': set(), 'current-options': set()}

        tempTrace[depth]['current-options'].add(move)

        if player == 'X':
            nextPlayer = 'O'
        else:
            nextPlayer = 'X'

        val = getTreePath(gs.play(move, player), player, nextPlayer, path + (move,), constraints, optimizations, depth + 1)

        if val['short']:
            for move2 in gs.legalMoves:
                if move2 != move:
                    tempTrace[depth]['pruned-edges'].add(move2)
            return val

    return val



def minimaxPlayer(gs, player, opponent, depth):
    bestState = {'score': -inf, 'move': None, 'path': ()}
    alpha = -inf
    v = gs.hasWon(player)

    if v is True:
        return {'score': 1, 'path': ()}

    if depth == depthLimit or len(gs.legalMoves) == 0:
        return {'score': 0, 'path': ()}

    for move in gs.legalMoves:
        s = gs.play(move, player)
        state = minimaxOpponent(s, player, opponent, depth)

        if state['score'] > bestState['score']:
            pruned = gs.legalMoves[:]
            pruned.remove(move)
            pathinfo = ({'depth': depth, 'current-options': gs.legalMoves, 'pruned-edges': pruned},) + state['path']
            bestState = {'score': state['score'], 'move': move, 'path': pathinfo}

    return bestState


def minimaxOpponent(gs, agent, opponent, depth):
    worstState = {'score': inf, 'path': ()}
    beta = inf
    v = gs.hasWon(opponent)

    if v is True:
        return {'score': -1, 'path': ()}

    for move in gs.legalMoves:
        s = gs.play(move, opponent)
        state = minimaxPlayer(s, agent, opponent, depth + 1)

        if state['score'] < worstState['score']:
            pruned = gs.legalMoves[:]
            pruned.remove(move)
            pathinfo = ({'depth': depth + 1, 'current-options': gs.legalMoves, 'pruned-edges': pruned},) + state['path']
            worstState = {'score': state['score'], 'move': move, 'path': pathinfo}

    return worstState
