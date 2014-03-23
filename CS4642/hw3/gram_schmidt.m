function [ Q, R ] = gram_schmidt( A )
%GRAM_SCHMIDT Summary of this function goes here
%   Detailed explanation goes here
    [~, n] = size(A);
    for k = 1:n
        Q(:, k) = A(:, k);
        for j = 1:(k - 1)
            R(j, k) = Q(:, j)' * A(:, k);
            Q(:, k) = Q(:, k) - R(j, k)*Q(:, j);
        end
        
        R(k, k) = norm(Q(:, k), 2);
        if R(k, k) == 0
            break
        end
        Q(:, k) = Q(:, k) / R(k, k);
    end

end

