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

        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<List<Movie>> call = apiInterface.getMovies();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful()){
                    for (Movie movie: response.body()){
                        movies.add(movie);
                    }
                    moviesAdapter.notifyDataSetChanged();
                }else {
                    Log.e(TAG,response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.e(TAG,t.getMessage());
            }
        });

    }
}