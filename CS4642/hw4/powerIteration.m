function [ ratio, x ] = powerIteration( A, x0 )
%POWERITERATION Summary of this function goes here
%   Detailed explanation goes here

epsilon = 1e-5;
ratio = inf;
x = x0;
diff = inf;
n = length(x0);
for i = 1:70
    xk = A*x;
    ratio = xk(n) / x(n);
    %xk = xk/norm(xk, inf);
    %diff = abs(norm(x) - norm(xk));
    x = xk;
end
x = x / norm(x, inf);
end

