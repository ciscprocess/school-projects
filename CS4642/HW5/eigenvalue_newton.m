function [ v, lambda ] = eigenvalue_newton( A )
%EIGENVALUE_NEWTON Summary of this function goes here
%   Detailed explanation goes here
    [~, n] = size(A);
    I = eye(n);
    x = rand(n, 1);
    x = x ./ norm(x);
    l = x'*A*x;
    for i = 1:100
        B = [A - l*I, -x; 2*x', 0];
        c = -[A*x - l*x; x'*x - 1];
        y = B \ c;
        x = x + y(1:end-1);
        l = l + y(end);
    end
    v = x;
    lambda = l;
end

