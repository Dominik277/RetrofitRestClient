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

    private List<Movie> movies;
    private Context context;

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

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.ViewHolder holder, int position) {

        Movie movie = movies.get(position);
        holder.title.setText(movie.getTitle());
        holder.director.setText(movie.getDirector());
        holder.description.setText(movie.getDescription());

    }

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
