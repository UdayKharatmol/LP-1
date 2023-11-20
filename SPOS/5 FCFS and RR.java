import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Process {
    int id;
    int arrivalTime;
    int burstTime;
    int remainingBurstTime;

    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingBurstTime = burstTime;
    }
}

class SchedulingAlgorithms {
    List<Process> processes;
    int timeQuantum;

    public SchedulingAlgorithms() {
        this.processes = new ArrayList<>();
    }

    public void addProcess(int id, int arrivalTime, int burstTime) {
        Process process = new Process(id, arrivalTime, burstTime);
        processes.add(process);
    }

    // First-Come-First-Serve (FCFS) Scheduling Algorithm
    public void fcfs() {
        processes.sort((p1, p2) -> Integer.compare(p1.arrivalTime, p2.arrivalTime));
        executeSchedulingAlgorithm("FCFS");
    }

    // Round Robin Scheduling Algorithm
    public void roundRobin(int timeQuantum) {
        this.timeQuantum = timeQuantum;
        executeSchedulingAlgorithm("Round Robin");
    }

    private void executeSchedulingAlgorithm(String algorithm) {
        int currentTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;

        System.out.println(algorithm + " Scheduling:");

        while (!processes.isEmpty()) {
            Process currentProcess = processes.get(0);

            if (currentProcess.arrivalTime <= currentTime) {
                int executionTime = Math.min(timeQuantum, currentProcess.remainingBurstTime);

                currentProcess.remainingBurstTime -= executionTime;
                currentTime += executionTime;

                if (currentProcess.remainingBurstTime == 0) {
                    processes.remove(0);
                    int turnaroundTime = currentTime - currentProcess.arrivalTime;
                    int waitingTime = turnaroundTime - currentProcess.burstTime;

                    totalTurnaroundTime += turnaroundTime;
                    totalWaitingTime += waitingTime;

                    System.out.println("Process " + currentProcess.id +
                            ": Turnaround Time = " + turnaroundTime +
                            ", Waiting Time = " + waitingTime);
                } else {
                    processes.add(processes.remove(0)); // Move the current process to the end of the queue
                }
            } else {
                currentTime++;
            }
        }

        double averageTurnaroundTime = (double) totalTurnaroundTime / processes.size();
        double averageWaitingTime = (double) totalWaitingTime / processes.size();

        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
        System.out.println("Average Waiting Time: " + averageWaitingTime);
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        SchedulingAlgorithms scheduler = new SchedulingAlgorithms();

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.println("Enter details for Process " + (i + 1) + ":");
            System.out.print("Arrival Time: ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Burst Time: ");
            int burstTime = scanner.nextInt();

            scheduler.addProcess(i + 1, arrivalTime, burstTime);
        }

        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter time quantum for Round Robin: ");
        int timeQuantum = scanner.nextInt();

        scheduler.fcfs();
        scheduler.roundRobin(timeQuantum);
    }
}
