package dagger2.and.retrofit;

import adapter.MoviesAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import model.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import webservice.ApiInterface;
import webservice.ServiceGenerator;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    //u ovoj liniji koda smo samo definirali objekt recyclerView tipa RecyclerView
    //koji imamo u activity_main XML datoteci, te je taj objekt za sada prazan
    //samo smo odredili ime preko kojega cemo ga referencirati
    private RecyclerView recyclerView;

    //u ovoj liniji koda smo na desnoj strani napravili objekt u memoriji tipa
    //ArrayList, a referencirat cemo ga preko imena movies.ArrayList nam je isto
    //kao i array, jedina razlika je u tome sto kod array-a moramo na pocetku
    //odrediti koliko ce biti elemenata u polju i ne moze se prosirivati, dok kod
    //ArrayList-a se mogu stalno dodavati elementi.Na desnoj strani nam je List, a
    //na lijevo ArrayList zbog toga sto je List interfejs i on u sebi ima neke od
    //metoda koje sluze za spremanje podataka, u nasem slucaju smo uzeli ArreyList,
    //a postoji jos LinkedList,Vector i mnogi drugi
    private List<Movie> movies = new ArrayList<>();

    //ovdje smo samo stvorili referencu na objekt, odnosno ime preko kojega cemo
    //zvati objekt klase MoviesAdapter,
    //MoviesAdapter je klasa koju smo sami napravili koja u sebi sadrzi sve potrebno
    //kako bi napravili adapter za RecyclerView
    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //u sljedecoj liniji koda smo pohranili unutar objekta recyclerView XML atribut
        //recyclerview pomocu metode findViewById()
        //findViewById() --> ova metoda nam sluzi kako bi otisla u XML, nasla određeni
        //atribut ciji smo ID prilozili kao argument i kao rezultat vraca View objekt te
        //ga mi pohranjujemo u recyclerView referencu, odnosno preko toga imena cemo ga
        //u buducnosti dozivati
        recyclerView = findViewById(R.id.recyclerview);

        //RecyclerView ima vise mogucnosti na koji nacin da prikazuje podatke,postoji
        //FrameLayouManager,postoji GridLayoutManager i postoji LinearLayoutManager
        //svi oni imaju razlicte sposobnosti prikazivanja podataka unutar RecyclerView-a
        //mi smo u nasem slucaju odabrali LinearLayoutManager i stvorili objekt od te klase
        //kao argument konstruktoru smo predali context, odnosno unutar koje klase zelimo
        //da nam bude prikazan LinearLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);

        //ovdje smo samo na desnoj strani stvorili objekt MoviesAdapter klase koji ce
        //imati zadatak opskrbljenja RecyclerView-a podacima i time na koji nacin
        //prikazati te podatke
        moviesAdapter = new MoviesAdapter(MainActivity.this,movies);

        //ovdje smo samo pozvali metodu getMovies() koju smo definirali dolje kojoj je
        //zadatak prikupiti informacije s interneta u vezi filmova koje skidamo u JSON
        //formatu
        getMovies();
    }

    //zadatak ove metode je da ode na web, ponasa se kao client i uzme podatke sa servera
    //na tocno onakav nacin kako smo definirali unutar ove metode
    private void getMovies() {

        //ovdje smo napravili objekt od interfejsa ApiInterface kojeg smo nazvali apiInterface u kojeg
        //smo pohranili ovo s desne strane, a s desne strane smo pozvali metodu createService iz klase
        //ServiceGenerator, metodu createService() smo pozvali na ovaj nacin zbog toga sto je ona
        //static metoda, kao parametar ovoj metodi smo predali ApiInterface interfejs
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);

        //Call je interfejs koji ima funkcionalnost da posalje zahtjev za podatkom na webserver i vraca
        //odgovor od tog servera.Svaki poziv ima svoj HTTP protokol
        //<List<Movie>> --> ovaj parametar nam predstavlja tip podataka koji server salje prema klijentu
        //                  znaci u nasem slucaju podaci koje ce nam server poslati su tipa Movie i biti
        //                  ce poredani u List-u
        //na desnoj strani pomocu objekta od interfejsa ApiInterfejs kojeg smo nazvali apiInterface pozivamo
        //metodu getMovies() kojoj smo dodjelili anotaciju @GET, tu anotaciju mozemo zamisliti kao kontejner
        //sa informacijama, znaci iako mi u tom interfejsu nismo deklarirali tijelo te metode mi smo joj objasnili
        //sta ce ona raditi putem anotacije, a ta getMovies() metoda nam vraca podatke u JSON zapisu
        Call<List<Movie>> call = apiInterface.getMovies();


        //znaci rekli smo da je call objekt od interfejsa tipa Call, te mi sada mozemo pozvati metodu enqueue jer
        //se ona nalazi unutar Call interfejsa
        //enqueue() --> ova metoda vrsi operacije nad nekim queue-om, a kada to prevedemo znaci da radi neke operacije
        //              na nekom redu ili nizu, ovu metodu mozemo zamisliti na nacin da idemo kupiti karte za film te
        //              odlucimo da cemo stati u red za kupnju karata(queue) i u trenutku kada smo stali u red onda je
        //              to enqueue, a onoga trenutka kada smo kupili karte i izasli iz reda, to se zove dequeue
        //ova metoda asinkrono salje zahtjev serveru te obavjestava callback o odgovoru kojeg joj je server dao ili
        //ako je doslo do nekog errora u komunikaciji
        call.enqueue(new Callback<List<Movie>>() {

            //kada pozovemo metodu enqueue te joj kao parametar prilozimo interfejs Callback implementiraju se dvije
            //metode onResponse() i onFailure()
            //Response<<>> --> ovaj parametar nam predstavlja odgovor koji se dobio putem HTTP protokola
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                //ovdje unutar if() bloka provjeravamo ako je zahtjev prema serveru bio dobar onda se izvrsava dio
                //koda unutar viticastih zagrada, a ako odgovor servera nije bio dobar onda se izvrsava dio koda
                //unutar else viticastih zagrada
                if (response.isSuccessful()){

                    for (Movie movie: response.body()){
                        movies.add(movie);
                    }
                    //ovdje imamo objekt od RecyclerView adaptera koji je zaduzen za opskrbljivanje RecyclerView-a
                    //podacima te na njega pozivamo metodu notifiyDataSetChanged()
                    //notifyDataSetChanged() --> posto se ovaj dio koda izvrsava ako je odgovor servera potvrdan onda
                    //                           znaci da server salje neke podatke prema klijentu te da ce se ti podaci
                    //                           morati prikazati unutar RecyclerView-a i onda mi dajemo RecyclerView
                    //                           adapteru do znanja da ce doci do unosa novih podataka u recyclerView
                    //                           pozivanjem metode notifyDataSetChanged()
                    moviesAdapter.notifyDataSetChanged();
                }else {
                    //ovaj dio koda se odvija u slucaju ako odgovor servera nije potvrdan, odnosno sadrzi neki exception
                    //te smo ovdje definirali da ce se unutar Logcat-a ispisati poruka s imenom klase te porukom zbog cega
                    //i gdje je doslo do exceptiona
                    Log.e(TAG,response.message());
                }
            }

            //ova metoda je pozvana kada je doslo do pogreske u komunikaciji između klijenta i servera te unutar njenog
            //tijela definiramo sta ce se desiti kada dođe do pogreske
            //Call<<>> --> ovaj parametar nam predstavlja zahtjev koji ce biti predan serveru te odgovor koji ce server
            //             vratiti klijentu
            //Throwable --> ova klasa je nadklasa za sve errore i exceptionse koji se mogu desiti u programu te pomocu
            //              objekta te klase mozemo pozvati metodu getMessage() koja vraca string kao poruku u kojoj se
            //              nalazi objasnjenje exception-a
            //U nasem slucaju mi smo definirali da ako dode do pogreske u komunikaciji između klijenta i servera ce se
            //pomocu Log() metode unutar konzole ispisati poruka unutar koje ce biti ime klase te poruka koja objasnjava
            //zbog cega i gdje je doslo do exception-a
            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.e(TAG,t.getMessage());
            }
        });

    }
}