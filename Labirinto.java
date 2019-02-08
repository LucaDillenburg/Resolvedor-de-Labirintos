import coordenada.*;
import pilha.*;
import fila.*;
import java.nio.file.*; //para o file exists
import java.io.*; //para arquivo

/**
 * @author 17188, 17189
*/
public class Labirinto implements Cloneable
{
	protected String            caminhoArq;
	protected BufferedReader    arquivo;
	protected char[][]          labirinto;

	protected Coordenada        entrada;
	protected Coordenada        saida;
	protected Pilha<Coordenada> caminho;

	public Labirinto(String caminho) throws Exception
	{
		if(!new File(caminho).exists())
			throw new Exception("O arquivo nao existe!");

		this.arquivo = new BufferedReader(new FileReader(
									caminho));
		this.caminhoArq = caminho;
	}

	public void instanciarMatriz() throws Exception
	{
		int linhas;
		int colunas;

		// CONSEGUE O NUMERO DE LINHAS E COLUNAS
		try
		{
			// Ve se o arquivo texto tem o numero de linhas e colunas
			linhas = Integer.parseInt(arquivo.readLine());
			// lanca excecao caso não possam ser convertidos para int

			try
			{
				colunas = Integer.parseInt(arquivo.readLine());
				// lanca excecao caso não possam ser convertidos para int
			}catch(Exception erro)
			{
				throw new Exception("Nao ha o numero de colunas no arquivo texto!");
				//explica o erro: ou coloca os dois valores (linha e coluna) ou nao coloca nenhum
			}
		}catch(Exception erro)
		// Se o arquivo texto nao tem o numero de linhas e colunas, ele conta
		{
			linhas  = 1;
			// ele ja leu uma linha quando tentou transformar a primeira linha em int
			colunas = 0;
			boolean primeiraVez = true;

			while(this.arquivo.ready())
			{
				String linha = this.arquivo.readLine();

				if(primeiraVez)
				{
					colunas = linha.length();
					primeiraVez = false;
				}else
					if(linha.length()!=colunas)
					{
						int linhaErro = linhas+1;
						throw new Exception("A linha "+linhaErro+" nao esta com o numero de caracteres adequado.");
					}

				linhas++;
				// uma linha a mais
			}

			this.arquivo.close();
			this.arquivo = new BufferedReader(
					  	   new FileReader(
					  	   this.caminhoArq));
			//fecha o arquivo e abre de novo (para voltar ao comeco do arquivo)
		}

		// INSTANCIA A MATRIZ
		// instancia a matriz com o labirinto com as informacoes de linha e coluna dadas ou contadas
		this.labirinto = new char[linhas][colunas];
	}

	public void colocarNaMatriz() throws Exception
	{
		// COLOCA TUDO NA MATRIZ E VERIFICA QUANTAS ENTRADAS E SAIDAS
		int qtsE = 0;
		int qtsS = 0;

		this.entrada = new Coordenada();
		//nao precisa, porem java nao entende que nao dara erro depois
		this.saida   = new Coordenada();
		//nao precisa, porem java nao entende que nao dara erro depois

		// coloca os valores do arquivo texto labirinto na matriz
		// e conta quantas saidas e entradas ha, e onde estao
		for(int iLinha=0; iLinha<=this.labirinto.length-1; iLinha++)
		{
			if(!this.arquivo.ready())
				throw new Exception("Arquivo nao esta completo!");

			String linha = this.arquivo.readLine();

			if(linha.length()!=this.labirinto[0].length)
			{
				int linhaErro = iLinha+1;
				throw new Exception("A linha "+linhaErro+" nao esta com o numero de caracteres adequado.");
			}

			for(int iCol=0; iCol<=this.labirinto[0].length-1; iCol++)
				this.labirinto[iLinha][iCol] = linha.charAt(iCol);


			//tenta achar a entrada ou saida
			if(iLinha==0 || iLinha==this.labirinto.length-1)
			{
				for(int i=0; i<=this.labirinto[0].length-1; i++)
				{
					if(this.labirinto[iLinha][i]=='E')
					{
						qtsE++;
						this.entrada = new Coordenada(iLinha, i);
					}

					if(this.labirinto[iLinha][i]=='S')
					{
						qtsS++;
						this.saida = new Coordenada(iLinha, i);
					}
				}
			}else
			{
				if(this.labirinto[iLinha][0]=='E')
				{
					qtsE++;
					this.entrada = new Coordenada(iLinha, 0);
				}

				if(this.labirinto[iLinha][0]=='S')
				{
					qtsS++;
					this.saida = new Coordenada(iLinha, 0);
				}

				if(this.labirinto[iLinha][this.labirinto[0].length-1]=='E')
				{
					qtsE++;
					this.entrada = new Coordenada(iLinha, this.labirinto[0].length-1);
				}

				if(this.labirinto[iLinha][this.labirinto[0].length-1]=='S')
				{
					qtsS++;
					this.saida = new Coordenada(iLinha, this.labirinto[0].length-1);
				}
			}
		}
		this.arquivo.close();


		// lanca excecao caso existam uma ou mais entrada/saida
		if(qtsE!=1)
			throw new Exception("Ha nenhuma ou mais de uma entrada!");
		if(qtsS!=1)
			throw new Exception("Ha nenhuma ou mais de uma saida!");
	}

