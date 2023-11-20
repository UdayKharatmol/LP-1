import java.util.LinkedList;
import java.util.Queue;

class Process {
    private String name;
    private int arrivalTime;
    private int burstTime;
    private int remainingTime;

    public Process(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }

    public String getName() {
        return name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
}

class Scheduler {
    public static void FCFS(Queue<Process> processes) {
        System.out.println("FCFS Scheduling:");

        int currentTime = 0;

        while (!processes.isEmpty()) {
            Process currentProcess = processes.poll();
            System.out.println("Executing process: " + currentProcess.getName() +
                    " (Arrival Time: " + currentProcess.getArrivalTime() +
                    ", Burst Time: " + currentProcess.getBurstTime() + ")");

            currentTime += currentProcess.getBurstTime();
        }
    }

    public static void roundRobin(Queue<Process> processes, int timeQuantum) {
        System.out.println("\nRound Robin Scheduling with Time Quantum " + timeQuantum + ":");

        Queue<Process> queue = new LinkedList<>(processes);
        int currentTime = 0;

        while (!queue.isEmpty()) {
            Process currentProcess = queue.poll();
            int executionTime = Math.min(timeQuantum, currentProcess.getRemainingTime());

            System.out.println("Executing process: " + currentProcess.getName() +
                    " (Arrival Time: " + currentProcess.getArrivalTime() +
                    ", Remaining Time: " + currentProcess.getRemainingTime() +
                    ", Execution Time: " + executionTime + ")");

            currentTime += executionTime;
            currentProcess.setRemainingTime(currentProcess.getRemainingTime() - executionTime);

            if (currentProcess.getRemainingTime() > 0) {
                queue.offer(currentProcess);
            }
        }
    }
}

public class SchedulerExample {
    public static void main(String[] args) {
        Queue<Process> processes = new LinkedList<>();
        processes.add(new Process("P1", 0, 5));
        processes.add(new Process("P2", 2, 3));
        processes.add(new Process("P3", 4, 1));
        processes.add(new Process("P4", 5, 2));

        Scheduler.FCFS(new LinkedList<>(processes));

        int timeQuantum = 2;
        Scheduler.roundRobin(new LinkedList<>(processes), timeQuantum);
    }
}
