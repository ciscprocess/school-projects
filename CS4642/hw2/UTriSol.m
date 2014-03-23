function x = UTriSol(U,b)
% x = UTriSol(U,b)
%
% Solves the nonsingular upper triangular system  Xx = b 
% where U is n-by-n, b is n-by-1, and x is n-by-1.

n = length(b);
x = zeros(n,1);

for j = n - (0:(n - 1))
    x(j) = b(j) / U(j, j);
    b(1:j - 1) = b(1:j - 1) - U(1: j - 1, j)*x(j);
end