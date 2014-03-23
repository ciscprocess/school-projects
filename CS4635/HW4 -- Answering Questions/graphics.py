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
win_length = game.win_length


def clear():
    """
    Cross-platform function that clears the screen
    """
    os.system(['clear','cls'][os.name == 'nt'])


def generate_board_string(game_state):
    nums = range(1, side + 1)
    letters = [chr(x + 64) for x in range(1, side + 1)]
    col_string = '   '  # Three Spaces
    for char in letters:
        col_string += char + ' '

    num_string = ''

    for i in range(0, side):
        space = ['  ', ' '][i >= 9]
        num_string += str(nums[i]) + space
        for ii in range(0, side):
            num_string += game_state.grid[i][ii] + ' '
        num_string += '\n'

    return col_string + '\n' + num_string


def draw_board(game_state):
    """
    Draws the state of a current game to the screen in ASCII characters
    @param game_state:
    """
    print generate_board_string(game_state)
