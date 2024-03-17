package Entity;



public class UtenteRegistrato {
    
    

    public UtenteRegistrato(long idUtenteRegistrato, String Nome, String Cognome, Contatto contatto) {
        super();
        this.idUtenteRegistrato = idUtenteRegistrato;
        this.Nome = Nome;
        this.Cognome = Cognome;
        this.contatto = contatto;
    }
    

    public long getIdUtenteRegistrato() {
        return this.idUtenteRegistrato;
    }

    public void setIdUtenteRegistrato(long idUtenteRegistrato) {
        this.idUtenteRegistrato = idUtenteRegistrato;
    }

    public String getNome() {
        return this.Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getCognome() {
        return this.Cognome;
    }

    public void setCognome(String Cognome) {
        this.Cognome = Cognome;
    }

    public Contatto getContatto() {
        return this.contatto;
    }

    public void setContatto(Contatto contatto) {
        this.contatto = contatto;
    }

    private long idUtenteRegistrato;
    private String Nome;
    private String Cognome;
    private Contatto contatto;   

}