	public void resolverLabirinto()
	{
		try
		{
			this.caminho = new Pilha(this.labirinto.length*this.labirinto[0].length);
			//                                linhas              colunas
			Pilha<Fila<Coordenada>> possibilidades =
							new Pilha(this.labirinto.length*this.labirinto[0].length);
							//             linhas           colunas

			Coordenada atual = new Coordenada(entrada);

			boolean progressivo = true;

			Fila<Coordenada> fila = new Fila();
			//nao precisa, porem java nao entende que nao dara erro depois

			while(!atual.equals(this.saida))
			{
				if(progressivo)
					// procura as possibilidades de caminho a serem seguidos
					fila = this.possibilidadesAoRedor(atual);

				if(!fila.vazia())
				// modo progressivo: da um passo
				{
					progressivo = true;

					atual = new Coordenada(fila.getElemento());

					fila.desenfileire();

					possibilidades.empilhe(fila);

					if(this.labirinto[atual.getX()][atual.getY()]==' ')
					{
						this.labirinto[atual.getX()][atual.getY()]='*';
						// anda (se nao for a saída)
						this.caminho.empilhe(atual); //nao empilha a saida
					}
				}else
				// não existem mais possibilidades ao redor dessa coordenada
				// modo regressivo: volta até o cruzamento mais proximo e pega outro caminho
				{
					this.labirinto[atual.getX()][atual.getY()]=' ';
					// volta um quadrado

					// o caminho pode chegar vazio (se nao tiver nenhum espaco livre logo na entrada)
					if(this.caminho.vazia())
						throw new Exception("O labirinto nao tem um caminho para a saida!");

					this.caminho.desempilhe();

					if(this.caminho.vazia())
						throw new Exception("O labirinto nao tem um caminho para a saida!");

					atual = new Coordenada(this.caminho.getElemento());

					if(!possibilidades.getElemento().vazia())
						fila = new Fila(possibilidades.getElemento());

					possibilidades.desempilhe();
					progressivo = false;
				}
			}

			Pilha<Coordenada> inverso = new Pilha(this.labirinto.length*this.labirinto[0].length);

			while(!caminho.vazia())
			{
				inverso.empilhe(caminho.getElemento());
				caminho.desempilhe();
			}

			this.caminho = inverso;
		}catch(Exception erro)
		{}
	}


	protected Fila possibilidadesAoRedor(Coordenada atual)
	{
		Fila<Coordenada> fila=null;
		try
		{
			fila = new Fila(3);

			// em cima
			if(atual.getX()+1<=this.labirinto.length-1 && (labirinto[atual.getX()+1][atual.getY()]==' '
				|| labirinto[atual.getX()+1][atual.getY()]=='S'))
			{
				Coordenada livre = new Coordenada(atual.getX()+1, atual.getY());
				fila.enfileire(livre);
			}

			// direita
			if(atual.getY()+1<=this.labirinto[0].length-1 && (labirinto[atual.getX()][atual.getY()+1]==' '
				||labirinto[atual.getX()][atual.getY()+1]=='S'))
			{
				Coordenada livre = new Coordenada(atual.getX(), atual.getY()+1);
				fila.enfileire(livre);
			}

			// baixo
			if(atual.getX()-1>=0 && (labirinto[atual.getX()-1][atual.getY()]==' '
				|| labirinto[atual.getX()-1][atual.getY()]=='S'))
			{
				Coordenada livre = new Coordenada(atual.getX()-1, atual.getY());
				fila.enfileire(livre);
			}

			// esquerda
			if(atual.getY()-1>=0 && (labirinto[atual.getX()][atual.getY()-1]==' '
				|| labirinto[atual.getX()][atual.getY()-1]=='S'))
			{
				Coordenada livre = new Coordenada(atual.getX(), atual.getY()-1);
				fila.enfileire(livre);
			}
		}catch(Exception erro)
		{}

		return fila;
	}


