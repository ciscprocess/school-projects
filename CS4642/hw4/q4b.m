A = [2, 3, 2; 10, 3, 4; 3, 6, 1];
[a,b] = eig(A);
x0 = [0, 0, 1]';
vals = zeros(3);
vecs = zeros(3);
[eigenval_1, eigenvec_1] = powerIteration(A, x0);
vals(1, 1) = eigenval_1;
vecs(:, 1) = eigenvec_1;


I = eye(3);
alpha = -sign(eigenvec_1(1, 1))*norm(eigenvec_1(:, 1), 2) ;
v = eigenvec_1(:, 1) - alpha*I(:, 1);
H = I - 2*(v*v')/(v'*v);
A2 = H*A*H';

B = A2(2:end, 2:end);
[eigenval_2, eigenvec_B1] = powerIteration(B, [0;1]);
eigenvec_2 = inv(H)*[(A2(1, 2:end)*eigenvec_B1)/(eigenval_2 - eigenval_1); eigenvec_B1];
vals(2, 2) = eigenval_2;
vecs(:, 2) = eigenvec_2;

I = eye(2);
alpha2 = -sign(eigenvec_B1(1, 1))*norm(eigenvec_B1(:, 1), 2) ;
v2 = eigenvec_B1(:, 1) - alpha2*I(:, 1);
H2 = I - 2*(v2*v2')/(v2'*v2);
B2 = H2*B*H2';

C = B2(2:end, 2:end);
[eigenval_3, eigenvec_C1] = powerIteration(C, [1]);

% transform eigenvec_C1 to eigenvec_B2
eigenvec_B2 = inv(H2)*[(B2(1, 2:end)*eigenvec_C1)/(eigenval_3 - eigenval_2); eigenvec_C1];
eigenvec_3 = inv(H)*[(A2(1, 2:end)*eigenvec_B2)/(eigenval_3 - eigenval_1); eigenvec_B2];
vals(3, 3) = eigenval_3;
vecs(:, 3) = eigenvec_3;



