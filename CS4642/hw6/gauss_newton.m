function [ x ] = gauss_newton( t, y )
%GAUSS_NEWTON Summary of this function goes here
%   Detailed explanation goes here
    x = [1, 0]';
    for i = 1:100
        j1 = -exp(x(2)*t);
        j2 = -x(1)*t.*exp(x(2)*t);
        jacob = [j1, j2];
        func = x(1)*exp(x(2)*t);
        r = y - func;
        if rank(jacob) < 2
            disp('Hi');
            break
        end
        s = jacob \ -r;
        x = x + s;
    end
end