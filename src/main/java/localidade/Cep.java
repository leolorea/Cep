package localidade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Cep {

	private List<String> listaEstados;
	private List<Integer> listaFaixaMin;
	private List<Integer> listaFaixaMax;

	public Cep() {

		Conexao conexao = new Conexao();
		Connection conn = conexao.conectar();
		PreparedStatement state;

		carregarDados(conn);

		List<String> listaEst = new ArrayList<String>();
		List<Integer> listaMin = new ArrayList<Integer>();
		List<Integer> listaMax = new ArrayList<Integer>();

		try {

			state = conn.prepareStatement("SELECT * FROM localidade");

			ResultSet resSet = state.executeQuery();

			while (resSet.next()) {
				String estado = resSet.getString("estado");
				Integer faixaMin = resSet.getInt("faixa_min");
				Integer faixaMax = resSet.getInt("faixa_max");

				listaEst.add(estado);
				listaMin.add(faixaMin);
				listaMax.add(faixaMax);
			}
			this.setListaEstados(listaEst);
			this.setListaFaixaMin(listaMin);
			this.setListaFaixaMax(listaMax);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static Integer pesquisarCep(List<Integer> listaFaixaMax, List<Integer> listaFaixaMin, Integer cep) {

		Integer comeco = 0;
		Integer ultimo = (listaFaixaMax.size());
		Integer media;

		while (comeco <= ultimo) {

			media = (comeco + ultimo) / 2;

			if (cep >= listaFaixaMin.get(media) && cep <= listaFaixaMax.get(media)) {
				return media;
			} else if (cep >= listaFaixaMin.get(media) && cep >= listaFaixaMax.get(media)) {
				comeco = media + 1;
			} else if (cep <= listaFaixaMin.get(media) && cep <= listaFaixaMax.get(media)) {
				ultimo = media - 1;

			}
		}
		return -1;
	}

	public void carregarDados(Connection conn) {

		PreparedStatement create;
		PreparedStatement insert;

		try {
			create = conn.prepareStatement(
					"CREATE TABLE IF NOT EXISTS cep.localidade (id BIGINT NOT NULL auto_increment, estado varchar(2), faixa_min integer not null,faixa_max integer not null, primary key (id))");

			create.execute();

			insert = conn.prepareStatement(
					"INSERT INTO localidade (estado,faixa_min,faixa_max ) VALUES ('SP',01000000,19999999),( 'RJ',20000000,28999999),('ES',29000000,29999999),\r\n"
							+ "('MG',30000000,39999999),('BA',40000000,48999999),('SE',49000000,49999999),('PE',50000000,56999999),('AL',57000000,57999999),('PB',58000000,58999999),\r\n"
							+ "('RN',59000000,59999999),('CE',60000000,63999999),('PI',64000000,64999999),('MA',65000000,65999999),('PA',66000000,68899999),('AP',68900000,68999999),\r\n"
							+ "('AM',69400000,69899999),('RR',69300000,69389999),('AC',69900000,69999999),('DF',70000000,73699999),('GO',74000000,76799999),('RO',76800000,76999999),\r\n"
							+ "('TO',77000000,77995999),('MT',78000000,78899999),('MS',79000000,79999999),('PR',80000000,87999999),('SC',88000000,89999999),('RS',90000000,99999999)");
			insert.execute();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public List<String> getListaEstados() {
		return listaEstados;
	}

	public List<Integer> getFaixaMin() {
		return listaFaixaMin;
	}

	public List<Integer> getFaixaMax() {

		return listaFaixaMax;
	}

	public void setListaEstados(List<String> listaEstados) {
		this.listaEstados = listaEstados;
	}

	public void setListaFaixaMin(List<Integer> listaFaixaMin) {
		this.listaFaixaMin = listaFaixaMin;
	}

	public void setListaFaixaMax(List<Integer> listaFaixaMax) {
		this.listaFaixaMax = listaFaixaMax;
	}

}
