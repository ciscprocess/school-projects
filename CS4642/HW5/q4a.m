n = 10;
p = 10000;
r = 0.06;
a = 100000;
for i = 1:10000
    f = p*((1+r)^n-1)/r - a*(1+r)^n;
    fp = ((1+r)^n*(p-a*r)*log(1+r))/r;
    n = n - f/fp;
end

n