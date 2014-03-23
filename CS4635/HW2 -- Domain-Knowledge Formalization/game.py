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
winlength = 3

class GameState(object):
    def __init__(self, players):
        self.grid = [['_' for x in xrange(side)] for x in xrange(side)] # Grid of gametiles
        self.legalMoves = [(x, y) for x in xrange(side) for y in xrange(side)]
        self.players = players
        self.pturn = 0
        self.turn = 0

    def isPointLegal(self, point):
        point = tuple([p % side for p in point])
        return self.legalMoves.count(point) > 0

    def play(self, point, player):
        point = tuple([p % side for p in point])
        if self.legalMoves.count(point) == 0:
            print 'WARNING: An illegal move was attempted: ' + str(point)
            return self

        clone = GameState(self.players[:])
        clone.legalMoves = self.legalMoves[:]
        clone.grid = [[self.grid[y][x] for x in xrange(side)] for y in xrange(side)]
        clone.grid[point[0]][point[1]] = player
        clone.legalMoves.remove(point)
        clone.pturn = (self.pturn + 1) % len(self.players)
        clone.turn = self.turn + 1

        return clone

    def generateDynamics(self, player):
        dynagrid = [[[0, 0, 0, 0] for x in xrange(side + 2)] for x in xrange(side + 2)]
        for row in range(1, side + 1):
            for col in range(1, side + 1):
                if self.grid[row - 1][col - 1] == player:
                    dynagrid[row][col][0] = dynagrid[row - 1][col][0] + 1
                    dynagrid[row][col][1] = dynagrid[row][col - 1][1] + 1
                    dynagrid[row][col][2] = dynagrid[row - 1][col - 1][2] + 1
                    dynagrid[row][col][3] = dynagrid[row - 1][col + 1][3] + 1
                else:
                    dynagrid[row][col][0] = 0
                    dynagrid[row][col][1] = 0
                    dynagrid[row][col][2] = 0
                    dynagrid[row][col][3] = 0
        return dynagrid

    def getCurrentPlayer(self):
        return self.players[self.pturn]

    def getOpponent(self, player):
        if player == 'X':
            return 'O'
        else:
            return 'X'

    def hasWon(self, player):
        dynagrid = self.generateDynamics(player)
        for row in dynagrid:
            for elem in row:
                if max(elem) >= winlength:
                    return True
        else:
            return False

    def getWinner(self):
        players = ['O', 'X']

        for player in players:
            if self.hasWon(player):
                return player

        if len(self.legalMoves) == 0:
            return 'tie'

        return None

