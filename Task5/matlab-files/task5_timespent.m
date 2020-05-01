%MEAN ARRIVAL 0.12 TIME IN SYSTEM
x = [15:.1:75];
%Random
T1_RAND = fopen('timeinQ1-RAND.m');
y_T1_RAND = fscanf(T1_RAND, '%f');
T1_RAND_mean = mean(y_T1_RAND);
T1_RAND_std = std(y_T1_RAND);

%RR
T1_RR = fopen('timeinQ1-RR.m');
y_T1_RR = fscanf(T1_RR, '%f');
T1_RR_mean = mean(y_T1_RR);
T1_RR_std = std(y_T1_RR);
[n1rr,p1rr] = size(y_T1_RR);
x_T1_RR = 1:n1rr;

plot(x_T1_RR, y_T1_RR, x_T1_RAND, y_T1_RAND)

%SQF
T1_SQF = fopen('timeinQ1-SQF.m');
y_T1_SQF = fscanf(T1_SQF, '%f');
T1_SQF_mean = mean(y_T1_SQF);
T1_SQF_std = std(y_T1_SQF);

%MEAN ARRIVAL 0.11
%Random
T2_RAND = fopen('timeinQ2-RAND.m');
y_T2_RAND = fscanf(T2_RAND, '%f');
T2_RAND_mean = mean(y_T2_RAND);
T2_RAND_std = std(y_T2_RAND);

%RR
T2_RR = fopen('timeinQ2-RR.m');
y_T2_RR = fscanf(T2_RR, '%f');
T2_RR_mean = mean(y_T2_RR);
T2_RR_std = std(y_T2_RR);


%SQF
T2_SQF= fopen('timeinQ2-SQF.m');
y_T2_SQF = fscanf(T2_SQF, '%f');
T2_SQF_mean = mean(y_T2_SQF);
T2_SQF_std = std(y_T2_SQF);


%MEAN ARRIVAL 0.15
%Random
T3_RAND = fopen('timeinQ3-RAND.m');
y_T3_RAND = fscanf(T3_RAND, '%f');
T3_RAND_mean = mean(y_T3_RAND);
T3_RAND_std = std(y_T3_RAND);


%RR
T3_RR = fopen('timeinQ3-RR.m');
y_T3_RR = fscanf(T3_RR, '%f');
T3_RR_mean = mean(y_T3_RR);
T3_RR_std = std(y_T3_RR);

%SQF
T3_SQF= fopen('timeinQ3-SQF.m');
y_T3_SQF = fscanf(T3_SQF, '%f');
T3_SQF_mean = mean(y_T3_SQF);
T3_SQF_std = std(y_T3_SQF);

%MEAN ARRIVAL 2.0
%Random
T4_RAND = fopen('timeinQ4-RAND.m');
y_T4_RAND = fscanf(T4_RAND, '%f');
T4_RAND_mean = mean(y_T4_RAND);
T4_RAND_std = std(y_T4_RAND);

%RR
T4_RR = fopen('timeinQ4-RR.m');
y_T4_RR = fscanf(T4_RR, '%f');
T4_RR_mean = mean(y_T4_RR);
T4_RR_std = std(y_T4_RR);

%SQF
T4_SQF= fopen('timeinQ4-SQF.m');
y_T4_SQF = fscanf(T4_SQF, '%f');
T4_SQF_mean = mean(y_T4_SQF);
T4_SQF_std = std(y_T4_SQF);

