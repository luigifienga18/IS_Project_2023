package Entity;

import java.util.List;

public class MailingList {


    public MailingList(String idMailingList) {
        super();
        this.idMailingList = idMailingList;
    }


    public String getIdMailingList() {
        return this.idMailingList;
    }

    public void setIdMailingList(String idMailingList) {
        this.idMailingList = idMailingList;
    }

    public List<String> getListaEmails() {
        return this.listaEmails;
    }

    public void setListaEmails(List<String> listaEmails) {
        this.listaEmails = listaEmails;
    }


    
    private String idMailingList;
    private List<String> listaEmails;
}
