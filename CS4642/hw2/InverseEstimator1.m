function [ nn ] = InverseEstimator1( A )
%INVERSEESTIMATOR1 Summary of this function goes here
%   Detailed explanation goes here
    [L, U] = lu(A);
    [v, ~] = CustomUTriSol(U');
    
    y = L' \ v;
    z = A \ y;
    nn = norm(z, 1) / norm(y, 1);
    

