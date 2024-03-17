package Entity;


public class Inserzionista {


    public Inserzionista(Long idInserzionista, String username, String password, Contatto contatto) {
        super();
        this.idInserzionista = idInserzionista;
        this.username = username;
        this.password = password;
        //this.listaAnnunci = listaAnnunci;
        this.contatto = contatto;
    }



    public Long getIdInserzionista() {
        return this.idInserzionista;
    }

    public void setIdInserzionista(Long idInserzionista) {
        this.idInserzionista = idInserzionista;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


        /* 
    public List<Annuncio> getListaAnnunci() {
        return this.listaAnnunci;
    }

    public void setListaAnnunci(List<Annuncio> listaAnnunci) {
        this.listaAnnunci = listaAnnunci;
    }
    */


    public Contatto getContatto() {
        return this.contatto;
    }

    public void setContatto(Contatto contatto) {
        this.contatto = contatto;
    }

    @Override
    public String toString() {
        return "{" +
            " idIINS='" + getIdInserzionista() + "'\n" +
            " username='" + getUsername() + "'\n" +
             " password= " + "'" + getPassword() +"}";
    }


    private Long idInserzionista;
    private String username;
    private String password;
    //private List<Annuncio> listaAnnunci;
    private Contatto contatto;
}

