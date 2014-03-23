#-------------------------------------------------------------------------------
# Name:        GameState
# Purpose:     Serves as the class that contains all necessary information for
#              the game at any given time
#
# Author:      Nathan
#
# Created:     14/10/2012
# Copyright:   (c) Nathan 2012
# Licence:     <your licence>
#-------------------------------------------------------------------------------
import copy
import Queue
import random

side = 3
win_length = 3


class GameState(object):
    def __init__(self, players):
        self.grid = [['_' for x in xrange(side)] for x in xrange(side)] # Grid of gametiles
        self.legal_moves = [(x, y) for x in xrange(side) for y in xrange(side)]
        self.players = players
        self.pturn = 0
        self.turn = 0

    def is_point_legal(self, point):
        point = tuple([p % side for p in point])
        return self.legal_moves.count(point) > 0

    def play(self, point, player):
        point = tuple([p % side for p in point])
        if self.legal_moves.count(point) == 0:
            print 'WARNING: An illegal move was attempted: ' + str(point)
            return self

        clone = GameState(self.players[:])
        clone.legal_moves = self.legal_moves[:]
        clone.grid = [[self.grid[y][x] for x in xrange(side)] for y in xrange(side)]
        clone.grid[point[0]][point[1]] = player
        clone.legal_moves.remove(point)
        clone.pturn = (self.pturn + 1) % len(self.players)
        clone.turn = self.turn + 1

        return clone

    def generate_dynamics(self, player):
        grid = [[[0, 0, 0, 0] for _ in xrange(side + 2)] for _ in xrange(side + 2)]
        for row in range(1, side + 1):
            for col in range(1, side + 1):
                if self.grid[row - 1][col - 1] == player:
                    grid[row][col][0] = grid[row - 1][col][0] + 1
                    grid[row][col][1] = grid[row][col - 1][1] + 1
                    grid[row][col][2] = grid[row - 1][col - 1][2] + 1
                    grid[row][col][3] = grid[row - 1][col + 1][3] + 1
                else:
                    grid[row][col][0] = 0
                    grid[row][col][1] = 0
                    grid[row][col][2] = 0
                    grid[row][col][3] = 0
        return grid

    def get_current_player(self):
        return self.players[self.pturn]

    def has_won(self, player):
        grid = self.generate_dynamics(player)
        for row in grid:
            for elem in row:
                if max(elem) >= win_length:
                    return True
        else:
            return False

    def get_winner(self):
        players = ['O', 'X']

        for player in players:
            if self.has_won(player):
                return player

        if len(self.legal_moves) == 0:
            return 'tie'

        return None

