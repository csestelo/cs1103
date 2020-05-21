import java.io.File;
import java.util.Scanner;


class DirectoryList {
    public static void main(String[] args) {

        String directoryName;  // Directory name entered by the user.
        File directory;        // File object referring to the directory.
        Scanner scanner;       // For reading a line of input from the user.

        scanner = new Scanner(System.in);  // scanner reads from standard input.

        System.out.print("Enter a directory name: ");
        directoryName = scanner.nextLine().trim();
        directory = new File(directoryName);

        if (!directory.isDirectory()) {
            if (!directory.exists())
                System.out.println("There is no such directory!");
            else
                System.out.println("That file is not a directory.");
        } else {
            listFiles(directory, 0);
        }
    }

    static void listFiles(File dir, int indent) {
        for (String file : dir.list()) {
            File currentFile = new File(dir, file);
            if (currentFile.isDirectory()) {
                System.out.println(" ".repeat(indent) + file);
                listFiles(currentFile, indent + 4);
            } else {
                System.out.println(" ".repeat(indent) + file);
            }
        }
    }
}