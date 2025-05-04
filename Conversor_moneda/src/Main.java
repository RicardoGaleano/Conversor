import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner teclado=new Scanner(System.in);
        bienvenida( teclado);

        int opcion=pedirOpcion(teclado);

        while (opcion!=2){
            if (opcion==1){

                System.out.println("Ingresa la moneda base, por ejemplo USD.");
                String monedaBase=teclado.nextLine().toUpperCase();
                System.out.println("Ingresa la moneda a la que quieres convertir, por ejemplo COP.");
                String monedaDestino= teclado.nextLine().toUpperCase();
                String url="https://v6.exchangerate-api.com/v6/2c81e96e4852cd1f76ede71c/latest/"+monedaBase;
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .build();
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());
                Gson gson=new Gson();
                var cambio= response.body();
                ExchangeRateResponse datos=gson.fromJson(cambio,ExchangeRateResponse.class);
                Map<String, Double> tasas = datos.getConversion_rates();

                if (tasas.containsKey(monedaDestino)){
                    Double tasaCop=tasas.get(monedaDestino);
                    System.out.println("Tasa de conversion de "+monedaBase+" a "+monedaDestino + ": "+tasaCop);
                    System.out.println("Cuantos "+ monedaBase+" quieres convertir a "+monedaDestino+" :");
                    double cantidad=teclado.nextDouble();
                    teclado.nextLine();
                    double resultado= cantidad* tasaCop;
                    System.out.println(cantidad+" "+monedaBase+" equivale a "+resultado+" "+monedaDestino);

                }else {
                    System.out.println("La moneda destino no se encontro.Asegurate de escribir una moneda valida.");
                }
            }else {
                System.out.println("Opcion invalida.Intenta de nuevo");
            }
            opcion=menu(teclado);
        }
        System.out.println("Gracias por usar nuestros servicios.");
    }
    private static int pedirOpcion(Scanner teclado){
        int opcion=-1;
        while (opcion!=1 && opcion!=2){
            System.out.println("Elile la opcion correcta.");
            System.out.println("1. Convertir moneda");
            System.out.println("2. Salir del programa.");

            try{
                opcion=teclado.nextInt();
                teclado.nextLine();

                if (opcion!=1 && opcion!=2){
                    System.out.println("Opcion no valida, por favor elije 1 o 2.");
                }
            }catch (InputMismatchException e){
                System.out.println("Debes ingresar un numero.");
                teclado.nextLine();
            }
        }
        return opcion;
    }



    private static void bienvenida(Scanner teclado) {
        System.out.println("************************************");
        System.out.println("CONVERSOR DE MONEDAS MARRANO'S DAY");
        System.out.println("Ingresa tu nombre completo");
        String nombre=teclado.nextLine();
        System.out.println("Bienvenido a tu casa "+nombre.toUpperCase()+" para nosotros siempre es un placer ayudarte.");
        System.out.println("*** MENU PRINCIPAL ***");
    }

    private static int menu(Scanner teclado) {
        System.out.println("Elije la opcion que quieres desarrollar en nuestro menu.");
        System.out.println("1. Convertir moneda.");
        System.out.println("2. Salir");
        int opcion= teclado.nextInt();
        teclado.nextLine();
        return opcion;
    }
}
