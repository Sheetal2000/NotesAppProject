import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotesApp {
    static final String FILE_NAME = "notes.txt";
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Notes App ---");
            System.out.println("1. Add Note");
            System.out.println("2. View Notes");
            System.out.println("3. Delete Note");
            System.out.println("4. Edit Note");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine();
            switch (input) {
                case "1" -> addNote();
                case "2" -> viewNotes();
                case "3" -> deleteNote();
                case "4" -> editNote();
                case "5" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    // Feature 1: Add Note with timestamp
    public static void addNote() {
        System.out.print("Enter your note: ");
        String note = scanner.nextLine();

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String noteWithTimestamp = "[" + timestamp + "] " + note;

        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writer.write(noteWithTimestamp + "\n");
            System.out.println("Note saved!");
        } catch (IOException e) {
            System.out.println("Error saving note: " + e.getMessage());
        }
    }

    // View all notes
    public static void viewNotes() {
        System.out.println("\n--- Your Notes ---");
        List<String> notes = readNotesFromFile();

        if (notes.isEmpty()) {
            System.out.println("(No notes found)");
        } else {
            for (int i = 0; i < notes.size(); i++) {
                System.out.println((i + 1) + ". " + notes.get(i));
            }
        }
    }

    // Feature 2: Delete a note
    public static void deleteNote() {
        List<String> notes = readNotesFromFile();

        if (notes.isEmpty()) {
            System.out.println("No notes to delete.");
            return;
        }

        viewNotes();
        System.out.print("Enter the note number to delete: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;

            if (index >= 0 && index < notes.size()) {
                notes.remove(index);
                writeNotesToFile(notes);
                System.out.println("Note deleted successfully.");
            } else {
                System.out.println("Invalid note number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    // Feature 3: Edit a note
    public static void editNote() {
        List<String> notes = readNotesFromFile();

        if (notes.isEmpty()) {
            System.out.println("No notes to edit.");
            return;
        }

        viewNotes();
        System.out.print("Enter the note number to edit: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;

            if (index >= 0 && index < notes.size()) {
                System.out.print("Enter the new content: ");
                String newContent = scanner.nextLine();
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                notes.set(index, "[" + timestamp + "] " + newContent);
                writeNotesToFile(notes);
                System.out.println("Note updated successfully.");
            } else {
                System.out.println("Invalid note number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    // Helper: Read notes from file
    public static List<String> readNotesFromFile() {
        List<String> notes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                notes.add(line);
            }
        } catch (IOException e) {
            // Do nothing if file doesn't exist
        }
        return notes;
    }

    // Helper: Write all notes to file
    public static void writeNotesToFile(List<String> notes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String note : notes) {
                writer.write(note);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing notes: " + e.getMessage());
        }
    }
}
