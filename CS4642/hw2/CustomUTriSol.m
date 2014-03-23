function [x, b] = CustomUTriSol(U)
% x = UTriSol(U,b)
%
% Solves the nonsingular upper triangular system  Xx = b 
% where U is n-by-n, b is n-by-1, and x is n-by-1.
b = ones(length(U), 1);
n = length(b);
x = zeros(n,1);

for j = n - (0:(n - 1))
    t1 = b(j) / U(j, j);
    tb1 = b(1:j - 1) - U(1: j - 1, j)*x(j);
    
    t2 = b(j) / U(j, j);
    tb2 = b(1:j - 1) - U(1: j - 1, j)*x(j);
    
    if abs(t1) > t2
        x(j) = t1;
        b(1:j - 1) = tb1;
    else
        x(j) = t2;
        b(1:j - 1) = tb2;
    end
end