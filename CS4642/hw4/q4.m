A = [2, 3, 2; 10, 3, 4; 3, 6, 1];
x0 = [0, 0, 1]';
epsilon = 1e-10;
ratio = inf;
x = x0;
diff = inf;

while diff > epsilon
    xk = A*x;
    ratio = norm(xk, inf);
    xk = xk/norm(xk, inf);
    diff = abs(norm(x) - norm(xk));
    x = xk;
end

ratio
x

[a,b] = eig(A);