#-------------------------------------------------------------------------------
# Name:        ai
# Purpose:     pure AI routines
#
# Author:      Nathan
#
# Created:     06/09/2013
# Copyright:   (c) Nathan 2013
# Licence:     MIT
#-------------------------------------------------------------------------------
inf = float('inf')


class Path(object):
    def __init__(self, nodes, moves):
        self.states = nodes
        self.moves = moves
        self.value = None
        self.length = 1

    def extend(self, node):
        new = Path(self.states[:], self.moves[:])
        new.states.insert(0, node)
        #new.moves.insert(0, move)
        new.value = self.value
        new.length = self.length + 1
        return new

    def __repr__(self):
        string = ''
        for move in self.moves:
            string += str(move) + ' ->'
        string += ' end'
        return string


def get_tree_path(game_state, pick_function, player_label):
    reasoning_trace = {}

    def calculate_move(gs, pick, player, depth=0):
        """
        The Central Algorithm -- This is the general reasoning part of the AI, that uses
        constraints generated by the Agents to guide the game-state search.
        """
        winner = gs.get_winner()
        if not (winner is None):
            p = Path([gs], [])
            if winner == player:
                p.value = 1
            elif winner == 'tie':
                p.value = 0
            else:
                p.value = -1
            return p

        paths = []
        index = 0

        if 'Depth ' + str(depth) not in reasoning_trace:
            reasoning_trace['Depth ' + str(depth)] = {'depth': depth, 'pruned-edges': [], 'current-options': set()}

        for move in gs.legal_moves:
            current_player = gs.get_current_player()
            p = calculate_move(gs.play(move, current_player), pick, player, depth + 1)
            p.moves.insert(0, move)
            paths.append(p)
            chosen = pick(paths, len(gs.legal_moves) - (index + 1))
            if chosen:
                path = chosen.extend(gs)
                return path
            index += 1
        return None
    return calculate_move(game_state, pick_function, player_label, 0)

chosen_paths = []