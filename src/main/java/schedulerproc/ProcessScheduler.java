package main.java.schedulerproc;
/**
 * @author yasiro01
 */
import java.util.*;

/**
 * Process scheduler
 * 
 * readyQueue is a list of processes ready for execution
 * rrQuantum is the time quantum used by round-robin algorithm
 * add() and clear() are wrappers around ArrayList methods
 */
public class ProcessScheduler {
    private final ArrayList<SimpleProcess> readyQueue;
    private final int rrQuantum;


    public ProcessScheduler() {
        this.readyQueue = new ArrayList<>();
        this.rrQuantum = 4;
    }

    public void add(SimpleProcess newProcess) {
        this.readyQueue.add(newProcess);
    }

    public void clear() {
        this.readyQueue.clear();
    }


    public double getAvgWaitingTime(){
        ArrayList<Integer> waitTimes = new ArrayList<>();
        waitTimes.add(0);
        int lastProcessBurstTime = 0;
        for (int i=0; i<this.readyQueue.size(); i++) {
            waitTimes.add(waitTimes.get(waitTimes.size()-1)+lastProcessBurstTime);
            lastProcessBurstTime = this.readyQueue.get(i).getNextBurst();
        }
        return (double) waitTimes.stream().mapToInt(i -> i).sum()/this.readyQueue.size();
    }

    Comparator<SimpleProcess> compareByBurst = new Comparator<SimpleProcess>() {
        public int compare(SimpleProcess simpleProcess1, SimpleProcess simpleProcess2) {
            return Integer.compare(simpleProcess1.getNextBurst(), simpleProcess2.getNextBurst());
        }
    };

    Comparator<SimpleProcess> compareByPriority = new Comparator<SimpleProcess>() {
        public int compare(SimpleProcess simpleProcess1, SimpleProcess simpleProcess2) {
            return Integer.compare(simpleProcess1.getPriority(), simpleProcess2.getPriority());
        }
    };

    /**
     * FCFS scheduling algorithm implementation
     * 
     * @return average waiting time for all processes
     */
    public double useFirstComeFirstServe() {
        return getAvgWaitingTime();
    }

    /**
     * SJF scheduling algorithm implementation
     * 
     * @return average waiting time for all processes
     */
    public double useShortestJobFirst() {
        Collections.sort(this.readyQueue, compareByBurst);
        return getAvgWaitingTime();
    }

    /**
     * Priority scheduling algorithm implementation
     * 
     * @return average waiting time for all processes
     */
    public double usePriorityScheduling() {
        Collections.sort(this.readyQueue, compareByPriority);
        return getAvgWaitingTime();
    }

    /**
     * Round-Robin scheduling algorithm implementation
     * 
     * @return average waiting time for all processes
     */
    public double useRoundRobin() {
        int numProcess = this.readyQueue.size();

        HashMap<SimpleProcess, Integer> processWaitTimes = new HashMap<>();
        HashMap<SimpleProcess, Integer> leftOffTime = new HashMap<>();

        int timePassed = 0;
        int listIdx = 0;
        boolean done = false;

        while (!done) {
            // Get next process
            SimpleProcess currentProcess = this.readyQueue.get(listIdx);

            // Update the wait time of this process
            if (!processWaitTimes.containsKey(currentProcess)) {
                processWaitTimes.put(currentProcess, timePassed);
            } else {
                // We've seen this process before, we need to calculate the difference between when we last saw it and now
                processWaitTimes.put(currentProcess, processWaitTimes.get(currentProcess)+(timePassed-leftOffTime.get(currentProcess)));
            }

            // Update the time when the next process will start
            if (currentProcess.getRemainingTime() <= this.rrQuantum) {
                timePassed += currentProcess.getRemainingTime();
            } else {
                currentProcess.decrementRemainingTime(this.rrQuantum);
                timePassed += this.rrQuantum;
                this.readyQueue.add(currentProcess);
            }

            // Remember when this process finished
            leftOffTime.put(currentProcess, timePassed);

            // We're done once we've reached the end of the list
            if (listIdx == this.readyQueue.size()-1) {
                done = true;
            }
            listIdx++;
        }
        // Sum all the wait times
        float sum = 0;
        for (int waitTime : processWaitTimes.values()) {
            sum += waitTime;
        }
        return (double) sum/numProcess;
    }
}
