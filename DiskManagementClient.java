import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class DiskManagementClient {

    final static int PORT = 4711;

    public static void main(String[] args) {


        try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
            String userInput = stdIn.readLine();
            String[] tmpArray = userInput.split(" ");
            System.out.println(tmpArray.length);
            String HOST = tmpArray[0];

            try (Socket socket = new Socket(HOST, PORT);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
            ) {

                while ((userInput = stdIn.readLine()) != null) {
                    tmpArray = userInput.split(" ");
                    String tmp;
                    if (tmpArray.length < 2) {
                        System.out.println("Amount of prompts needed is 2." +
                                "The needed command prompts are <Hostmame>, <ProgrammCommand> and <FilePath>");
                        continue;
                    }
                    //System.out.println(userInput);
                    if (tmpArray[0].equals("file")) {
                        out.write(userInput + "\n");
                        out.flush();
                        tmp = in.readLine();
                        System.out.println(tmp);
                        socket.close();
                        System.exit(1);
                    } else if (tmpArray[0].equals("free")) {
                        out.write(userInput + "\n");
                        out.flush();
                        tmp = in.readLine();
                        System.out.println(tmp);
                        socket.close();
                        System.exit(1);
                    } else if (tmpArray[0].equals("get")) {
                        String[] paths = tmpArray[2].split("\\\\");
                        String f = paths[paths.length - 1];
                        try (DataInputStream dIn = new DataInputStream(new FileInputStream(tmpArray[2]));
                             DataOutputStream dOut = new DataOutputStream(new FileOutputStream("D:\\ServerOutput\\" + f))) {
                            byte[] bytes = new byte[1024 * 16];

                            int count;
                            while ((count = dIn.read(bytes)) > 0) {
                                dOut.write(bytes, 0, count);
                            }

                            socket.close();
                            System.exit(1);
                        }

                    } else {
                        out.write(userInput + "\n");
                        out.flush();
                        tmp = in.readLine();
                        System.out.println(tmp);
                    }

                }

            } catch (UnknownHostException e) {
                System.err.println("Don't know about host " + HOST);
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to " +
                        HOST);
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println();
            System.exit(1);

        }
    }
}
