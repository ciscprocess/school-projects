A = [1, 3; 1, 7];
[vec_t, val_t] = eigenvalue_newton(A)
[vec_r, val_r] = eig(A)

A = [1, 2, 3; 4, 5, 6; 7, 8, 9];
[vec_t, val_t] = eigenvalue_newton(A)
[vec_r, val_r] = eig(A)

A = rand(8);
[vec_t, val_t] = eigenvalue_newton(A)
[vec_r, val_r] = eig(A)