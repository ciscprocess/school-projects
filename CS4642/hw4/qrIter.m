function [ B ] = qrIter(A)
%QRITER Summary of this function goes here
%   Detailed explanation goes here
    epsilon = 1e-6;
    B = A;
    [m, n] = size(B);
    diff = inf;
    while diff > epsilon
       sigma = B(n, n);
       [Q, R] = qr(B - sigma*eye(m, n));
       B_temp = R*Q + sigma*eye(m, n);
       diff = norm(B_temp - B, 'fro');
       B = B_temp;
    end
    
    %B = eye(m, n) .* B;
end

