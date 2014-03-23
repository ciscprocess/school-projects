function [ nn ] = InverseEstimator2( A )
%INVERSEESTIMATOR1 Summary of this function goes here
%   Detailed explanation goes here
    n = length(A);
    best = 0;
    for i = 1:8
        y = rand(n, 1);
        z = A \ y;
        t = norm(z, 1) / norm(y, 1);
        if t > best
            best = t;
        end
    end
    
    nn = best;
    

