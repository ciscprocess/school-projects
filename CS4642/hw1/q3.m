% get a reference to the function to be used for the derivative
% approximation
func = @tan;

% approximate the derivative using left finite difference
x = 1;
k = 0:16;
h = 10.^-k;
a_d_func = (func(x + h) - func(x)) ./ h;
r_d_func = (sec(x))^2;
o_d_func = (func(x + sqrt(eps)) - func(x)) ./ sqrt(eps);

subplot(2,1,1); 
plot(log(h)/log(10), log(abs(a_d_func - r_d_func))/log(10));

fprintf('Error of eps^2: %f \n', log(abs(o_d_func - r_d_func))/log(10));
title('Derivative approximation for the Tangent function.');
xlabel('log(h)');
ylabel('log(error)');


% % approximate the derivative using centered finite difference
x = 1;
k = 0:16;
h = 10.^-k;
a_d_func = (func(x + h) - func(x - h)) ./ (2*h);
r_d_func = (sec(x))^2;
o_d_func = (func(x + sqrt(eps)) - func(x - sqrt(eps))) ./ 2*sqrt(eps);

subplot(2,1,2);
plot(log(h)/log(10), log(abs(a_d_func - r_d_func))/log(10));

title('Derivative approximation for the Tangent function (center difference).');
xlabel('log(h)');
ylabel('log(error)');

fprintf('Error of eps^2 (center diff): %f \n', log(abs(o_d_func - r_d_func))/log(10));