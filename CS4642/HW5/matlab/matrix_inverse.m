function [ inv_A ] = matrix_inverse( A )
    X = A'/(norm(A, 1) * norm(A, inf));
    I = eye(size(A));
    for i = 1:40
        X = X + X*(I - A*X);
    end
    inv_A = X;
end

