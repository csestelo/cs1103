package WebServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ReadRequest {
    private final static int LISTENING_PORT = 50505;

    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(LISTENING_PORT);
        } catch (Exception e) {
            System.out.println("Failed to create listening socket.");
            return;
        }
        System.out.println("Listening on port " + LISTENING_PORT);
        try {
            while (true) {
                Socket connection = serverSocket.accept();
                System.out.println("\nConnection from " + connection.getRemoteSocketAddress());
                handleConnection(connection);
            }
        } catch (Exception e) {
            System.out.println("Server socket shut down unexpectedly!");
            System.out.println("Error: " + e);
            System.out.println("Exiting.");
        }
    }

    private static void handleConnection(Socket connection) {
        try (connection) { // make SURE connection is closed
            String rootDirectory = "/Users/cintia.sestelo/projects/cs-1103/src";
            Scanner in = new Scanner(connection.getInputStream());
            OutputStream out = connection.getOutputStream();
            String firstLine = in.nextLine();

            while (true) { // it receives all input content from client
                if (!in.hasNextLine() || in.nextLine().trim().length() == 0)
                    break;
            }
            PrintWriter writer = new PrintWriter(out);
            String[] requestHead = firstLine.split(" ");
            boolean hasValidHTTPVersion = (requestHead[2].equals("HTTP/1.1") || requestHead[2].equals("HTTP/1.0"));
            File file = new File(rootDirectory + requestHead[1]);

            if (!requestHead[0].equals("GET") || !hasValidHTTPVersion) {
                sendErrorResponse(501, writer);
            } else if (file.isDirectory()) {
                sendErrorResponse(400, writer);
            } else if (!file.exists()) {
                sendErrorResponse(404, writer);
            } else if (!file.canRead()) {
                sendErrorResponse(403, writer);
            } else {
                sendFile(file, out, writer);
            }
        } catch (Exception e) {
            System.out.println("Error while communicating with client: " + e);
        }
    }

    /**
     * @param status Has format <int status> <string status> Example: 200 OK
     */
    private static void printHeaders(PrintWriter w, String status, long contentLength, String contentType) {
        w.print("HTTP/1.1 " + status + "\r\n");
        w.print("Connection: close" + "\r\n");
        w.print("Content-Length: " + contentLength + "\r\n");
        w.print("Content-Type: " + contentType + "\r\n");
        w.print("\r\n");
        w.flush();
    }

    private static void sendFile(File file, OutputStream socketOut, PrintWriter w) throws IOException {
        printHeaders(w, "200 OK", file.length(), getMimeType(file.getName()));

        InputStream in = new BufferedInputStream(new FileInputStream(file));
        OutputStream out = new BufferedOutputStream(socketOut);
        while (true) {
            int x = in.read(); // read one byte from file
            if (x < 0)
                break; // end of file reached
            out.write(x);  // write the byte to the socket
        }
        out.flush();
    }

    static void sendErrorResponse(int errorCode, PrintWriter w) {
        String status;
        String errorMessage;
        switch (errorCode) {
            case 400:
                status = errorCode + " Bad Request";
                errorMessage = "The given path is from a directory. Please, provide a file path.";
                break;
            case 403:
                status = errorCode + " Forbidden";
                errorMessage = "The server is not allowed to access the given file.";
                break;
            case 404:
                status = errorCode + " Not Found";
                errorMessage = "File does not exist. Please, provide a valid file path.";
                break;
            default:
                status = errorCode + " Not Implemented";
                errorMessage = "You are using either an invalid http method or http version.";
        }

        String html = "<html><head><title>Error</title></head><body><h2>Error: " + status +
                "</h2><p>" + errorMessage + "</p></body></html>";
        printHeaders(w, status, html.length(), "text/html");

        w.print(html);
        w.print("\r\n");
        w.flush();
    }

    private static String getMimeType(String fileName) {
        int pos = fileName.lastIndexOf('.');
        if (pos < 0)  // no file extension in name
            return "x-application/x-unknown";
        String ext = fileName.substring(pos + 1).toLowerCase();
        switch (ext) {
            case "txt":
                return "text/plain";
            case "html":
            case "htm":
                return "text/html";
            case "css":
                return "text/css";
            case "js":
                return "text/javascript";
            case "java":
                return "text/x-java";
            case "jpeg":
            case "jpg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "ico":
                return "image/x-icon";
            case "class":
                return "application/java-vm";
            case "jar":
                return "application/java-archive";
            case "zip":
                return "application/zip";
            case "xml":
                return "application/xml";
            case "xhtml":
                return "application/xhtml+xml";
            default:
                return "x-application/x-unknown";
        }
        // Note:  x-application/x-unknown  is something made up;
        // it will probably make the browser offer to save the file.
    }
}
