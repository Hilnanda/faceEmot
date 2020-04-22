package capture;

public class ModelPerson {

    private int id_regis, jarak_layar;
    private String nama_lengkap, kamera, ekspresi, tgl_regis, keterangan ,image;

    public ModelPerson() {
    }

    public ModelPerson(String nama_lengkap, String image) { //LastPerson
        this.nama_lengkap = nama_lengkap;
//        this.office = office;
        this.image = image;
    }

    public ModelPerson(int id_regis, String nama_lengkap, String kamera, int jarak_layar, String ekspresi, String tgl_regis, String keterangan) {
        this.id_regis = id_regis;
        this.nama_lengkap = nama_lengkap;
        this.kamera = kamera;
        this.jarak_layar = jarak_layar;
//        this.office = office;
        this.ekspresi = ekspresi;
        this.tgl_regis = tgl_regis;
        this.keterangan = keterangan;
        
    }

    public int getId_regis() {
        return id_regis;
    }

    public void setId_regis(int id_regis) {
        this.id_regis = id_regis;
    }

    public int getJarak_layar() {
        return jarak_layar;
    }

    public void setJarak_layar(int jarak_layar) {
        this.jarak_layar = jarak_layar;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getKamera() {
        return kamera;
    }

    public void setKamera(String kamera) {
        this.kamera = kamera;
    }

    public String getEkspresi() {
        return ekspresi;
    }

    public void setEkspresi(String ekspresi) {
        this.ekspresi = ekspresi;
    }

    public String getTgl_regis() {
        return tgl_regis;
    }

    public void setTgl_regis(String tgl_regis) {
        this.tgl_regis = tgl_regis;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
