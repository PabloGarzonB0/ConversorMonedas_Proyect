import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LecturaArchivos {
    public static void verUltimaConsulta(){
        try{
            File file = new File("src/Ouput_files_ConsultasRealizadas/consultasRealizadas.txt");
            Scanner sc = new Scanner(file);
            System.out.println("\nConsultas realizadas:");
            while(sc.hasNextLine()){
                //Se recorre linea por linea del archivo de texto y se imprime
                String line = sc.nextLine();
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("\nEl archivo no fue encontrado.");;
        }
    }
}

