customerWait = fopen('timespent.m');
wait = fscanf(customerWait, '%f');
x100 = (0:10:999)';
Nwait = 100;

workFinish = fopen('timefinished.m');
finish = fscanf(workFinish, '%f');
x1000 = (0:1:999)';
Nfinish = 1000;

plot (x100, wait, finish, x1000)

meanwait = mean(wait);
meanfinish = mean(finish);
stdwait = std(wait);
stdfinish = std(finish);

CI_wait = 1.96*stdwait/sqrt(Nwait);
CI_finish = 1.96*stdfinish/sqrt(Nfinish);

x = [-500:1:500];
xfinish = [0:.1:5000];
ywait = normpdf(x,meanwait,stdwait);
yfinish = normpdf(x,meanfinish,stdfinish);

% plot(x, ywait, x, yfinish);
