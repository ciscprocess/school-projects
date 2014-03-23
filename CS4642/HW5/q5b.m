A = [1, 2, 5; 4, 5, 6; 7, 8, 9];
A_t = matrix_inverse(A)
A_r = inv(A)
norm(A_t - A_r)


A = magic(5);
A_t = matrix_inverse(A)
A_r = inv(A)
norm(A_t - A_r)

A = magic(7);
b = [1, 2, 3, 4, 5, 6, 7]';
A_t = matrix_inverse(A)
x_t = A_t*b;
x_r = A\b;
norm(x_r - x_t)