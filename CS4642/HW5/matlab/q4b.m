a = 100000;
p = 10000;
n = 20;
r = 0.05;

for i = 1:10000
    f = p*((1+r)^n-1)/r - a*(1+r)^n;
    fp = -a*n*(1+r)^(-1+n)+(p*(1+n*r*(1+r)^(-1+n)-(1+r)^n))/r^2;
    r = r - f/fp;
end

r