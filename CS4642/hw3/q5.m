q1err = [];
q2err = [];
q3err = [];
for n = 2:12
    H = hilb(n);
    Q1 = gram_schmidt(H);
    Q2 = modified_gram_schmidt(H);
    Q3 = householder(H);
    q1err(n - 1) = - log(norm(eye(n) - Q1'*Q1))/log(10);
    q2err(n - 1) = - log(norm(eye(n) - Q2'*Q2))/log(10);
    q3err(n - 1) = - log(norm(eye(n) - Q3'*Q3))/log(10);
end

nn = 2:12;

plot(nn, q1err,'-gp',nn,q2err,'b-.d',nn,q3err,'r-.d');
title('Digits of Accuracy for `Q` Orthogonalization');
xlabel('size `n` of Hilbert matrix');
ylabel('Digits of Accuracy');
legend('Classical GS', 'Modified GS', 'Householder');
grid on