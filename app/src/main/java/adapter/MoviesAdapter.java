package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dagger2.and.retrofit.R;
import model.Movie;

//ova klasa nam je klasicna klasa koja sluzi kao adapter za RecyclerView.U nasem item-view
//layout-u smo odredili onako kako zelimo da nam izgleda jedan redak unutar RecyclerView-a
//te sada unutar ove klase definiramo adapter koji je zasluzen da opskrbi RecyclerView sa
//svim podacima koji su mu potrebni te da kaze RecyclerView-u na koji nacin da prikaze te
//podatke.Unutar ove klase također imamo i tri metode koje smo morali implmentirati jer se
//one nalaze unutar abstraktne klase RecyclerView.Adapter.Također imamo jos jednu klasu unutar
//ove a to je ViewHolder klasa u koju pohranjujemo sve nase view-ove koji se nalaze u jednom
//retku
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    //ovdje smo samo definirali List tip podataka te da cemo u taj List pohranjivati podatke
    //tipa Movie, Movie nam je isto klasa kao i String,Double... te mozemo pohranjivati podatke
    //ovog tipa.Movie klasa u sebi sadrzi neke member varijeble od kojih se moze napraviti objekt
    //tako da ovaj tip podatka u sebi sadrzava te member varijable i te podatke tipa Movie cemo
    //pohranjivati unutar objekta koji smo nazvali movies.List nam je interfejs unutar kojeg se
    //pohranjuju podaci pohranjuju u nekakvom pravilnom redosljedu
    private List<Movie> movies;

    //ovdje smo samo naveli ime preko kojeg cemo se obracati objektu u memoriji tipa Context, taj
    //objekt u memoriji jos uvijek nije stvoren nego smo samo za sad njegovu referencu stvorili
    private Context context;


    //ovo je konstruktor ove klase koji nam sluzi da preda podatke objektu kada se objekt bude inicijalizirao
    //znaci i prije smo rekli da adapter mora imati podatke koje RecylcerView treba prikazati, odnosno da mu
    //on predaje te podatke i sada kad mi pravimo objekt ove MoviesAdapter klase mi njemu kao argument
    //konstruktora dajemo Listu "moviesa" odnosno listu svih tih filmova koje smo dobili pomocu klase Movie
    //i sada taj objekt koji se kreira od ove klase u sebi ima podatke koje RecyclerView treba prikazati i
    //onda se taj objekt samo prilozi RecylcerView-u preko setAdapter() metode.
    public MoviesAdapter(Context context,List<Movie> movies) {
        this.movies = movies;
        this.context = context;
    }

    @NonNull
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie,parent,false);

        return new ViewHolder(itemView);
    }

    //ovo ce mi biti lakse ako zapamtim na sljedeci nacin, znaci kada koristimo RecyclerView onda imamo samo tipa
    //10 redove na screen-u i mozda dva koja su u pripravi koji se recikliraju i svaki put kada jedan redak nestane
    //sa screen-a on ce doci ponovno na screen jer se reciklira, ali ovoga puta bez podataka i upravo je to zadaca
    //ove metode, da onaj redak koji treba biti prikazan sljedeci na zaslonu napuni podacima
    //holder -->
    //position --> ovaj parametar predstavlja poziciju elementa unutar neke kolekcije podataka gdje smo spremili
    //             u nasem slucaju broj elementa unutar List-a
    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.ViewHolder holder, int position) {

        //
        Movie movie = movies.get(position);
        holder.title.setText(movie.getTitle());
        holder.director.setText(movie.getDirector());
        holder.description.setText(movie.getDescription());

    }

    //ova metoda nam sluzi kako bi odredila koliko ima elemenata u nekoj kolekciji koju mi koristimo
    //znaci, movies smo rekli da ce ih biti vise i da ce svaki biti poredan unutar List-a, znaci ako
    //imamo unutar List-a tri razlicita movies objekta onda ova metoda vraca broj tri i tako dalje
    @Override
    public int getItemCount() {
        return movies.size();
    }

    //ovo je jedna nested klasa ViewHolder koja nije dio android SDK nego ju sami implementiramo
    //nju mozemo zamisliti kao neku helper klasu koja nam je kao memorija te u nju pohranjujemo
    //sve view-ove koje cemo koristit u RecyclerView-u, znaci sve one view-ove koje cemo koristiti
    //unutar jednog retka moramo implementirati unutar ove klase te cemo ih kasnije referencirati
    //na osnovu ovih imena koje smo ovdje naveli
    public class ViewHolder extends RecyclerView.ViewHolder{

        //ovdje smo naveli varijeble i njihove tipove podataka, ove varijable su sve tipa TextView
        //zbog toga sto u nasem jednom retku unutar RecyclerView-a imamo tri view-a i svi su tipa
        //TextView zbog toga ih ovdje referenciramo kako bi ih kasnije mogli upotrebljavati
        private TextView title;
        private TextView director;
        private TextView description;

        //ovo je konstruktor u cijem tijelu smo java objektima dodjelili atribute iz XML-a
        public ViewHolder(@NonNull View itemView) {

            //?????????????????????????????
            super(itemView);

            //u sljedeca tri reda smo atribute iz XML-a dodjelili java objektima, odnosno
            //kada budemo htjeli vrsiti neke operacije nad tim atributima iz XML-a onda ih
            //dozivamo preko ovog imena
            //findViewById() --> ova metoda nam sluzi kako bi dohvatili XML atribute te ih
            //                   pohranili unutar objekta kojeg smo mu definirali, znaci npr
            //                   prvi TextView iz XML-a nam se zove title i mi taj TextView
            //                   pomocu ove metode pohranjujemo u title objekt
            //                   i sada kad god zelimo vrsiti neke operacije nad tim TextView-om
            //                   onda to izvodimo preko title objekta
            title = itemView.findViewById(R.id.title);
            director = itemView.findViewById(R.id.director);
            description = itemView.findViewById(R.id.description);
        }
    }

}
