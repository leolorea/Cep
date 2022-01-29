package localidade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class BuscaCep {

	public static void main(String[] args) {
		Cep busca = new Cep();

		List<Integer> listaFaixaMax = busca.getFaixaMax();
		List<Integer> listaFaixaMin = busca.getFaixaMin();
		List<String> listaEstados = busca.getListaEstados();

		System.out.println("Digite o número do Cep:");
		Scanner scanner = new Scanner(System.in);
		Integer cep = scanner.nextInt();
		if (cep < 1000000 || cep > 99999999) {
			System.out.println(-1 + " Cep inválido");
		}

		Integer resultado = Cep.pesquisarCep(listaFaixaMax, listaFaixaMin, cep);
		System.out.println(listaEstados.get(resultado));

		scanner.close();

	}
}
