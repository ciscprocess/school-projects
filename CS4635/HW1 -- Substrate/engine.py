#-------------------------------------------------------------------------------
# Name:        module1
# Purpose:
#
# Author:      Nathan
#
# Created:     02/09/2013
# Copyright:   (c) Nathan 2013
# Licence:     <your licence>
#-------------------------------------------------------------------------------

import game
import graphics
import agents

'''
    DEFAULTS:
    X IS NAIVE
    O IS THOUGHTFUL
'''

def main():
    X1, O1 = 0, 0
    X2, O2 = 0, 0
    X3, O3 = 0, 0
    trials = 10
    # Naive vs Naive
    print 'Beginning Naive vs Naive Trials!'
    for _ in xrange(trials):
        gs = game.GameState()
        graphics.drawBoard(gs)

        stupid = agents.NaiveAgent()
        stupidO = agents.NaiveAgent('O')

        tt = game.TurnTable()
        tt.addPlayer(stupid)
        tt.addPlayer(stupidO)

        winner = gs.getWinner()
        while not winner:
            player = tt.getActivePlayer()
            gs = player.play(gs)
            graphics.drawBoard(gs)

            winner = gs.getWinner()

        if winner == 'X':
            X1 += 1
        else:
            O1 += 1
    # Naive vs Thoughtful
    print 'Beginning Naive vs Thoughtful Trials!'
    for _ in xrange(trials):
        gs = game.GameState()
        graphics.drawBoard(gs)

        stupid = agents.NaiveAgent()
        thoughtful = agents.ThoughtfulAgent()

        tt = game.TurnTable()
        tt.addPlayer(stupid)
        tt.addPlayer(thoughtful)

        winner = gs.getWinner()
        while not winner:
            player = tt.getActivePlayer()
            gs = player.play(gs)
            graphics.drawBoard(gs)

            winner = gs.getWinner()

        if winner == 'X':
            X2 += 1
        else:
            O2 += 1

    # Thoughtful vs Thoughtful
    print 'Beginning Thoughtful vs Thoughtful Trials!'
    for _ in xrange(trials):
        gs = game.GameState()
        graphics.drawBoard(gs)

        thoughtfulO = agents.ThoughtfulAgent()
        thoughtfulX = agents.ThoughtfulAgent('X')

        tt = game.TurnTable()
        tt.addPlayer(thoughtfulX)
        tt.addPlayer(thoughtfulO)

        winner = gs.getWinner()
        while not winner:
            player = tt.getActivePlayer()
            gs = player.play(gs)
            graphics.drawBoard(gs)

            winner = gs.getWinner()

        if winner == 'X':
            X3 += 1
        else:
            O3 += 1

    print 'The Trials are over.'
    print 'Summarizing Results '
    print 'Naive vs. Naive: '
    print '\tX Wins: ' + str(X1)
    print '\tO Wins: ' + str(O1)

    print 'Naive vs. Thoughtful: '
    print '\tX Wins: ' + str(X2)
    print '\tO Wins: ' + str(O2)

    print 'Thoughtful vs. Thoughtful: '
    print '\tX Wins: ' + str(X3)
    print '\tO Wins: ' + str(O3)





if __name__ == '__main__':
    main()
