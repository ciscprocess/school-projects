% Set Options
options = optimset('MaxIter', 400, 'TolX', 0.000000000000000001);

% Optimize
[a, b, c] = fminunc(@(t)(mult(t, [-8.36; 10.77], [10.24; -5.53])), [1,1], options);