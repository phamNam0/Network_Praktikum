
import java.io.*;
import java.net.*;
import java.nio.file.*;

public class DiskManagementServer {
    final static int PORT = 4711;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {


                try (Socket client = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                     PrintWriter out = new PrintWriter(client.getOutputStream()))

                {
                    System.out.println("waiting for input...");
                    String input;
                    while ((input = in.readLine()) != null) {
                        System.out.println(input);
                        String[] i = input.split(" ");
                        String command = i[0];
                        String path = i[1];

                        if (command.equals("file")) {
                            if (isValidPath(Paths.get(path))) {
                                File f = new File(path);
                                out.write("The file takes " + f.length() + " Bytes space.\n");
                                out.flush();
                            } else {
                                out.write("The file doesn't exist");
                                out.flush();
                            }
                        } else if (command.equals("free")) {
                            Path f = Paths.get(path);
                            System.out.println(isValidPath(f));
                            if (isValidPath(f)) {
                                long space = Files.getFileStore(f).getUnallocatedSpace();
                                out.write("The space taken " + space + ". The socket is closing now.\n");
                                out.flush();
                            } else {
                                out.write("The given path doesn't exist\n");
                                out.flush();
                            }

                        } else if (command.equals("get")) {
                            try (DataOutputStream fOut = new DataOutputStream(new FileOutputStream(path));
                                 DataInputStream fIn = new DataInputStream(new FileInputStream(path))) {
                                byte[] bArray = new byte[1024 * 16];
                                int byte0;
                                while ((byte0 = fIn.readByte()) > 0) {
                                    fOut.write(bArray, 0, byte0);
                                }
                                //fOut.write();
                            }
                        }
                    }
                    System.out.println("ende");


                } catch (SocketException e) {
                    System.err.println("Socket was unexpectedly terminated");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static boolean isValidPath(Path path) {
        try {
            boolean p = Files.exists(path);
            return p;
        } catch (NullPointerException | IllegalArgumentException ex) {
            return false;
        }
    }

}
