import java.io.*; //para dar readline
import coordenada.*;
import pilha.*;
import fila.*;
import java.nio.file.*; //para o file exists

/**
 * @author 17188, 17189
*/
public class ResolvedorLabirinto
{
    public static void main (String[] args)
    {
		BufferedReader teclado = new BufferedReader(
								 new InputStreamReader(
						         System.in));

		String deNovo="";
		do
		{
			try
			{
				// Le o caminho para o arquivo onde o labirinto se encontra
				System.out.print("Digite o caminho para o arquivo (nao precisa ter o numero de linhas e colunas): ");
				String caminhoParaOArquivo = teclado.readLine();

				Labirinto labirinto = new Labirinto(caminhoParaOArquivo);

				// RESOLVE O LABIRINTO
				labirinto.instanciarMatriz();
				labirinto.colocarNaMatriz();
				labirinto.resolverLabirinto();

				// DAQUI PARA BAIXO ELE APENAS MOSTRA O LABIRINTO:
				System.out.println();

				boolean escolherCaminho = false;
				// o usuario soh pode escolher se quer ver o caminho ou nao se ele
				// quis ver algo antes do caminho. Se ele nao quis ver nada antes do caminho,
				// o caminho aparecera automaticamente.

				String resposta;
				boolean continuar;
				do
				{
					System.out.println("Deseja ver a solucao do labirinto aqui? (S/N)");
					resposta = teclado.readLine();
					continuar = !resposta.equals("s") && !resposta.equals("S") && !resposta.equals("n") && !resposta.equals("N");

					//escreve que as opcoes foram invalidas se ele nao escreveu "n", "N", "s" ou "S"
					if(continuar)
						System.out.println("Sua resposta nao esta dentro das opcoes dadas.");
				}while(continuar);

				System.out.println();
				if(resposta.equals("s") || resposta.equals("S"))
				{
					// mostrar a resolucao do labirinto no cmd
					System.out.println(labirinto.getLabirinto());
					escolherCaminho = true;
					// Se o usuario quis ver alguma solucao do labirinto antes
					//(no cmd ou em arquivo texto, ele pode escolher se quer ver as coordenadas do caminho ou nao)
				}

				do
				{
					System.out.println("Deseja ver a solucao do labirinto num arquivo texto? (S/N)");
					resposta = teclado.readLine();
					continuar = !resposta.equals("s") && !resposta.equals("S") && !resposta.equals("n") && !resposta.equals("N");

					//escreve que as opcoes foram invalidas se ele nao escreveu "n", "N", "s" ou "S"
					if(continuar)
						System.out.println("Sua resposta nao esta dentro das opcoes dadas.");
				}while(continuar);

				System.out.println();
				if(resposta.equals("s") || resposta.equals("S"))
				{
					// escreve o Labirinto ja resolvido na tela e, em um arquivo texto (para labirintos Malignos) simultaneamente
					BufferedWriter escritor = new BufferedWriter(new FileWriter("LabirintoResolvido.txt"));
					escritor.append("RESOLUCAO DO LABIRINTO: "+caminhoParaOArquivo+ "\r\n");
					escritor.append("\r\n"); //pula de linha no arquivo texto

					// mostrar o conteudo do labirinto
					escritor.append(labirinto.getLabirinto());

					escritor.close();
					System.out.println("A resolucao do Labirinto esta tambem no arquivo 'LabirintoResolvido.txt'.");
					System.out.println();

					escolherCaminho = true;
					// Se o usuario quis ver alguma solucao do labirinto antes
					//(no cmd ou em arquivo texto, ele pode escolher se quer ver as coordenadas do caminho ou nao)
				}

				resposta = ""; // zera a variavel

				if(escolherCaminho)
				{
					do
					{
						System.out.println("Deseja ver as coordenadas do caminho ate a saida? (S/N)");
						resposta = teclado.readLine();
						continuar = !resposta.equals("s") && !resposta.equals("S") && !resposta.equals("n") && !resposta.equals("N");

						//escreve que as opcoes foram invalidas se ele nao escreveu "n", "N", "s" ou "S"
						if(continuar)
							System.out.println("Sua resposta nao esta dentro das opcoes dadas.");
					}while(continuar);
				}else
					System.out.println("Ja que voce nao quis ver o labirinto de nenhuma forma, o minimo que"+
								"podemos fazer por voce eh mostrar as coordenadas da entrada ateh a saida!");

				if(!escolherCaminho || resposta.equals("s") || resposta.equals("S"))
				{
					// Escreve todas as coordenadas usadas para sair do labirinto, da entrada até a saida
					System.out.println("Coordenadas do caminho ate a SAIDA: ");

					System.out.println(labirinto.getCaminho());

					System.out.println();
				}

				System.out.println();
			}
			catch (Exception erro)
			{
				// Exibir o erro (nao tem o que ser consertado: erro do usuario)
				System.out.println();
				System.out.println();
				System.err.println(erro);
				System.out.println();
			}

			// pergunta se o usuario quer tentar outro labirinto
			boolean continuar;
			do
			{
				System.out.println("Deseja escolher outro labirinto? (S/N)");
				try
				{
					deNovo = teclado.readLine();
					continuar = !deNovo.equals("s") && !deNovo.equals("S") && !deNovo.equals("n") && !deNovo.equals("N");
				}catch(Exception erro)
				{
					continuar = true;
					// Se der algum erro no readLine, ele pergunta denovo
				}

				//escreve que as opcoes foram invalidas se ele nao escreveu "n", "N", "s" ou "S"
				if(continuar)
					System.out.println("Sua resposta nao esta dentro das opcoes dadas.");
			}while(continuar);

			System.out.println();
			System.out.println();
		}
        while(deNovo.equals("s") || deNovo.equals("S"));

		System.out.println("Obrigado por usar nosso programa! Tenha um bom dia! :D ");
		System.out.println();
		System.out.println();
        System.out.println("Pressione [Enter] para sair...");
        try
        {
			System.in.read();
		}catch(Exception erro)
		{} //nao chegar aqui
    }
}