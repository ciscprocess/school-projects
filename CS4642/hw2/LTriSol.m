function x = LTriSol(L,b)
% x = LTriSol(L,b)
%
% Solves the nonsingular lower triangular system  Lx = b 
% where L is n-by-n, b is n-by-1, and x is n-by-1.

n = length(b);
x = zeros(n,1);
for j=1:n-1
   x(j) = b(j)/L(j,j);
   b(j+1:n) = b(j+1:n) - L(j+1:n,j)*x(j);
end
x(n) = b(n)/L(n,n);