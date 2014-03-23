function [ Q, R ] = householder( A )
%HOUSEHOLDER Summary of this function goes here
%   Detailed explanation goes here
    [m, n] = size(A);
    I = eye(m, n);
    Q = I;
    for k = 1:n
        alpha = -sign(A(k, k))*norm(A(k:m, k), 2) ;
        v = [zeros(k - 1, 1); A(k:m, k)] - alpha*I(:, k);
        beta = v'*v;
        if beta == 0
            continue
        end
        H = I - 2*(v*v')/(v'*v);
        A = H*A;
        Q = Q*H';
    end
    R = A;
end

