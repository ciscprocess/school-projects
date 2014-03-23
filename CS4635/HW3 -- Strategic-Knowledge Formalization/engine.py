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


def main():
    print 'Beginning Naive v. Naive'
    gs = game.GameState(['X', 'O'])
    graphics.drawBoard(gs)

    X = agents.NaiveAgent('X')
    O = agents.NaiveAgent('O')

    while not gs.getWinner():
        gs = X.play(gs) or O.play(gs)
        graphics.drawBoard(gs)

    X.printReasoningTrace()
    print ''
    O.printReasoningTrace()
    print ''
    print 'Player ' + gs.getWinner() + ' Wins!!!'

    print 'Beginning Naive v. Thoughtful'
    gs = game.GameState(['X', 'O'])
    graphics.drawBoard(gs)

    X = agents.NaiveAgent('X')
    O = agents.ThoughtfulAgent('O')

    while not gs.getWinner():
        gs = X.play(gs) or O.play(gs)
        graphics.drawBoard(gs)

    X.printReasoningTrace()
    print ''
    O.printReasoningTrace()
    print ''
    print 'Player ' + gs.getWinner() + ' Wins!!!'


    print 'Beginning Thoughtful v. Thoughtful'
    gs = game.GameState(['X', 'O'])
    graphics.drawBoard(gs)

    X = agents.ThoughtfulAgent('X')
    O = agents.ThoughtfulAgent('O')

    while not gs.getWinner():
        gs = X.play(gs) or O.play(gs)
        graphics.drawBoard(gs)

    X.printReasoningTrace()
    print ''
    O.printReasoningTrace()
    print ''
    print 'Player ' + gs.getWinner() + ' Wins!!!'





if __name__ == '__main__':
    main()
