/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formulario;

/**
 *
 * @author Magnero
 */
public class Meta {

    /**
     * @return the Nome
     */
    public String getNome() {
        return Nome;
    }

    /**
     * @param Nome the Nome to set
     */
    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    /**
     * @return the Tipo
     */
    public String getTipo() {
        return Tipo;
    }

    /**
     * @param Tipo the Tipo to set
     */
    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    /**
     * @return the extra
     */
    public String getExtra() {
        return extra;
    }

    /**
     * @param extra the extra to set
     */
    public void setExtra(String extra) {
        this.extra = extra;
    }
    
    private String Nome;
    private String Tipo;
    private String extra;
    
}
