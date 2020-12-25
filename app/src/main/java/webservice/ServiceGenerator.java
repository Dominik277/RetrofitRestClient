package webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    //ovdje smo postavili konstantu BASE_API_URL unutar koje smo pohranili URL WebServera s kojeg
    //cemo dohvacati podatke koji su pohranjeni u JSON formatu, znaci ubuduce kada god zelimo
    //upisati ovaj link ne moramo se patiti i sve to upisavati nego samo upisemo BASE_API_URL
    private final static String BASE_API_URL = "https://ghibliapi.herokuapp.com/";

    //ovdje smo samo napravili varijablu unutar koje smo pohranili vrijednost null, odnos nista
    //Retrofit --> Retrofit adatptira Java interfejse za HTTP zahtjeve tako sto koristi anotacije
    //             na određenim metodama kako bi specificirao kako ce te metode poslati zahtjev serveru
    private static Retrofit retrofit = null;

    //ovo je glavna klasa koja se koristi za GSON, GSON je Java library koja sluzi kako bi se pretvorili
    //java objekti u JSON zapis.GSON se također moze koristiti i za pretvaranje JSON zapisa u Java
    //objekte, znaci obrnuto, GSON objekt mozemo napraviti i na default nacin sa new kljucnom rijeci
    //ako ne trebamo nikavu njegovu specionalnu funkcionalnost
    //GsonBuilder --> ovo je jos jedna klasa iz obitelji GSON koja radi skoro na isti nacin kao i GSON
    //klasa jedino sto ima par dodatnih funkcionalnosti kao sto je version control i slicno
    //GSON objekt ne zadrzava svoje stanje pa je moguce koristiti isti objekt u vise serijalizacija i
    //deserijalizacije
    //create() --> ova metoda se koristi samo kada kao konstruktor imamo GsonBuilder(), a kada njega
    //koristimo onda znamo da se koriste neke posebne funkcionalnosti u odnosu na default funkcionalnosti
    private static Gson gson = new GsonBuilder().create();

    //Logging u programiranju je sposobnost da se zapisuju apsolutno svi podatci vezani uz aplikaciju,
    //kao sto je npr. podaci o izvođenju programa, podaci o networkingu itd.
    //setLevel() --> metoda ima razlicitih varijacija, Level.NONE koristimo ako zelimo da nam se uopce
    //               ne prikazuje niti jedan log, Level.BASIC koristimo ako zelimo da nam se u logging-u
    //               prikazuju obicne informacije o slanju zahtjeva za informacijom prema serveru i
    //               informacije o odgovoru servera prema klijentu, Level.HEADER koristimo u istom slucaju
    //               kao i Level.BASIC samo sto u ovom slucaju jos dobivamo inforamcije i o headeru i na
    //               kraju imamo Level.BODY koji koristimo u slucaju kada zelimo imati funkcionalnost i
    //               Level.BASIC i Level.HEADER.Moramo biti oprezni s koristenjem Level.BODY jer je to
    //               mocna metoda koja moze u loggeru prikazati cak i lozinke
    //Znaci ukratko, ova klasa se koristi kako bi logirala sve HTTP podatke koji se salju tokom zahtjeva
    //i odgovora servera, pomocu metode setLevel() ogranicavamo koliko informacija zelimo da se prikazu
    //unutar loggera
    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    //HTTP je nacin na koji moderne aplikacije ostvaruju networking, na taj nacin se prenose podaci i
    //informacije od servera i klijenta i obrnuto, ako je taj networking efikasniji onda nam je i
    //aplikacija brza, a upravo to je zadata OkHttpClient library-a, OkHttp library radi najbolje kada
    //se od nje napravi samo jedna instanca, odnosno singleton
    //OkHttpClient --> ovo je klasa ciji je objekt zaduzen za slanje i primanje network zahtjeva
    //addInterceptor() --> interceptori su jaki mehanizmi koji imaju mogucnost provjeravanja, ponovnog
    //                     slanja i preoblikovanja networking zahtjeva za nekim podatkom, pomocu ove
    //                     metode smo samo rekli da ce aplikacija imati interceptor, znaci dodali smo s
    //                     tom metodom interceptor u cijelu aplikaciju koji ce sada voditi brigu o
    //                     logiranju podataka koji stizu sa servera ili se salju na server
    private static OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor);


    //ovdje smo na ovaj nacin stvorili varijablu okHttpClient u koju smo pohranili sve ono s desne
    //strane, a na desnoj strani nam je objekt tipa OkHttpClient.Builder kojeg smo upogonili pomocu
    //metode create(), sve do ovog koraka taj objekt je bio definiran, ali ne i upogonjen, ali smo
    //ga sada upogonili
    private static OkHttpClient okHttpClient = okHttpClientBuilder.build();

    //T --> ovo nam predstavlja class type parameter
    public static <T> T createService(Class<T> serviceClass){

        //u ovom dijelu koda provjeravamo ako je objekt klase Retrofit jednak null onda se izvrsava
        //dio koda unutar viticastih zagrada, a dio unutar viticastih zagrada nam govori da onda
        //inicijaliziramo retofit objekt pomocu konstruktora i puno ostalih elemenata
        //client() --> ovaj metoda nam predstavlja klijenta koji je zasluzan za slanje zahtjeva serveru
        //baseUrl() --> ovoj metodi kao argument prilazemo link webstranice s koje zelimo skidati podatke
        //addConverterFactory() --> ova metoda se koristi za serijalizaciju i deserijalizaciju
        //build() --> sve ono sto smo gore definirali ne bi bilo upogonjeno bez ove metode, onda je
        //            zasluzna za kreiranje instance ove klase
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit.create(serviceClass);
    }

}
