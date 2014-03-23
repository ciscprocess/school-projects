t = 0.5:0.5:4;
%t = 0:1:3;
t = t';
%y = [2, 0.7, 0.3, 0.1]';
y = [6.8, 3, 1.5, 0.75, 0.48, 0.25, 0.2, 0.15]';
fprintf('Gauss-Newton iteration result!\n');
x1 = gauss_newton(t, y)

A = [ones(8, 1), t];
xp = A \ log(y);
x2 = xp;
x2(1) = exp(x2(1));
r2 = log(y) - (log(x2(1)) + x2(2)*t);
err = 0.5*(r2'*r2);

fprintf('Least-squares transformation result!\n');
x2

fprintf('Difference between x1, the vector x calculated using Newton-Gauss, and x2 the vector from Linear Least Squares\n');
diff = x1 - x2