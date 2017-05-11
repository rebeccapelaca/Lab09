package it.polito.tdp.metrodeparis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.model.CoppiaFermate;
import it.polito.tdp.metrodeparis.model.Fermata;

public class MetroDAO {

	public List<Fermata> getAllFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
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
		
		final String sql = "SELECT id_fermata, nome, coordX, coordY " +
			               "FROM connessione, fermata " +
			               "WHERE connessione.id_stazP = fermata.id_fermata";
		
		List<Fermata> fermate = new ArrayList<Fermata>();
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				Fermata f = new Fermata(rs.getInt("id_fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordX"), rs.getDouble("coordY")));	
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

	public List<CoppiaFermate> listaCoppieFermateCollegate() {
		
		final String sql = "SELECT id_linea, f1.id_fermata AS id1, f2.id_fermata AS id2, f1.nome AS nome1, f2.nome AS nome2, " +
		                   "f1.coordX AS coordX1, f2.coordX AS coordX2, f1.coordY AS coordY1, f2.coordY AS coordY2 " +
			               "FROM connessione, fermata AS f1, fermata AS f2 " +
			               "WHERE connessione.id_stazP = f1.id_fermata AND connessione.id_stazA = f2.id_fermata";
		
		List<CoppiaFermate> coppieFermate = new ArrayList<CoppiaFermate>();
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f1 = new Fermata(rs.getInt("id1"), rs.getString("nome1"), new LatLng(rs.getDouble("coordX1"), rs.getDouble("coordY1")));
				Fermata f2 = new Fermata(rs.getInt("id2"), rs.getString("nome2"), new LatLng(rs.getDouble("coordX2"), rs.getDouble("coordY2")));
				
				CoppiaFermate cf = new CoppiaFermate(f1, f2, rs.getInt("id_linea"));
				
				//if(!coppieFermate.contains(new CoppiaFermate(f2, f1, rs.getInt("id_linea"))))
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
	
	public double getTempo(Fermata f1, Fermata f2, int idLinea) {
		
		int velocita = 0; 
		double tempo;
		double spazio;
		
		final String sql = "SELECT velocita " +
		                   "FROM linea " +
				           "WHERE id_linea = ?";
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, idLinea); 
			
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				velocita = rs.getInt("velocita");
			}
			
			conn.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Database Error");
		}
		
		spazio = LatLngTool.distance(f1.getCoords(), f2.getCoords(), LengthUnit.KILOMETER);
		
		tempo = spazio/velocita;
		
		return tempo;
	}

	public int getPeso(int id_linea) {
		
		int attesa = 0;

		final String sql = "SELECT intervallo " +
                           "FROM linea " +
		                   "WHERE id_linea = ?";
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, id_linea); 
			
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				attesa = rs.getInt("intervallo");
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Database Error");
		}
		
		return attesa;
	}
}
