#-------------------------------------------------------------------------------
# Name:        graphics
# Purpose:     text-based graphics for whatever the parent game to tic-tac-toe is
#
# Author:      Nathan
#
# Created:     14/10/2012
# Copyright:   (c) Nathan 2012
# Licence:     MIT
#-------------------------------------------------------------------------------
import os
import game

side = game.side
winlength = game.winlength

"""
    handy little cross-platform clear screen function (works with windows and
    UNIX-like operating systems)
"""
def clear():
    os.system(['clear','cls'][os.name == 'nt'])

def drawBoard(gamestate):
    nums = range(1, side + 1)
    letters = [chr(x + 64) for x in range(1, side + 1)]
    colstring = '   ' # Three Spaces
    for char in letters:
        colstring += char + ' '

    numstring = ''

    for i in range(0, side):
        space = ['  ', ' '][i >= 9]
        numstring += str(nums[i]) + space
        for ii in range(0, side):
            numstring += gamestate.grid[i][ii] + ' '
        numstring += '\n'


    print colstring

    print numstring

