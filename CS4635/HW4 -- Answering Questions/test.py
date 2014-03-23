#-------------------------------------------------------------------------------
# Name:        test
# Purpose:     tests for functions
#
# Author:      Nathan
#
# Created:     06/09/2013
# Copyright:   (c) Nathan 2013
# Licence:     <your licence>
#-------------------------------------------------------------------------------

import game
import graphics

def testHasWon():
    gs = game.GameState()
    gs = gs.play((0, 2), 'n')
    gs = gs.play((1, 1), 'n')
    gs = gs.play((2, 0), 'n')
    gs = gs.play((0, 0), 'n')
    graphics.draw_board(gs)

    value = gs.has_won('n')

    if not value:
        print 'testHasWon failed!'

def main():
    testHasWon()

if __name__ == '__main__':
    main()