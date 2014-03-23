__author__ = 'Nathan'

prompt = '>> '
import graphics


def extract_choice(answer_string):
    for i in range(10):
        index = answer_string.find(str(i))
        if index != -1:
            return str(i)
    return -1


def question_console(agent_x, agent_o):
    running = True
    while running:
        print 'Welcome to the questioning console!'
        print 'Which agent would you like to interrogate?'
        print '\t1.) The player \'X\' Agent (' + agent_x.name + ')'
        print '\t2.) The player \'O\' Agent (' + agent_o.name + ')'
        print '\t3.) Quit'

        choice = extract_choice(raw_input(prompt))
        while choice != '1' and choice != '2' and choice != '3':
            print 'Invalid input. Try again.'
            choice = extract_choice(raw_input(prompt))

        if choice == '1':
            question_console_player(agent_x)
        elif choice == '2':
            question_console_player(agent_o)
        else:
            running = False


def question_console_player(agent):
    running = True
    while running:
        print 'You are now interrogating ' + agent.name + ', playing as Player ' + agent.label
        print 'What would you like to ask of Player ' + agent.label + '?'
        print '\t1.) What knowledge are you aware of?'
        print '\t2.) Tell me about a specific turn.'
        print '\t3.) Return to previous menu'

        choice = extract_choice(raw_input(prompt))
        choices = ['1', '2', '3']
        while not choice in choices:
            print 'Invalid input. Try again.'
            choice = extract_choice(raw_input(prompt))
        if choice == '1':
            display_console_knowledge(agent)
        elif choice == '2':
            turn = prompt_console_turn()
            question_console_turn(agent, turn)
        elif choice == '3':
            running = False


def display_console_knowledge(agent):
    print agent.show_knowledge()


def prompt_console_turn():
    print 'Which Turn? (0 thru 8)'
    choice = extract_choice(raw_input(prompt))
    turns = [str(x) for x in range(9)]
    while not choice in turns:
        print 'Invalid input. Try again.'
        choice = extract_choice(raw_input(prompt))

    return choice


def question_console_turn(agent, turn):
    running = True
    while running:
        print 'You are now asking Player ' + agent.label + ' (' + agent.name + ')' + ' about turn ' + str(turn)
        print 'What about the turn would you like to ask?'
        print '\t1.) Where did you move on this turn?'
        print '\t2.) What was the Game-State at this turn?'
        print '\t3.) Why did you move where you did?'
        print '\t4.) Return to previous menu'

        choice = extract_choice(raw_input(prompt))
        choices = ['1', '2', '3', '4']
        while not choice in choices:
            print 'Invalid input. Try again.'
            choice = extract_choice(raw_input(prompt))

        if choice == '1':
            if agent.paths[int(turn)]:
                print 'In turn ' + str(turn) + ', I moved at ' + str(agent.paths[int(turn)].moves[0])
            else:
                print 'I didn\'t move this turn.'
        elif choice == '2':
            print_console_game_state(agent, turn)
        elif choice == '3':
            print agent.explain_move(int(turn))
        else:
            running = False


def print_console_game_state(agent, turn):
    path = agent.paths[int(turn)]
    if not path:
        print 'I wasn\'t paying attention that turn.'
    else:
        graphics.draw_board(path.states[0])