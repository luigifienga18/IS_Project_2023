package Entity;

public class Contatto {
    

    public Contatto(String email, String cellulare, Long idutenteregistrato, Long idinserzionista) {
        super();
        this.email = email;
        this.cellulare = cellulare;
        this.idutenteregistrato=idutenteregistrato;
        this.idinserzionista=idinserzionista;
    }
    //ciao 20

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellulare() {
        return this.cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public Long getIDutenteregistrato(){
        return this.idutenteregistrato;
    }

    public void setIDutenteregistrato(Long id){
        this.idutenteregistrato=id;
    }

    public Long getIDinserzionista(){
        return this.idinserzionista;
    }

    public void setIDinserzionista(Long id){
        this.idinserzionista=id;
    }

    @Override
    public String toString() {
        return "{" +
            " email='" + getEmail() + "'\n" +
            " cellulare='" + getCellulare() + "'\n" +
             " IDUR: " + "'" + getIDutenteregistrato() + "'\n" +  
             " IDI: " + "'" +getIDinserzionista() + "'" +"}";
    }



    private String email;
    private String cellulare;
    private Long idutenteregistrato;
    private Long idinserzionista;
}
