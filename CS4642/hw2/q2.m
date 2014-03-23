function [ x ] = q2( A, B, C, b )
    xprime = C \ b;
    b2 = 2*A*xprime + 2*A^2*b + xprime + A*b;
    x = B \ b2;
end