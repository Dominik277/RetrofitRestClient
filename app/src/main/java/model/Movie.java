package model;

import com.google.gson.annotations.SerializedName;


//ova klasa nam je primjer POJO klase koju mozemo zamislit kao klasu koja sluzi
//za spremanje nekakvih podataka.Ova klasa je cista Java klasa i ne ovisi o nikakvim
//frameworkovima,third-party librierima,interfejsima ili ista slicno.POJO klasa nam
//moze posluziti i kada pravimo aplikaciju s bazom podataka pa jedna POJO klasa moze
//predstavljati jedan entitet, odnosno jednu tablicu u bazi.POJO klasa se sastoji od
//instance varijabli koje opisuju kakav ce objekt biti ako budemo instacirali ovu klasu
//u nasem slucaju objekt ove klase ce imati svoj id,title,description i director.Posto
//su svi atributi ove klase privatni njima ne mozemo direktno pristupati izvan klase,
//nego smo napravili gettere i settere koji imaju access modifiere tipa public te se njima
//moze pristupiti izvan ove klase.Getteri rade na nacin da kada napravimo objekt ove klase
//i zelimo pristupiti vrijednosti "title" koju taj objekt ima, onda pozovemo getter metodu
//getTitle() koja vraca tu instace varijablu title od objekta.Setteri rade na nacin da
//ako u neki objekt zelimo pohraniti neku vrijednost, npr ako u objekt ove klase zelimo
//pohraniti da je direktor Quentin Tarantino onda kao parametar setter metode unesemo ime
//direktora kojeg zelimo pohraniti u tom objektu
//@SerializedName --> znamo kako nam izgleda JSON zapis podataka prilikom skidanja s interneta
//                    npr. mi cemo u nasem slucaju imati objekt movie i u njegove instance
//                    varijable ce biti pohranjene vrijednosti koje se skinu s interneta u
//                    JSON obliku, znamo kako izgleda JSON zapis.I sada pomocu @SerializedName
//                    mi navodimo kao argument kako zelimo da nam se zovu instance varijable u
//                    JSON zapisu
public class Movie {

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("director")
    private String director;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
