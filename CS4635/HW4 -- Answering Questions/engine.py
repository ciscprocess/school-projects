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
import questioning as q


def main():
    x, o = play_game(agents.NaiveAgent, agents.ThoughtfulAgent)
    q.question_console(x, o)


def play_game(agent_x, agent_o):
    gs = game.GameState(['X', 'O'])

    X = agent_x('X')
    O = agent_o('O')

    print 'Beginning ' + X.name + ' v. ' + O.name

    graphics.draw_board(gs)
    while not gs.get_winner():
        gs = X.play(gs) or O.play(gs)
        graphics.draw_board(gs)

    print 'Player ' + gs.get_winner() + ' Wins!!!'
    return X, O

if __name__ == '__main__':
    main()
