%% Generate Plot Surface
[X, Y] = meshgrid(-2:0.1:2);
Z = 2*X.^3 - 3*X.^2 - 6.*X.*Y.*(X-Y-1);

% Symbolic Function for fminsearch
f = @(x)2.*x(1).^3 - 3.*x(1).^2 - 6.*x(1).*x(2).*(x(1)-x(2)-1);

surf(X, Y, Z);
hold on;

%% Plot Critical Points
plot3(0, -1, f([0, -1]), 'bo')
hold on;
plot3(-1, -1, f([-1, -1]), 'bo')
hold on;
plot3(0, 0, f([0, 0]), 'bo')
hold on;
plot3(1, 0, f([1, 0]), 'bo')

%% Test fminsearch convergence

x1 = [0, 0];
x2 = [10, 10];
x3 = [-1, -1];
x4 = [0, -1];
x5 = [1, 1];


z1 = fminsearch(f, x1);
fprintf('starting point (%f, %f) settles to (%f, %f)\n', x1(1), x1(2), z1(1), z1(2));

z2 = fminsearch(f, x2);
fprintf('starting point (%f, %f) settles to (%f, %f)\n', x2(1), x2(2), z2(1), z2(2));

z3 = fminsearch(f, x3);
fprintf('starting point (%f, %f) settles to (%f, %f)\n', x3(1), x3(2), z3(1), z3(2));

z4 = fminsearch(f, x4);
fprintf('starting point (%f, %f) settles to (%f, %f)\n', x4(1), x4(2), z4(1), z4(2));

z5 = fminsearch(f, x5);
fprintf('starting point (%f, %f) settles to (%f, %f)\n', x5(1), x5(2), z5(1), z5(2));
