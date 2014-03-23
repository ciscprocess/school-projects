#-------------------------------------------------------------------------------
# Name:        ontology
# Purpose:     This module defines the propositions and predicates for my game
#              playing language.
#
# Author:      Nathan
#
# Created:     20/09/2013
# Copyright:   Nathan 2013
# Licence:     Public Domain
#-------------------------------------------------------------------------------

# Internal Gamestate Globals
side = 3
_roles = []
_current_role = None
_current_context = [['_' for x in xrange(side)] for x in xrange(side)]
_variables = {}


# Variables / Propositions
def init_variable(v, domain):
    _variables[v] = set(domain)

def for_all(v, func):
    v = _variables[v]
    for d in v:
        func(d)

init_variable('r', ['X', 'O'])
init_variable('m', range(side))
init_variable('v', range(side))

# Context
def has_played(r, m, n):
    return _current_context[m][n] == r

def is_occupied(m, n):
    return _current_context[m][n] != '_'

# Defines a player role
def role(r):
    if not r in _roles:
        _roles += [r]
    if not _current_role:
        _current_role = r


# Play function
def move(r, p):
    if is_occupied(*p):
        raise StandardError('Attempting to play on an occupied square. This ' +
                            'should not happen. This is a bug.')
    else:
        _current_context[p[0], p[1]] = r





