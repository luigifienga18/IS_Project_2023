package Entity;

import java.sql.Date;
import java.util.List;

public class Annuncio {


    public Annuncio(Long idAnnuncio, Date dataDiPubblicazione, String città, String cap, Double numeroMetriQuadri, int numeroVani, String descrizione, Double prezzo, Tipologia tipologia, Stato stato, List<Fotografia> listaFotografie, Inserzionista inserzionista) {
        super();
        this.idAnnuncio = idAnnuncio;
        this.dataDiPubblicazione = dataDiPubblicazione;
        this.città = città;
        this.cap = cap;
        this.numeroMetriQuadri = numeroMetriQuadri;
        this.numeroVani = numeroVani;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.tipologia = tipologia;
        this.stato = stato;
        this.listaFotografie = listaFotografie;
        this.inserzionista = inserzionista;
    }


    public Annuncio() {
    }

    


    public Long getIdAnnuncio(){
        return this.idAnnuncio;
    }

    public void setIdAnnuncio(Long id){
        this.idAnnuncio = id;
    }

    public Date getDataDiPubblicazione(){
        return this.dataDiPubblicazione;
    }

    public void setDataDiPubblicazione(Date date){
        this.dataDiPubblicazione = date;
    }

    public String getCittà(){
        return this.città;
    }

    public void setCittà(String città){
        this.città = città;
    }

    public String getCap(){
        return this.cap;
    }

    public void setCap(String CAP){
        this.cap = CAP;
    }

    public Double getNumeroMetriQuadri(){
        return this.numeroMetriQuadri;
    }

    public void setNumeroMetriQuadri(Double nr_m2){
        this.numeroMetriQuadri = nr_m2;
    }

    public int getNumeroVani(){
        return this.numeroVani;
    }

    public void setNumeroVani(int nr_v){
        this.numeroVani = nr_v;
    }

    public String getDescrizione(){
        return this.descrizione;
    }

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }

    public Double getPrezzo(){
        return this.prezzo;
    }

    public void setPrezzo(Double prezzo){
        this.prezzo = prezzo;
    }


    public Tipologia getTipologia() {
        return this.tipologia;
    }

    public void setTipologia(Tipologia tipologia) {
        this.tipologia = tipologia;
    }

    public Stato getStato() {
        return this.stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }


    public List<Fotografia> getListaFotografie(){
        return this.listaFotografie;
    }

    public void setListaFotografie(List<Fotografia> listaFoto){
        this.listaFotografie = listaFoto;
    }

    public Inserzionista getInserzionista(){
        return this.inserzionista;
    }

    public void setInserzionista(Inserzionista inserzionista){
        this.inserzionista = inserzionista;
    }


    @Override
    public String toString() {

        String s = "";
        String m = " m\u00B2";
        String p = "$";//\u20AC


       if(tipologia == Tipologia.AFFITTO)
            s=" al mese";

        return "{" +
            "idAnnuncio= '" + getIdAnnuncio() + "'\n" +
            "dataDiPubblicazione= '" + getDataDiPubblicazione() + "'\n" +
            "città= '" + getCittà() + "'\n" +
            "cap= '" + getCap() + "'\n" +
            "numeroMetriQuadri= '" + getNumeroMetriQuadri() + m + "'\n" +
            "numeroVani= '" + getNumeroVani() + "'\n" +
            "descrizione= '" + getDescrizione() + "'\n" +
            "prezzo= '" + p.toString() + getPrezzo() + s.toString() +"'\n" +
            "tipologia= '" + getTipologia() + "'\n" +
            "stato= '" + getStato() + "'}\n";
    }


    // private String toStringD() {
    //     StringBuilder sb = new StringBuilder();
    //     int charCount = 0;
    //     for (int i = 0; i < descrizione.length(); i++) {
    //         sb.append(descrizione.charAt(i));
    //         charCount++;
    //         if (charCount == 136) {
    //             sb.append("\n");
    //             charCount = 0;
    //         }
    //     }
    //     return sb.toString();
    // }




    private Long idAnnuncio;
    private Date dataDiPubblicazione;
    private String città;
    private String cap;
    private Double numeroMetriQuadri;
    private int numeroVani;
    private String descrizione;
    private Double prezzo;
    private Tipologia tipologia;
    private Stato stato;
    private List<Fotografia> listaFotografie = null;
    private Inserzionista inserzionista;

}
