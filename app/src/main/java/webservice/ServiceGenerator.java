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

    private static OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor);

    private static OkHttpClient okHttpClient = okHttpClientBuilder.build();

    public static <T> T createService(Class<T> serviceClass){
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
