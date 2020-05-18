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
public class ModelUserData {
    private int ID_USER,ID_STIMULUS,HAPPY_USER,SAD_USER,DISGUST_USER,SURPRISED_USER,ANGRY_USER;
    private String NAMA_USER,JENIS_KELAMIN,EKSPRESI_HASIL,KETERANGAN_USER,VIDEO_USER;

    public ModelUserData() {
    }

    public int getID_USER() {
        return ID_USER;
    }

    public void setID_USER(int ID_USER) {
        this.ID_USER = ID_USER;
    }

    public int getID_STIMULUS() {
        return ID_STIMULUS;
    }

    public void setID_STIMULUS(int ID_STIMULUS) {
        this.ID_STIMULUS = ID_STIMULUS;
    }

    public int getHAPPY_USER() {
        return HAPPY_USER;
    }

    public void setHAPPY_USER(int HAPPY_USER) {
        this.HAPPY_USER = HAPPY_USER;
    }

    public int getSAD_USER() {
        return SAD_USER;
    }

    public void setSAD_USER(int SAD_USER) {
        this.SAD_USER = SAD_USER;
    }

    public int getDISGUST_USER() {
        return DISGUST_USER;
    }

    public void setDISGUST_USER(int DISGUST_USER) {
        this.DISGUST_USER = DISGUST_USER;
    }

    public int getSURPRISED_USER() {
        return SURPRISED_USER;
    }

    public void setSURPRISED_USER(int SURPRISED_USER) {
        this.SURPRISED_USER = SURPRISED_USER;
    }

    public int getANGRY_USER() {
        return ANGRY_USER;
    }

    public void setANGRY_USER(int ANGRY_USER) {
        this.ANGRY_USER = ANGRY_USER;
    }

    public String getNAMA_USER() {
        return NAMA_USER;
    }

    public void setNAMA_USER(String NAMA_USER) {
        this.NAMA_USER = NAMA_USER;
    }

    public String getJENIS_KELAMIN() {
        return JENIS_KELAMIN;
    }

    public void setJENIS_KELAMIN(String JENIS_KELAMIN) {
        this.JENIS_KELAMIN = JENIS_KELAMIN;
    }

    public String getEKSPRESI_HASIL() {
        return EKSPRESI_HASIL;
    }

    public void setEKSPRESI_HASIL(String EKSPRESI_HASIL) {
        this.EKSPRESI_HASIL = EKSPRESI_HASIL;
    }

    public String getKETERANGAN_USER() {
        return KETERANGAN_USER;
    }

    public void setKETERANGAN_USER(String KETERANGAN_USER) {
        this.KETERANGAN_USER = KETERANGAN_USER;
    }

    public String getVIDEO_USER() {
        return VIDEO_USER;
    }

    public void setVIDEO_USER(String VIDEO_USER) {
        this.VIDEO_USER = VIDEO_USER;
    }
    
}