	public String getCaminho()
	{
		String ret="";

		try{
			if(caminho==null)
			{
				ret += "Entrada: "+this.entrada+"\n";
				ret += "Saida: "+this.saida+"\n";
				ret += "Labirinto nao resolvido!";
			}else
			{
				ret += "Entrada: "+this.entrada+"\n";

				ret += this.caminho.toString();

				ret += "\nSaida: "+this.saida+"\n";
			}
		}catch(Exception erro)
		{}

		return ret;
	}

	public String getLabirinto()
	{
		String ret="";

		for(int l=0; l<=this.labirinto.length-1; l++)
			for(int c=0; c<=this.labirinto[0].length-1; c++)
				ret += c==this.labirinto[0].length-1?this.labirinto[l][c]+"\r\n":
					this.labirinto[l][c];
		return ret;
	}

	public String getCaminhoArq()
	{
		return this.caminhoArq;
	}

	public Coordenada getEntrada()
	{
		return this.entrada;
	}

	public Coordenada getSaida()
	{
		return this.saida;
	}

	// METODOS OBRIGATORIOS
	public String toString()
	{
		String ret = "Caminho Arq: "+this.caminhoArq+"\n";
		ret += "Labirinto: \n";
		ret += this.getLabirinto();
		ret += this.getCaminho();
		return ret;
	}

	public int hashCode()
	{
		int ret = 13;

		ret = ret*7 + this.caminhoArq.hashCode();
		ret = ret*7 + this.arquivo.hashCode();
		ret = ret*7 + this.entrada.hashCode();
		ret = ret*7 + this.saida.hashCode();
		ret = ret*7 + this.caminho.hashCode();

		for(int l=0; l<=this.labirinto.length-1; l++)
			for(int c=0; c<=this.labirinto[0].length-1; c++)
				ret = ret*7 + new Character(this.labirinto[l][c]).hashCode();

		return ret;
	}

	public boolean equals(Object obj)
	{
		if(obj==null)
			return false;
		if(obj==this)
			return true;

		if(!(obj instanceof Labirinto))
			return false;

		Labirinto lab = (Labirinto)obj;

		if(!this.caminhoArq.equals(lab.caminhoArq) || !this.arquivo.equals(lab.arquivo)
			|| !this.entrada.equals(lab.entrada) || !this.saida.equals(lab.saida) ||
			!this.caminho.equals(lab.caminho))
			return false;

		if(this.labirinto==null && lab.labirinto==null)
			return true;
		if((this.labirinto==null && lab.labirinto!=null) ||
			(this.labirinto!=null && lab.labirinto==null))
			return false;

		if(this.labirinto.length!=lab.labirinto.length ||
			this.labirinto[0].length!=lab.labirinto[0].length)
			return false;

		for(int l=0; l<=this.labirinto.length-1; l++)
			for(int c=0; c<=this.labirinto[0].length-1; c++)
				if(this.labirinto[l][c]!=lab.labirinto[l][c])
					return false;

		return true;
	}

	public Labirinto clone()
	{
		Labirinto ret = null;

		try
		{
			ret = new Labirinto(this);
		}catch(Exception erro)
		{}

		return ret;
	}

	public Labirinto(Labirinto modelo) throws Exception
	{
		if(modelo==null)
			throw new Exception("Labirinto nao instanciado!");

		this.caminhoArq = modelo.caminhoArq;
		this.arquivo = modelo.arquivo;
		this.entrada = new Coordenada(modelo.entrada);
		this.saida = new Coordenada(modelo.saida);
		this.caminho = new Pilha(modelo.caminho);

		for (int i=0; i<=modelo.labirinto.length-1; i++)
			for (int j=0; j<=modelo.labirinto[0].length-1; j++)
				this.labirinto[i][j] = modelo.labirinto[i][j];
	}
}