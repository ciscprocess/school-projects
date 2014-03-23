a = 100000;
n = 20;
r = 0.06;
p = 10000;

for i = 1:100
    f = p*((1+r)^n-1)/r - a*(1+r)^n;
    fp = (-1+(1+r)^n)/r;
    p = p - f/fp;
end

p