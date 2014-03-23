% f(x) = x^(2n) - a^n = 0
% f'(x) = 2nx^(2n - 1)
% g(x) = x - (x^(2n) - a^n)/(2nx^(2n - 1))

x = 2;
a = 2;
n = 10;
gg = [];
gg(1) = x;
for k = 2:20
    f = x^(2*n) - a^n;
    fp = 2*n*x^(2*n - 1);
    x = x - f/fp;
    gg(k) = x;
end

plot(1:20, gg - sqrt(2));
title('Newton Iteration error e_k vs k for n = 10','FontSize',16)
xlabel('iteration k');
ylabel('error e_k');

