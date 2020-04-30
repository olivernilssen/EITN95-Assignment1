
public class Global{
	public static final int ARRIVAL = 1, READY = 2, MEASURE = 3, MEASUREC = 4, MEASURET = 5;
	public static double time = 0;
	public static final int RAND = 1, ROUNDROBIN = 2, SQF = 3; //RANDOM, ROUNDROBIN and SHORTEST QUEUE FIRST
	public static QS[] allQueues = new QS[5];
	public static int RR = 1;
	public static boolean startMeasuring = false;
}
