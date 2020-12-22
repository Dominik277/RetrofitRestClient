package webservice;

import java.util.List;
import model.Movie;
import retrofit2.Call;
import retrofit2.http.GET;

//posto koristimo interfejs unutar kojeg se nalaze abstraktne metode bez tijela,
//svaka klasa koja implemetira ovaj interfejs MORA implemenitari ovu klasu i
//napraviti tijelo te metode
//ovo je interfejs unutar kojeg su navedene samo abstraktne metode, tj. samo
//naziv metode, a njeno tijelo nismo implementirali, niti ga smijemo implementirati
//unutar interfejsa
public interface ApiInterface {

    //ova metoda ce vratiti Call objekt, unutar siljtih zagrada imamo List i unutar
    //njegovih siljatih zagrada imamo Movie, to nam govori da ce nam Call vratiti
    //Listu filmova(Movie), a to sto nam je unutar siljatih zagrada, upravo to nam je
    //JSON array objekata
    //@GET --> posto imamo abstrktnu klasu getMovies() mi bi nju trebali ispuniti nekim
    //         kodom unutar one klase koja implementira ovaj interfejs, ali Retrofit se
    //         se brine oko toga tako sto stavimo anotaciju @GET, zadaca @GET anotacije
    //         je da skine podatke sa servera, a kako bi znala koje podatke treba skinuti
    //         to joj prilazemo kao argument.Zamislimo da zelimo skinuti sve temperature u
    //         hrvatskoj preko Google Mapsa i taj zapis prikazemo u JSON-u to cemo raditi
    //         na sljedeci nacin, zamislimo da imamo link https://www.googlemaps.temerature
    //         onda kao argument @GET anotaciji dajemo u navodnicima samo dio "temperature"
    //         i na taj nacin ova metoda getMovies() je implemenitrana,zna koji joj je zadatak
    //         a sve to pomocu @GET anotacije
    //Zbog toga sto smo unutar siljastih zagrada priloziti klasu Movie i unutar nje imamo sve
    //atribute koje zelimo prikazati, na taj smo nacin odredili kako ce biti prikazani podaci
    //unutar JSON-a, odnosno kojim redosljedom
    @GET("films")

    //Call --> preko ovoga interfejsa mozemo pozvati metode koje imaju za zadacu slanje zahtjeva
    //         na neki WebServer i koje vracaju nazad odgovor toga servera
    //         svaka metoda unutar interfejsa mora imati svoju anotaciju pomocu koje se opisuje
    //         sta ce ta metoda raditi bez da implemenitramo njeno tijelo, nesto slicno kao u
    //         Room-u kada samo u Dao klasi napravili tri metode za azuriranje, unosenj i brisanje
    //         podataka pomocu anotacija @insert,@delete,@update.Te anotacije su metadate, sto
    //         zapravo znaci podaci o podacima, te smo mi na taj nacin svim tim metoda pomocu tih
    //         anotacija zapravo implementirali tijelo na "nevidljiv nacin" ajmo to tako reci,
    //         odnosno dodali smo funkcionalnost toj metodu onakvu kakvu ta anotacija sadrzi u
    //         sebi.Na isti ovaj nacin djeluje i anotacije @GET koja u sebi ima pohranjene sve
    //         funkcionalnosti pa onda ta metoda ne mora biti implementirana.Anotacije se najcesce
    //         i koriste u interfejsima, jer kod interfejsa tijelo metode ne smije biti inicijalizirano
    //         a pomocu anotacija mi metodama dajemo funkcionalnost bez da dodajemo ista u njeno tijelo
    Call<List<Movie>> getMovies();

}
