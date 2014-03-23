function [ Q, R ] = modified_gram_schmidt( A )
%   modified_gram_schmidt Summary of this function goes here
%   Detailed explanation goes here
    [~, n] = size(A);
    for k = 1:n
        R(k, k) = norm(A(:, k), 2);
        if R(k, k) == 0
            R = A;
            break
        end
        
        Q(:, k) = A(:, k) / R(k, k);
        for j = (k + 1):n
            R(k, j) = Q(:, k)'*A(:, j);
            A(:, j) = A(:, j) - R(k, j)*Q(:, k);
        end
    end
    R = A;
end
