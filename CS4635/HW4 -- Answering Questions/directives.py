#-------------------------------------------------------------------------------
# Name:        directives
# Purpose:     I realized my code could be more faithful to my design, so I
#              started implementing some of the directives
#
# Author:      Nathan
#
# Created:     02/11/2013
# Copyright:   (c) Nathan 2013
# Licence:     <your licence>
#-------------------------------------------------------------------------------


def max_chain(context):
    pass


def pick_move(paths, player):
    chosen_path = None
    if player == 'self':  # it's this agent's turn
        chosen_path = max(paths, key=lambda p: p.value)
    else:  # it's the other agent's turn
        chosen_path = min(paths, key=lambda p: p.value)
    return chosen_path


def chain_value(n, player):
    multiplier = [-1, 1][player == 'self']
    if n == 3:
        return multiplier
    else:
        return 0



directives = {
    'Max-Chain': max_chain,
    'Pick-Move': pick_move,
    'Chain-Value': chain_value
}
