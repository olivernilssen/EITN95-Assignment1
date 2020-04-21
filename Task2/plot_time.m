fileA = fopen('customersA.m');
Ay = fscanf(fileA, '%f');
Ax = (0:0.1:99.9)';

fileB = fopen('customersB.m');
By = fscanf(fileB, '%f');
Bx = Ax;

plot(Ax, Ay, Bx,By, '.-')