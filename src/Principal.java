import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        List<String> historial =new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        try{
            ConversorMoneda conversor = new ConversorMoneda();
            System.out.println("-------TASAS DE CONVERSION----------");
            Moneda monedaBase = conversor.buscarMoneda("USD");
            System.out.println("Tasa de conversion encontradas con exito.");
            int opcion;
            int EleccionUsuario;
            String menu = """
                \n::::::::::Conversor de monedas  ::::::::::
                \t1. Convertir de valor.
                \t2. Mostrar monedas disponibles.
                \t3. Mostrar historial.
                \t4. Guardar historial en un archivo.
                \t5. Salir.
                """;

            do {
                System.out.print(menu+" Eleccion: ");
                while (!scanner.hasNext()){
                    System.out.println("Por favor, ingrese un numero valido valido");
                    scanner.next(); //Limpieza de buffer
                }
                opcion = scanner.nextInt();
                switch (opcion) {
                    case 1:
                        //Solicitud de moneda y monto
                        System.out.println("Ingrese la moneda de origen: ");
                        String monedaOrigen = scanner.next().toUpperCase();

                        System.out.println("Ingrese la moneda de destino: ");
                        String monedaDestino = scanner.next().toUpperCase();

                        System.out.println("Ingrese  el monto a convertir: ");
                        while (!scanner.hasNextFloat()){
                            System.out.println("Por favor, ingrese un valor numerico valido");
                            scanner.next(); //Limpieza de buffer
                        }
                        float monto =scanner.nextFloat();
                        // Intentar realizar la conversion

                        try{
                            float resultado = conversor.convertirMoneda(monedaBase, monedaDestino, monedaOrigen, monto);
                            String registro = String.format("(%s) %.2f %s  convierte a : %.2f %s",
                                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy")),
                                    monto, monedaOrigen, resultado, monedaDestino);
                            historial.add(registro);

                        }catch (RuntimeException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    case 2: //Mostrar monedas disponibles
                        System.out.println("Monedas disponibles en la API");
                        verCodigoMonedas();
                        //conversor.obtenerMonedasDisponibles(monedaBase).forEach(System.out::println);
                        break;

                    case 3: //Mostrar historial
                        if (historial.isEmpty()){
                            System.out.println("No hay conversiones registradas");
                        }else {
                            System.out.println("Historial de conversiones");
                            historial.forEach(System.out::println);
                        }
                        break;

                    case 4: //Guardar historial en un archivo
                        try {
                            GeneradorDeArchivo generador = new GeneradorDeArchivo();
                            generador.guardarHistorial(historial);
                            System.out.println("Historial guardado Exitosamente en historial_conversiones.json");

                        } catch (IOException e) {
                            System.out.println("Error al guardar el historial: "+ e.getMessage());
                        }
                        break;
                    case 5:
                        System.out.println("Operaciones de programa finalizadas");
                        System.exit(0);

                    default:
                        System.out.println("Opcion valida. Intente nuevamente");
                }

            }while(opcion != 5);
        }
        catch (RuntimeException e) {
            System.out.println("Error al cargar las tasa de conversion: "+ e.getMessage());
        }

    }
    private static void verCodigoMonedas(){
        String monedas = """
                         \nCODIGOS DE MONEDAS POR PAIS:
                         **********************************************************************************************************************************************
                         "USD": "Dólar estadounidense",              "AED": "Dírham de los Emiratos Árabes Unidos",   "AFN": "Afgani afgano",
                         "ALL": "Lek albanés",                       "AMD": "Dram armenio",                           "ANG": "Florín antillano neerlandés",
                         "AOA": "Kwanza angoleño",                   "ARS": "Peso argentino",                         "AUD": "Dólar australiano",
                         "AWG": "Florín arubeño",                    "AZN": "Manat azerbaiyano",                      "BAM": "Marco convertible de Bosnia-Herzegovina",
                         "BBD": "Dólar de Barbados",                 "BDT": "Taka de Bangladesh",                     "BGN": "Lev búlgaro",
                         "BHD": "Dinar bareiní",                     "BIF": "Franco burundés",                        "BMD": "Dólar bermudeño",
                         "BND": "Dólar de Brunéi",                   "BOB": "Boliviano boliviano",                    "BRL": "Real brasileño",
                         "BSD": "Dólar bahameño",                    "BTN": "Ngultrum butanés",                       "BWP": "Pula botsuano",
                         "BYN": "Rublo bielorruso",                  "BZD": "Dólar beliceño",                         "CAD": "Dólar canadiense",
                         "CDF": "Franco congoleño",                  "CHF": "Franco suizo",                           "CLP": "Peso chileno",
                         "CNY": "Yuan chino",                        "COP": "Peso colombiano",                        "CRC": "Colón costarricense",
                         "CUP": "Peso cubano",                       "CVE": "Escudo caboverdiano",                    "CZK": "Corona checa",
                         "DJF": "Franco yibutiano",                  "DKK": "Corona danesa",                          "DOP": "Peso dominicano",
                         "DZD": "Dinar argelino",                    "EGP": "Libra egipcia",                          "ERN": "Nakfa eritreo",
                         "ETB": "Birr etíope",                       "EUR": "Euro",                                   "FJD": "Dólar fiyiano",
                         "FKP": "Libra malvinense",                  "FOK": "Corona feroesa",                         "GBP": "Libra esterlina",
                         "GEL": "Lari georgiano",                    "GGP": "Libra de Guernsey",                      "GHS": "Cedi ghanés",
                         "GIP": "Libra gibraltareña",                "GMD": "Dalasi gambiano",                        "GNF": "Franco guineano",
                         "GTQ": "Quetzal guatemalteco",              "GYD": "Dólar guyanés",                          "HKD": "Dólar de Hong Kong",
                         "HNL": "Lempira hondureño",                 "HRK": "Kuna croata",                            "HTG": "Gourde haitiano",
                         "HUF": "Forinto húngaro",                   "IDR": "Rupia indonesia",                        "ILS": "Nuevo shéquel israelí",
                         "IMP": "Libra de la Isla de Man",           "INR": "Rupia india",                            "IQD": "Dinar iraquí",
                         "IRR": "Rial iraní",                        "ISK": "Corona islandesa",                       "JEP": "Libra de Jersey",
                         "JMD": "Dólar jamaicano",                   "JOD": "Dinar jordano",                          "JPY": "Yen japonés",
                         "KES": "Chelín keniano",                    "KGS": "Som kirguís",                            "KHR": "Riel camboyano",
                         "KID": "Dólar de Kiribati",                 "KMF": "Franco comorense",                       "KRW": "Won surcoreano",
                         "KWD": "Dinar kuwaití",                     "KYD": "Dólar caimano",                          "KZT": "Tenge kazajo",
                         "LAK": "Kip laosiano",                      "LBP": "Libra libanesa",                         "LKR": "Rupia de Sri Lanka",
                         "LRD": "Dólar liberiano",                   "LSL": "Loti lesothense",                        "LYD": "Dinar libio",
                         "MAD": "Dírham marroquí",                   "MDL": "Leu moldavo",                            "MGA": "Ariary malgache",
                         "MKD": "Dinar macedonio",                   "MMK": "Kyat birmano",                           "MNT": "Tugrik mongol",
                         "MOP": "Pataca de Macao",                   "MRU": "Uguiya mauritano",                       "MUR": "Rupia mauriciana",
                         "MVR": "Rufiyaa maldiva",                   "MWK": "Kwacha malauí",                          "MXN": "Peso mexicano",
                         "MYR": "Ringgit malayo",                    "MZN": "Metical mozambiqueño",                   "NAD": "Dólar namibio",
                         "NGN": "Naira nigeriana",                   "NIO": "Córdoba nicaragüense",                   "NOK": "Corona noruega",
                         "NPR": "Rupia nepalí",                      "NZD": "Dólar neozelandés",                      "OMR": "Rial omaní",
                         "PAB": "Balboa panameño",                   "PEN": "Sol peruano",                            "PGK": "Kina de Papúa Nueva Guinea",
                         "PHP": "Peso filipino",                     "PKR": "Rupia pakistaní",                        "PLN": "Zloty polaco",
                         "PYG": "Guaraní paraguayo",                 "QAR": "Rial catarí",                            "RON": "Leu rumano",
                         "RSD": "Dinar serbio",                      "RUB": "Rublo ruso",                             "RWF": "Franco ruandés",
                         "SAR": "Rial saudí",                        "SBD": "Dólar de las Islas Salomón",             "SCR": "Rupia de Seychelles",
                         "SDG": "Libra sudanesa",                    "SEK": "Corona sueca",                           "SGD": "Dólar de Singapur",
                         "SHP": "Libra de Santa Elena",              "SLE": "Leona de Sierra Leona",                  "SLL": "Leone sierraleonés",
                         "SOS": "Chelín somalí",                     "SRD": "Dólar surinamés",                        "SSP": "Libra sursudanesa",
                         "STN": "Dobra santotomense",                "SYP": "Libra siria",                            "SZL": "Lilangeni suazi",
                         "THB": "Baht tailandés",                    "TJS": "Somoni tayiko",                          "TMT": "Manat turcomano",
                         "TND": "Dinar tunecino",                    "TOP": "Pa'anga tongano",                        "TRY": "Lira turca",
                         "TTD": "Dólar de Trinidad y Tobago",        "TVD": "Dólar tuvaluano",                        "TWD": "Nuevo dólar taiwanés",
                         "TZS": "Chelín tanzano",                    "UAH": "Grivna ucraniana",                       "UGX": "Chelín ugandés",
                         "UYU": "Peso uruguayo",                     "UZS": "Som uzbeko",                             "VES": "Bolívar venezolano",
                         "VND": "Dong vietnamita",                   "VUV": "Vatu vanuatuense",                       "WST": "Tala samoano",
                         "XAF": "Franco CFA BEAC",                   "XCD": "Dólar del Caribe Oriental",              "XDR": "Derechos Especiales de Giro",
                         "XOF": "Franco CFA BCEAO",                  "XPF": "Franco CFP",                             "YER": "Rial yemení",
                         "ZAR": "Rand sudafricano",                  "ZMW": "Kwacha zambiano",                        "ZWL": "Dólar zimbabuense"
                         **********************************************************************************************************************************************
                         """;
        System.out.println(monedas);
    }
}
