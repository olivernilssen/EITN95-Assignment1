%MEAN ARRIVAL 0.12 Customers
x = [1:.01:5];
%Random
C1_RAND = fopen('customers1-RAND.m');
y_C1_RAND = fscanf(C1_RAND, '%f');
C1_RAND_mean = mean(y_C1_RAND);
C1_RAND_std = std(y_C1_RAND);
yy_C1_RAND = normpdf(x,C1_RAND_mean,C1_RAND_std);

%RR
C1_RR = fopen('customers1-RR.m');
y_C1_RR = fscanf(C1_RR, '%f');
C1_RR_mean = mean(y_C1_RR);
C1_RR_std = std(y_C1_RR);
yy_C1_RR = normpdf(x,C1_RR_mean,C1_RR_std);

%SQF
C1_SQF= fopen('customers1-SQF.m');
y_C1_SQF = fscanf(C1_SQF, '%f');
C1_SQF_mean = mean(y_C1_SQF);
C1_SQF_std = std(y_C1_SQF);
yy_C1_SQF = normpdf(x,C1_SQF_mean,C1_SQF_std);

%MEAN ARRIVAL 0.11
%Random
C2_random = fopen('customers2-RAND.m');
y_C2_RAND = fscanf(C2_random, '%f');
C2_RAND_mean = mean(y_C2_RAND);
C2_RAND_std = std(y_C2_RAND);
yy_C2_RAND = normpdf(x,C2_RAND_mean,C2_RAND_std);

%RR
C2_RR = fopen('customers2-RR.m');
y_C2_RR = fscanf(C2_RR, '%f');
C2_RR_mean = mean(y_C2_RR);
C2_RR_std = std(y_C2_RR);

%SQF
C2_SQF= fopen('customers2-SQF.m');
y_C2_SQF = fscanf(C2_SQF, '%f');
C2_SQF_mean = mean(y_C2_SQF);
C2_SQF_std = std(y_C2_SQF);

%MEAN ARRIVAL 0.15
%Random
C3_random = fopen('customers3-RAND.m');
y_C3_RAND = fscanf(C3_random, '%f');
C3_RAND_mean = mean(y_C3_RAND);
C3_RAND_std = std(y_C3_RAND);

%RR
C3_RR = fopen('customers3-RR.m');
y_C3_RR = fscanf(C3_RR, '%f');
C3_RR_mean = mean(y_C3_RR);
C3_RR_std = std(y_C3_RR);

%SQF
C3_SQF= fopen('customers3-SQF.m');
y_C3_SQF = fscanf(C3_SQF, '%f');
C3_SQF_mean = mean(y_C3_SQF);
C3_SQF_std = std(y_C3_SQF);

%MEAN ARRIVAL 2.0
%Random
C4_random = fopen('customers4-RAND.m');
y_C4_RAND = fscanf(C4_random, '%f');
C4_RAND_mean = mean(y_C4_RAND);
C4_RAND_std = std(y_C4_RAND);

%RR
C4_RR = fopen('customers4-RR.m');
y_C4_RR = fscanf(C4_RR, '%f');
C4_RR_mean = mean(y_C4_RR);
C4_RR_std = std(y_C4_RR);

%SQF
C4_SQF= fopen('customers4-SQF.m');
y_C4_SQF = fscanf(C4_SQF, '%f');
C4_SQF_mean = mean(y_C4_SQF);
C4_SQF_std = std(y_C4_SQF);

plot(x, yy_C1_RAND, x, yy_C1_RR, x, yy_C1_SQF)
