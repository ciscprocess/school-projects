function [ err, grad ] = mult( rolled, from, target )
%MULT Summary of this function goes here
%   Detailed explanation goes here
    theta = rolled(1);
    sf = rolled(2);
    R = [cos(theta), -sin(theta); sin(theta), cos(theta)];
    S = sf * eye(2);
    T = R*S;
    to = T*from;
    err = norm(abs(target - to))
    grad = target - to;
end

