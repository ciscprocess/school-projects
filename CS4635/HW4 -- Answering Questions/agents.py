#-------------------------------------------------------------------------------
# Name:        agents
# Purpose:     provides classes for the tic-tac-toe playing agents
#
# Author:      Nathan
#
# Created:     02/09/2013
# Copyright:   (c) Nathan 2013
# Licence:     MIT
#-------------------------------------------------------------------------------
import random
import ai
from directives import directives


class Agent(object):
    def __init__(self):
        self.label = '-'
        self.name = 'NoAgent'
        self.history = {}
        self.paths = [None] * 9

    def move_simulation_comparison(self, real_turn, simulated_turn):
        """
        Returns whether the agent correctly predicted the opponent's move at turn @real_turn in turn @simulated_turn
        @param real_turn: the turn in the actual game
        @param simulated_turn: the turn in which real_turn was simulated
        @type real_turn: int
        @type simulated_turn: int
        @raise StandardError
        """
        if simulated_turn > real_turn:
            raise StandardError('Invalid simulated_turn specified in move_simulation_comparison.')
        sim_path = self.paths[simulated_turn]
        sim_move = sim_path.moves[simulated_turn - real_turn]
        real_move = self.paths[real_turn].moves[0]

        return sim_move == real_move

    def warning(self, message):
        print self.name + ' [WARN]: ' + message


class NaiveAgent(Agent):
    """
    NaiveAgent always tries to win without regard to the other player
    """
    def __init__(self, label):
        Agent.__init__(self)
        self.label = label
        self.name = 'NaiveAgent'

    def play(self, gs):
        """
        @type gs: game.GameState
        @return: @rtype:
        """
        if gs.get_current_player() != self.label:
            return None

        random.shuffle(gs.legal_moves)

        def choice(paths, moves):
            chosen_path = max(paths, key=lambda p: p.value)

            if chosen_path.value <= 0 < moves:
                return None
            return chosen_path

        path = ai.get_tree_path(gs, choice, self.label)
        self.paths[gs.turn] = path
        point = path.moves[0]
        return gs.play(point, self.label)

    def print_reasoning_trace(self):
        print 'Reasoning trace for ' + self.label + ': ' + str(self.history)

    def explain_move(self, turn):
        c_player = 0
        path = self.paths[turn]
        if not path:
            return 'I didn\'t move in turn ' + str(turn) + '. '
        string = 'In turn ' + str(turn) + ', I moved at ' + str(path.moves[0]) + '. '

        for move in path.moves[1:]:
            if c_player == 1:
                string += 'I moved there because it lead to a game-state, assuming the opponent moves at '
                string += str(move) + ', that leads to a maximum terminal state. '
            else:
                string += 'To move further along this path to a maximum terminal state, I would move at ' + str(move)
                string += '. '
            c_player = (c_player + 1) % 2
        return string

    def show_knowledge(self):
        return 'I am aware of the directives Max-Chain and Chain-Value. I use them to score terminal Game-States.'

class ThoughtfulAgent(Agent):
    """
    ThoughtfulAgent uses Minimax to make its moves
    """
    def __init__(self, label):
        Agent.__init__(self)
        self.label = label
        self.name = 'ThoughtfulAgent'

    def play(self, gs):
        """
        @type gs: game.GameState
        @return: @rtype:
        """
        if gs.get_current_player() != self.label:
            return None

        random.shuffle(gs.legal_moves)

        def choice(paths, moves):
            if moves == 0:
                player = 'opponent'
                pick = directives['Pick-Move']
                if paths[0].states[0].get_current_player() != self.label:
                    player = 'self'
                return pick(paths, player)
            else:
                return None

        path = ai.get_tree_path(gs, choice, self.label)
        self.paths[gs.turn] = path
        point = path.moves[0]
        return gs.play(point, self.label)

    def print_reasoning_trace(self):
        print 'Reasoning trace for ' + self.label + ': ' + str(self.history)

    def explain_move(self, turn):
        c_player = 1
        path = self.paths[turn]
        if not path:
            return 'I didn\'t move in turn ' + str(turn) + '. '
        string = 'In turn ' + str(turn) + ', I moved at ' + str(path.moves[0]) + '. '
        for move in path.moves[1:len(path.moves) - 1]:
            if c_player == 0:
                string += str(move) + '. '
            else:
                string += 'I moved there because my opponent, '
                string += 'would have moved at ' + str(move) + ', as defined by the Pick-Move directive, ' \
                                                               'which returns the move an optimal opponent would make.'
                string += '(S)he would have moved there in response to my ideal move at '
            c_player = (c_player + 1) % 2

        if c_player == 1:
            string += 'I would have moved there because my opponent, whom I assumed to be optimal, '
            string += 'would have moved at ' + str(path.moves[len(path.moves) - 1])
            string += '. (S)he would have moved there because '
            string += 'it was the best terminal for h(er/im) given the game at that time.'
        else:
            string += str(path.moves[len(path.moves) - 1]) + '. I would have moved there because it was the best '
            string += 'terminal state for me given the partially completed game.'
        return string

    def show_knowledge(self):
        string = 'I am aware of the directives Max-Chain, Chain-Value, and Pick-Move, and I use Pick-Move to better '
        string += 'constrain my reasoning.'
        return string