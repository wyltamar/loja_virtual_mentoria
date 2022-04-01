package mentoria.lojavirtual;

import mentoria.lojavirtual.util.ValidaCPF;
import mentoria.lojavirtual.util.ValidaCnpj;

public class TesteCPFCNPJ {

	public static void main(String[] args) {

		boolean isCnpj = ValidaCnpj.isCNPJ("88.797.605/0001-04");
		System.out.println("CNPJ é válido? "+isCnpj);
		
		boolean isCpf = ValidaCPF.isCPF("163.365.510-59");
		System.out.println("CPF é válido? "+isCpf);
		
	}

}
