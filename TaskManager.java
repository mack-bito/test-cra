import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Task Manager CLI Application
 * -----------------------------------
 * Commands:
 *   add <title>
 *   list
 *   done <id>
 *   delete <id>
 *   help
 *   exit
 */
public class TaskManager {

    private static final String DB_FILE = "tasks.db";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Map<Integer, Task> tasks = new LinkedHashMap<>();
    private int nextId = 1;

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        manager.load();
        manager.run();
        manager.save();
    }

    /* =========================
       Application Loop
       ========================= */
    private void run() {
        System.out.println("=== Task Manager CLI ===");
        System.out.println("Type 'help' to see commands\n");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            String[] parts = input.split("\\s+", 2);
            String command = parts[0].toLowerCase();

            try {
                switch (command) {
                    case "add":
                        if (parts.length < 2) {
                            error("Title required");
                        } else {
                            addTask(parts[1]);
                        }
                        break;

                    case "list":
                        listTasks();
                        break;

                    case "done":
                        markDone(parseId(parts));
                        break;

                    case "delete":
                        deleteTask(parseId(parts));
                        break;

                    case "help":
                        printHelp();
                        break;

                    case "exit":
                        System.out.println("Bye 👋");
                        return;

                    default:
                        error("Unknown command");
                }
            } catch (Exception e) {
                error(e.getMessage());
            }
        }
    }

    /* =========================
       Commands
       ========================= */
    private void addTask(String title) {
        Task task = new Task(
                nextId++,
                title,
                TaskStatus.PENDING,
                LocalDateTime.now()
        );
        tasks.put(task.getId(), task);
        success("Task added (id=" + task.getId() + ")");
    }

    private void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("(no tasks)");
            return;
        }

        for (Task task : tasks.values()) {
            System.out.println(task.format());
        }
    }

    private void markDone(int id) {
        Task task = getTask(id);
        task.setStatus(TaskStatus.DONE);
        success("Task marked as DONE");
    }

    private void deleteTask(int id) {
        if (tasks.remove(id) == null) {
            throw new IllegalArgumentException("Task not found");
        }
        success("Task deleted");
    }

    /* =========================
       Helpers
       ========================= */
    private int parseId(String[] parts) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("ID required");
        }
        try {
            return Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid ID");
        }
    }

    private Task getTask(int id) {
        Task task = tasks.get(id);
        if (task == null) {
            throw new IllegalArgumentException("Task not found");
        }
        return task;
    }

    private void printHelp() {
        System.out.println("""
                Commands:
                  add <title>     Add new task
                  list            List all tasks
                  done <id>       Mark task as done
                  delete <id>     Delete task
                  help            Show help
                  exit            Exit application
                """);
    }

    private void error(String msg) {
        System.out.println("❌ " + msg);
    }

    private void success(String msg) {
        System.out.println("✅ " + msg);
    }

    /* =========================
       Persistence
       ========================= */
    private void load() {
        File file = new File(DB_FILE);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = Task.deserialize(line);
                tasks.put(task.getId(), task);
                nextId = Math.max(nextId, task.getId() + 1);
            }
        } catch (IOException e) {
            error("Failed to load tasks");
        }
    }

    private void save() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DB_FILE))) {
            for (Task task : tasks.values()) {
                writer.println(task.serialize());
            }
        } catch (IOException e) {
            error("Failed to save tasks");
        }
    }

    /* =========================
       Domain Model
       ========================= */
    enum TaskStatus {
        PENDING,
        DONE
    }

    static class Task {
        private final int id;
        private final String title;
        private TaskStatus status;
        private final LocalDateTime createdAt;

        Task(int id, String title, TaskStatus status, LocalDateTime createdAt) {
            this.id = id;
            this.title = title;
            this.status = status;
            this.createdAt = createdAt;
        }

        int getId() {
            return id;
        }

        void setStatus(TaskStatus status) {
            this.status = status;
        }

        String format() {
            return String.format(
                    "[%d] %-8s %-30s (%s)",
                    id,
                    status,
                    title,
                    createdAt.format(FORMATTER)
            );
        }

        String serialize() {
            return id + "|" +
                   escape(title) + "|" +
                   status + "|" +
                   createdAt.format(FORMATTER);
        }

        static Task deserialize(String line) {
            String[] parts = line.split("\\|", 4);
            int id = Integer.parseInt(parts[0]);
            String title = unescape(parts[1]);
            TaskStatus status = TaskStatus.valueOf(parts[2]);
            LocalDateTime created =
                    LocalDateTime.parse(parts[3], FORMATTER);
            return new Task(id, title, status, created);
        }

        private static String escape(String s) {
            return s.replace("\\", "\\\\").replace("|", "\\|");
        }

        private static String unescape(String s) {
            return s.replace("\\|", "|").replace("\\\\", "\\");
        }
    }
}

