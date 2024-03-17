package Utility;
import java.util.List;

import Entity.*;

public class DatiInserzionistaAnnuncio {


    public DatiInserzionistaAnnuncio(List<Annuncio> listaAnnunci, List<String> listaContatti) {
        this.listaAnnunci = listaAnnunci;
        this.listaContattiInserzionisti = listaContatti;
    }



    public List<Annuncio> getListaAnnunci() {
        return this.listaAnnunci;
    }

    public void setListaAnnunci(List<Annuncio> listaAnnunci) {
        this.listaAnnunci = listaAnnunci;
    }

    public List<String> getListaContattiInserzionisti() {
        return this.listaContattiInserzionisti;
    }

    public void setListaContattiInserzionisti(List<String> listaContatti) {
        this.listaContattiInserzionisti = listaContatti;
    }


    @Override
    public String toString() {

        String string = "";
        if (listaContattiInserzionisti != null){

            for(int i=0;i<listaAnnunci.size();i++)
            {   
                Integer k = i+1;
                string=string.concat("---------"+k.toString()+"° Annuncio---------\n");
                string=string.concat(listaAnnunci.get(i).toString()+ "\n" + listaContattiInserzionisti.get(i).toString()+ "\n");
            }

        }
        else{
            for(int i=0;i<listaAnnunci.size();i++)
            {
                Integer k = i+1;
                string=string.concat("---------"+k.toString()+"° Annuncio---------\n");
                string=string.concat(listaAnnunci.get(i).toString()+ "\n");
            }
        }

        return string;
    }



    
    private List<Annuncio> listaAnnunci;
    private List<String> listaContattiInserzionisti;
}
