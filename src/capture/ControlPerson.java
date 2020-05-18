package capture;

import util.Koneks;
import util.ModeloTabela;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import records.model.ModelStimulus;
import records.model.ModelUserData;

public class ControlPerson {

    Koneks conecta = new Koneks();
    public void insert(ModelPerson mod) {
        
String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis()));

        try {
            conecta.conexao();
            PreparedStatement pst = conecta.conn.prepareStatement("INSERT INTO dataset_register (id_regis, nama_lengkap, kamera, jarak_layar, ekspresi, tgl_regis, keterangan) VALUES (?, ?, ?, ?, ?, ?, ?)");
            pst.setInt(1, mod.getId_regis());
            pst.setString(2, mod.getNama_lengkap());
            pst.setString(3, mod.getKamera());
            pst.setInt(4, mod.getJarak_layar());
            pst.setString(5, mod.getEkspresi());
            pst.setString(6, date);
            pst.setString(7, mod.getKeterangan());
//            pst.setString(8, mod.getLinkedin());
//            pst.setString(9, mod.getGithub());
//            pst.setString(10, date);
            pst.executeUpdate();
            System.out.println("Dados do(a): " + mod.getEkspresi() + " cadastrados");
            conecta.desconecta();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
    }
    public void insertStimulus(ModelStimulus mod) {
        

        try {
            conecta.conexao();
            PreparedStatement pst = conecta.conn.prepareStatement("INSERT INTO stimulus (NAMA_STIMULUS, SOURCE_STIMULUS, EKSPRESI_STIMULUS) VALUES (?, ?, ?)");
            pst.setString(1, mod.getNama_stimulus());
            pst.setString(2, mod.getSource_stimulus());
            pst.setString(3, mod.getEkspresi_stimulus());
            
//            pst.setString(8, mod.getLinkedin());
//            pst.setString(9, mod.getGithub());
//            pst.setString(10, date);
            pst.executeUpdate();
//            System.out.println("Dados do(a): " + mod.getEkspresi() + " cadastrados");
            conecta.desconecta();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
    }
    public void insertUserData(ModelUserData mod) {
        

        try {
            conecta.conexao();
            PreparedStatement pst = conecta.conn.prepareStatement("INSERT INTO userdata (ID_STIMULUS,NAMA_USER,JENIS_KELAMIN) VALUES (?, ?, ?)");
            pst.setInt(1, mod.getID_STIMULUS());
            pst.setString(2, mod.getNAMA_USER());
            pst.setString(3, mod.getJENIS_KELAMIN());
            
//            pst.setString(8, mod.getLinkedin());
//            pst.setString(9, mod.getGithub());
//            pst.setString(10, date);
            pst.executeUpdate();
//            System.out.println("Dados do(a): " + mod.getEkspresi() + " cadastrados");
            conecta.desconecta();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
    }

    public void update(ModelPerson mod, int id_regis) {
        conecta.conexao();
        try {
            PreparedStatement pst = conecta.conn.prepareStatement("UPDATE dataset_register SET nama_lengkap= ?, kamera= ?, jarak_layar= ?, ekspresi= ?, keterangan= ? WHERE id_regis=?");
            pst.setString(1, mod.getNama_lengkap());
            pst.setString(2, mod.getKamera());
            pst.setInt(3, mod.getJarak_layar());
            pst.setString(4, mod.getEkspresi());
//            pst.setString(5, date);
            pst.setString(5, mod.getKeterangan());
//            pst.setString(7, mod.getLinkedin());
//            pst.setString(8, mod.getGithub());
            pst.setInt(6, id_regis);
            pst.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar dados\n ERRO: " + ex);
        }
//        conecta.desconecta();
    }
    public void updateStimulus(ModelStimulus mod, int id_stimulus) {
        conecta.conexao();
        try {
            PreparedStatement pst = conecta.conn.prepareStatement("UPDATE stimulus SET NAMA_STIMULUS= ?, SOURCE_STIMULUS= ?, EKSPRESI_STIMULUS= ? WHERE ID_STIMULUS=?");
            pst.setString(1, mod.getNama_stimulus());
            pst.setString(2, mod.getSource_stimulus());
            pst.setString(3, mod.getEkspresi_stimulus());
            
            pst.setInt(4, id_stimulus);
            pst.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Jumlah: " + ex);
        }
//        conecta.desconecta();
    }

    public void delete(int id) {
        conecta.conexao();
        try {
            PreparedStatement pst = conecta.conn.prepareStatement("DELETE FROM dataset_register WHERE id_regis= '" + id + "'");
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Hapus!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error!");
        } finally {
            conecta.desconecta();
        }
    }
    public void deleteStimulus(int id) {
        conecta.conexao();
        try {
            PreparedStatement pst = conecta.conn.prepareStatement("DELETE FROM stimulus WHERE ID_STIMULUS= '" + id + "'");
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Hapus!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error!");
        } finally {
            conecta.desconecta();
        }
    }
    public void deleteUser(int id) {
        conecta.conexao();
        try {
            PreparedStatement pst = conecta.conn.prepareStatement("DELETE FROM userdata WHERE ID_USER= '" + id + "'");
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Hapus!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error!");
        } finally {
            conecta.desconecta();
        }
    }
    
    public void getUserData(String SQL, JTable tabela) {
        String id = null;

        conecta.conexao();
        ArrayList dados = new ArrayList();
        String[] Colunas = new String[]{"", "ID", "Nama User", "Jenis Kelamin","Bahagia","Sedih","Jijik","Terkejut","Marah", "Ekspresi User", "Ekspresi Stimulus", "Video User","Video Stimulus","Keterangan"};
        conecta.executaSQL(SQL);
        try {
            conecta.rs.first();
            do {
                dados.add(new Object[]{
                    "",
                    conecta.rs.getString("ID_USER"),
                    conecta.rs.getString("NAMA_USER"),
                    conecta.rs.getString("JENIS_KELAMIN"),
                    conecta.rs.getString("HAPPY_USER"),
                    conecta.rs.getString("SAD_USER"),
                    conecta.rs.getString("DISGUST_USER"),
                    conecta.rs.getString("SURPRISED_USER"),
                    conecta.rs.getString("ANGRY_USER"),
                    conecta.rs.getString("EKSPRESI_HASIL"),
                    conecta.rs.getString("EKSPRESI_STIMULUS"),
                    conecta.rs.getString("VIDEO_USER"),
                    conecta.rs.getString("SOURCE_STIMULUS"),
                    conecta.rs.getString("KETERANGAN_USER")
                });
            } while (conecta.rs.next());
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(rootPane, "Lista de Cadastro Vazia!");
        } finally {
            conecta.desconecta();
        }

        ModeloTabela modelo = new ModeloTabela(dados, Colunas);
        tabela.setModel((TableModel) modelo);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(0);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(50);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(200);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(200);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(6).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(7).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(8).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(9).setPreferredWidth(200);
        tabela.getColumnModel().getColumn(10).setPreferredWidth(200);
        tabela.getColumnModel().getColumn(11).setPreferredWidth(200);
        tabela.getColumnModel().getColumn(12).setPreferredWidth(200);
        tabela.getColumnModel().getColumn(13).setPreferredWidth(200);
    }
    
    public void getDatasetData(String SQL, JTable tabela) {
        String id = null;

        conecta.conexao();
        ArrayList dados = new ArrayList();
        String[] Colunas = new String[]{"","ID", "Nama Lengkap", "Kamera", "Jarak Layar (cm)", "Ekspresi", "Tanggal Registrasi","Keterangan"};
        conecta.executaSQL(SQL);
        try {
            conecta.rs.first();
            do {
                dados.add(new Object[]{
                    "",
                    conecta.rs.getInt("id_regis"),
                    conecta.rs.getString("nama_lengkap"),
                    conecta.rs.getString("kamera"),
                    conecta.rs.getString("jarak_layar"),
                    conecta.rs.getString("ekspresi"),
                    conecta.rs.getString("tgl_regis"),
                    conecta.rs.getString("keterangan")
                });
            } while (conecta.rs.next());
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(rootPane, "Lista de Cadastro Vazia!");
        } finally {
            conecta.desconecta();
        }

        ModeloTabela modelo = new ModeloTabela(dados, Colunas);
        tabela.setModel((TableModel) modelo);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(0);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(50);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(200);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(200);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(200);
        tabela.getColumnModel().getColumn(6).setPreferredWidth(300);
        tabela.getColumnModel().getColumn(7).setPreferredWidth(500);
    }
    
    
    public void getStimulusData(String SQL, JTable tabela) {
        String id = null;

        conecta.conexao();
        ArrayList dados = new ArrayList();
        String[] Colunas = new String[]{"","ID", "Nama Stimulus", "Ekspresi Stimulus", "Video Stimulus"};
        conecta.executaSQL(SQL);
        try {
            conecta.rs.first();
            do {
                dados.add(new Object[]{
                    "",
                    conecta.rs.getInt("ID_STIMULUS"),
                    conecta.rs.getString("NAMA_STIMULUS"),
                    conecta.rs.getString("EKSPRESI_STIMULUS"),
                    conecta.rs.getString("SOURCE_STIMULUS")
                });
            } while (conecta.rs.next());
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(rootPane, "Lista de Cadastro Vazia!");
        } finally {
            conecta.desconecta();
        }

        ModeloTabela modelo = new ModeloTabela(dados, Colunas);
        tabela.setModel((TableModel) modelo);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(0);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(50);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(300);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(300);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(450);
        
    }

    public void preencherTabela(String SQL, JTable tabela) {
        String id = null;

        conecta.conexao();
        ArrayList dados = new ArrayList();
        String[] Colunas = new String[]{"", "ID", "Nama Lengkap", "Kamera", "Jarak Layar", "Ekspresi", "Keterangan"};
        conecta.executaSQL(SQL);
        try {
            conecta.rs.first();
            do {
                dados.add(new Object[]{
                    "",
                    conecta.rs.getString("id_regis"),
                    conecta.rs.getString("nama_lengkap"),
                    conecta.rs.getString("kamera"),
                    conecta.rs.getString("jarak_layar"),
                    conecta.rs.getString("ekspresi"),
                    conecta.rs.getString("keterangan")});
            } while (conecta.rs.next());
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(rootPane, "Lista de Cadastro Vazia!");
        } finally {
            conecta.desconecta();
        }

        ModeloTabela modelo = new ModeloTabela(dados, Colunas);
        tabela.setModel((TableModel) modelo);
        tabela.getColumnModel().getColumn(0).setCellRenderer(new ControlPerson.ImageRenderer());
        tabela.getColumnModel().getColumn(1).setMaxWidth(0);
        tabela.getColumnModel().getColumn(1).setMinWidth(0);
        tabela.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(0);
        tabela.getTableHeader().getColumnModel().getColumn(1).setMinWidth(0);
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    class ImageRenderer implements TableCellRenderer {

        public JLabel lbl = new JLabel();

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            try {
                Object text = table.getValueAt(row, 1);
                File image = new File("C:\\photos\\person." + text + ".1.jpg");
                String path = image.getAbsolutePath();
                ImageIcon i = new ImageIcon(new ImageIcon(String.valueOf(path)).getImage().getScaledInstance(lbl.getWidth() + 50, lbl.getHeight() + 50, Image.SCALE_SMOOTH));
                lbl.setIcon(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lbl;
        }
    }

    public void editar(ModelPerson mod, int id) {
        conecta.conexao();
        try {
            PreparedStatement pst = conecta.conn.prepareStatement("UPDATE apto SET apto= ? WHERE id=? ");
            pst.setString(1, mod.getEkspresi());
            pst.setInt(2, id);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Alterado com Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar apartamento. Tente Novamente!");
        } finally {
            conecta.desconecta();
        }
    }

}
