A1 = [10, -7, 0; -3, 2, 6; 5, -1, 5];
A2 = [-73, 78, 24; 92, 66, 25; -80, 37, 10];


estimate1A1 = InverseEstimator1(A1);
estimate2A1 = InverseEstimator2(A1);
trueValueA1 = norm(inv(A1), 1);
estimate1CondA1 = norm(A1, 1) * estimate1A1;
estimate2CondA1 = norm(A1, 1) * estimate2A1;
trueCondA1 = cond(A1, 1);

estimate1A2 = InverseEstimator1(A2);
estimate2A2 = InverseEstimator2(A2);
trueValueA2 = norm(inv(A2), 1);
estimate1CondA2 = norm(A2, 1) * estimate1A2;
estimate2CondA2 = norm(A2, 1) * estimate2A2;
trueCondA2 = cond(A2, 1);

fprintf('A1 rel error method 1: %f\n', abs(trueCondA1 - estimate1CondA1) / trueCondA1);
fprintf('A1 rel error method 2: %f\n', abs(trueCondA1 - estimate2CondA1) / trueCondA1);

fprintf('A2 rel error method 1: %f\n', abs(trueCondA2 - estimate1CondA2) / trueCondA2);
fprintf('A2 rel error method 2: %f\n', abs(trueCondA2 - estimate2CondA2) / trueCondA2);