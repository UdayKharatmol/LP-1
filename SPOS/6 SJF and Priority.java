import java.util.*;

class Process {
    int id;
    int arrivalTime;
    int burstTime;
    int priority;

    public Process(int id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}

class SchedulingAlgorithms {
    List<Process> processes;

    public SchedulingAlgorithms() {
        this.processes = new ArrayList<>();
    }

    public void addProcess(int id, int arrivalTime, int burstTime, int priority) {
        Process process = new Process(id, arrivalTime, burstTime, priority);
        processes.add(process);
    }

    // Shortest Job First (SJF) Preemptive Scheduling Algorithm
    public void sjfPreemptive() {
        List<Process> processOrder = new ArrayList<>();
        List<Process> remainingProcesses = new ArrayList<>(processes);

        int currentTime = 0;
        while (!remainingProcesses.isEmpty()) {
            Process shortestJob = getShortestJob(remainingProcesses, currentTime);
            if (shortestJob != null) {
                processOrder.add(shortestJob);
                currentTime += 1; // Process one unit of time
                shortestJob.burstTime -= 1;

                if (shortestJob.burstTime == 0) {
                    remainingProcesses.remove(shortestJob);
                }
            } else {
                currentTime += 1; // No process is available, time is idling
            }
        }

        printSchedule(processOrder, "SJF Preemptive");
    }

    private Process getShortestJob(List<Process> processes, int currentTime) {
        Process shortestJob = null;
        int shortestBurstTime = Integer.MAX_VALUE;

        for (Process process : processes) {
            if (process.arrivalTime <= currentTime && process.burstTime < shortestBurstTime && process.burstTime > 0) {
                shortestJob = process;
                shortestBurstTime = process.burstTime;
            }
        }

        return shortestJob;
    }

    // Priority Non-Preemptive Scheduling Algorithm
    public void priorityNonPreemptive() {
        List<Process> processOrder = new ArrayList<>(processes);
        processOrder.sort(Comparator.comparingInt(p -> p.priority));

        printSchedule(processOrder, "Priority Non-Preemptive");
    }

    private void printSchedule(List<Process> processOrder, String algorithm) {
        System.out.println(algorithm + " Scheduling:");
        System.out.println("Process\tArrival Time\tBurst Time\tPriority");
        for (Process process : processOrder) {
            System.out.println(process.id + "\t\t" + process.arrivalTime + "\t\t\t" + process.burstTime + "\t\t\t" + process.priority);
        }
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
            System.out.print("Priority: ");
            int priority = scanner.nextInt();

            scheduler.addProcess(i + 1, arrivalTime, burstTime, priority);
        }

        scheduler.sjfPreemptive();
        scheduler.priorityNonPreemptive();
    }
}
