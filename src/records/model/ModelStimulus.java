/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package records.model;

/**
 *
 * @author ACER
 */
public class ModelStimulus {
    private String nama_stimulus,source_stimulus,ekspresi_stimulus;
    private int id_stimulus;
    public ModelStimulus() {
    }

    public ModelStimulus(String nama_stimulus, String source_stimulus, String ekspresi_stimulus, int id_stimulus) {
        this.nama_stimulus = nama_stimulus;
        this.source_stimulus = source_stimulus;
        this.ekspresi_stimulus = ekspresi_stimulus;
        this.id_stimulus = id_stimulus;
    }

    public String getNama_stimulus() {
        return nama_stimulus;
    }

    public void setNama_stimulus(String nama_stimulus) {
        this.nama_stimulus = nama_stimulus;
    }

    public String getSource_stimulus() {
        return source_stimulus;
    }

    public void setSource_stimulus(String source_stimulus) {
        this.source_stimulus = source_stimulus;
    }

    public String getEkspresi_stimulus() {
        return ekspresi_stimulus;
    }

    public void setEkspresi_stimulus(String ekspresi_stimulus) {
        this.ekspresi_stimulus = ekspresi_stimulus;
    }

    public int getId_stimulus() {
        return id_stimulus;
    }

    public void setId_stimulus(int id_stimulus) {
        this.id_stimulus = id_stimulus;
    }

    
    
}
