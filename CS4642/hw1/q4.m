% solve, iteratively, the following recurrence relation:
% x_{k+1} = 2.25*x_k - 0.5*x_{k-1}

n = 60; % since we're working in double-precision
relation = ones(1, n);
relation(1) = 1/3;
relation(2) = 1/12;

for i = 3:n
    relation(i) = 2.25*relation(i - 1) - 0.5*relation(i - 2);
end

k = 1:n;
ground_relation = (4.^(1 - k)) / 3;

subplot(3, 1, 1);
plot(k, log(abs(ground_relation - relation)));
title('Error');
xlabel('k');
ylabel('log(error)');

subplot(3, 1, 2);
plot(k, log(relation));
title('Difference equation approximation');
xlabel('k');
ylabel('log(value)');

subplot(3, 1, 3);
plot(k, log(ground_relation));
title('Ground Truth');
xlabel('k');
ylabel('log(value)');