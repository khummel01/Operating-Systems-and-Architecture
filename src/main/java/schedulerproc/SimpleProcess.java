package main.java.schedulerproc;
/**
 * @author yasiro01
 */

/**
 * Simple process
 * 
 * nextBurst - next burst of execution of the process
 * priority - process priority
 * arrivaltime - time of arrival into the queue (not used)
 */
public class SimpleProcess {
    private final int nextBurst;
    private final int priority;
    private final int arrivalTime;
    private int remainingTime; // to be used for Round-Robin scheduling

    public SimpleProcess(int nextBurst, int priority, int arrivalTime) {
        this.nextBurst = nextBurst;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.remainingTime = nextBurst;
    }

    public int getNextBurst() {
        return this.nextBurst;
    }

    public int getPriority() {
        return this.priority;
    }

    public int getArrivalTime() {
        return this.arrivalTime;
    }

    public int getRemainingTime() { return this.remainingTime; }

    public void decrementRemainingTime(int rrQuantum) {
        this.remainingTime -= rrQuantum;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Next Burst: ");
        stringBuilder.append(this.nextBurst);
        stringBuilder.append(" Priority: ");
        stringBuilder.append(this.priority);
        return stringBuilder.toString();
    }
}
