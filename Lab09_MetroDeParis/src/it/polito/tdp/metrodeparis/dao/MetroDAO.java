package it.polito.tdp.metrodeparis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metrodeparis.model.CoppiaFermate;
import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.Linea;

public class MetroDAO {

	public List<Fermata> getAllFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
	}
	
    public List<Fermata> getVertici() {
		
		final String sql = "SELECT DISTINCT id_linea, id_fermata, nome, coordX, coordY " +
				           "FROM connessione, fermata " +
				           "WHERE connessione.id_stazP = fermata.id_fermata";
		
		List<Fermata> fermate = new ArrayList<Fermata>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				Linea l = this.getLinea(rs.getInt("id_linea"));
				Fermata f = new Fermata(rs.getInt("id_fermata"), rs.getString("fermata.nome"), new LatLng(rs.getDouble("coordX"), rs.getDouble("coordY")), l);	
				fermate.add(f);			
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
		
	}
    
    private Linea getLinea(int idLinea) {
    	
    	Linea l = null;
    	
		
		final String sql = "SELECT id_linea, nome, velocita, intervallo " +
		                   "FROM linea " +
				           "WHERE id_linea = ?";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, idLinea);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				
				l = new Linea(rs.getInt("id_linea"), rs.getString("nome"), rs.getInt("velocita"), rs.getInt("intervallo"));

			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return l;
		
	}

	public List<CoppiaFermate> listaConnessioni() {
		
		final String sql = "SELECT  id_linea, id_connessione, f1.id_fermata AS id1, f2.id_fermata AS id2, f1.nome AS nome1, f2.nome AS nome2, " +
                                   "f1.coordX AS coordX1, f2.coordX AS coordX2, f1.coordY AS coordY1, f2.coordY AS coordY2 " +
                           "FROM connessione, fermata AS f1, fermata AS f2 " +
                           "WHERE connessione.id_stazP = f1.id_fermata AND connessione.id_stazA = f2.id_fermata";
			               
		
		List<CoppiaFermate> coppieFermate = new ArrayList<CoppiaFermate>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				Linea l = this.getLinea(rs.getInt("id_linea"));
				Fermata f1 = new Fermata(rs.getInt("id1"), rs.getString("nome1"), new LatLng(rs.getDouble("coordX1"), rs.getDouble("coordY1")), l);
				Fermata f2 = new Fermata(rs.getInt("id2"), rs.getString("nome2"), new LatLng(rs.getDouble("coordX2"), rs.getDouble("coordY2")), l);
				CoppiaFermate cf = new CoppiaFermate(f1, f2, l, rs.getInt("id_connessione"));
				coppieFermate.add(cf);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return coppieFermate;
		
	}
}
