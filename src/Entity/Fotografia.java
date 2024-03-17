package Entity;
import java.io.InputStream;

public class Fotografia {

    private long idFotografia;
    private long idAnnuncio;
    private InputStream path;

    public Fotografia(long idFotografia, long idAnnuncio, InputStream path) {
        super();
        this.idFotografia = idFotografia;
        this.idAnnuncio = idAnnuncio;
        this.path = path;
    }

    
    public long getIdFotografia(){
        return this.idFotografia;
    }

    public void setIdFotografia(long idFotografia){
        this.idFotografia = idFotografia;
    }

    public long getIdAnnuncio(){
        return this.idAnnuncio;
    }

    public void setIdAnnuncio(long idAnnuncio){
        this.idAnnuncio = idAnnuncio;
    }

    public InputStream getPath(){
        return this.path;
    }

    public void setPath(InputStream path){
        this.path = path;
    }


    //@Override
   // public String toString() {
  //      return "{ path= " + getPath() + "}";
  //  }


}
