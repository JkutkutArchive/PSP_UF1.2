package dam.psp.uf1.tarea2_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Escribe una clase llamada Ejecuta que reciba como argumentos el comando y las opciones
 * del comando que se quiere ejecutar. El programa debe crear un proceso hijo que ejecute el
 * comando con las opciones correspondientes mostrando un mensaje de error en el caso de que
 * no se realizase correctamente la ejecución. El padre debe esperar a que el hijo termine de
 * informar si se produjo alguna anomalía en la ejecución del hijo.
 *
 * @author Jkutkut - Jorge Re
 */
public class Ejecuta {
    public static void main(String[] args) {
        String cmd = String.join(" ", args);
        exec(cmd);
        // Manual tests
        exec("");
        exec(null);
        exec("ls -l fjasdklf");
        exec("ls");
        exec("ls -l");
    }

    private static void exec(String cmd) {
        if (cmd == null || cmd.isEmpty()) {
            System.out.println("No command specified\n");
            return;
        }
        try {
            Process p = new ProcessBuilder(cmd.split(" ")).start();
            p.waitFor();
            if (p.exitValue() != 0)
                handleError(p.getErrorStream());
            else
                handleOutput(p.getInputStream());
        }
        catch (Exception e)
        {
            System.out.println("Error executing cmd:");
            e.printStackTrace();
        }
    }

    private static void handleOutput(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader (new InputStreamReader (is));
        StringBuilder sb = new StringBuilder();
        String l;
        while ((l = br.readLine()) != null)
            sb.append(l).append("\n");
        System.out.println("Command executed correctly:\n");
        System.out.println(sb);
    }

    private static void handleError(InputStream errorStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(errorStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)
            sb.append(line).append("\n");
        System.out.println("Error executing cmd:\n");
        System.out.println(sb);
    }
}