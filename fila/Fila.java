package fila;
import  java.lang.reflect.*;

/**
 * Fila generica e circular
 * @autor 17188, 17189
*/
public class Fila<X> implements Cloneable
{
	protected Object[] fila;
	protected float    taxaDeCrescimento;

	protected int inicio = 0;
 	protected int fim    = -1;
 	protected int qtd    = 0;

	/**
	 * Construtor sem parametro que inicializa a Fila com 10 posicoes no vetor e 50% de taxa de crescimento
	*/
	public Fila()
	{
		this.iniciacao(10, 50);
	}


	/**
	 * Construtor que inicializa a Fila com o tamanho dado e taxa de crescimento de 50%
	 * @param tamanho sera a quantidade de posicoes da fila
	 * @exception Exception throw exception se a fila fosse ter menos de uma posicao
	*/
	public Fila(int tamanho) throws Exception
	{
		if(tamanho<1)
			throw new Exception("Tamanho invalido!");

		this.iniciacao(tamanho, 50);
	}

	/**
	 * Construtor que inicializa a Fila com o tamanho e taxa de crescimento dados
	 * @param tamanho sera a quantidade de posicoes da fila
	 * @param taxa sera a taxa de crescimento usada para aumentar a fila quando esse acabar suas posicoes livres
	 * @exception Exception lanca excecao se a fila fosse ter menos de uma posicao, ou a taxa nao aumentaria em nada o tamanho da fila
	*/
	public Fila(int tamanho, float taxa) throws Exception
	{
		if(tamanho<=0)
			throw new Exception("Tamanho invalido!");

		if(taxa<=0)
			throw new Exception("Taxa invalida!");

		this.iniciacao(tamanho, taxa);
	}

	protected void iniciacao(int tamanho, float taxa)
	{
		this.fila = new Object[tamanho];
		this.taxaDeCrescimento = taxa;
	}

// METODOS ESPECIFICOS
	/**
	 * Adiciona a instancia dada ao final da fila
	 * @param x eh o objeto a ser armazenado no fim da fila
	 * @exeption Exception lanca excecao caso o parametro seja nulo
	*/
 	public void enfileire(X x) throws Exception
 	{
 		if(x==null)
			throw new Exception("Valor para ser enfileirado e nulo!");

		if(this.qtd==this.fila.length)
			this.cresca();

		if(this.fim==this.fila.length-1)
			this.fim = 0;
		else
			this.fim++;
		this.qtd++;

		this.fila[this.fim] = x instanceof Cloneable?this.meuCloneDeX(x):x;
 	}

 	protected void cresca()
 	{
 		float mult = 1+this.taxaDeCrescimento/100;

		int   tam  = (int)Math.ceil(this.fila.length*mult);

 		Object[] novaFila = new Object[tam];

		for(int i=0; i<=this.qtd-1; i++)
			this.fila[i] = this.fila[(this.inicio+i)%this.fila.length];

 		this.inicio = 0;
		this.fim    = this.qtd-1;

 		this.fila = novaFila;
 	}

	/**
	 * Metodo que retorna o primeiro elemento da fila
	 * @return X : retorna o primeiro elemento da fila
	 * @exeption Exception lanca excecao caso a fila esteja vazia
	*/
 	public X getElemento() throws Exception
 	{
 		if(this.qtd==0)
 			throw new Exception("Nao ha dados!");

 		return this.meuCloneDeX((X)this.fila[this.inicio]);
 	}

	/**
	 * Metodo que retira o primeiro da fila
	 * @exeption Exception lanca excecao caso a fila esteja vazia
	*/
 	public void desenfileire() throws Exception
 	{
		if(this.vazia())
		throw new Exception("Fila vazia.");

 		this.fila[this.inicio] = null;

 		if(this.inicio==this.qtd-1)
 			this.inicio = 0;
 		else
 			this.inicio++;

 		this.qtd--;
 	}

	/**
	 * Verifica se a fila está vazia
	 * @return boolean : retorna true se a fila estiver vazia, e false se houver no minimo um objeto na fila
	*/
 	public boolean vazia()
 	{
 		return this.qtd==0;
 	}

// MetodoS OBRIGATORIOS
	/**
	 * Metodo obrigatorio que retorna todos os objetos da fila numa String
	 * @return String : retorna todos os objetos da fila no formato {x1, x2, x3, ..., xn}
	*/
	public String toString()
	{
		String ret = "{";

		for(int i=0; i<=this.qtd-1; i++)
			ret+= this.fila[(this.inicio+i)%this.fila.length]
			+ (i==this.qtd-1?"":", ");

		ret+="}";

		return ret;
	}

	/**
	* Metodo obrigatorio que retorna um int que representa o objeto (this)
	* @return int : retorna o um número "unico" para cada Fila diferente
	*/
	public int hashCode()
	{
		int ret = 1;

		ret = ret*7 + new Float(this.taxaDeCrescimento).hashCode();
		ret = ret*7 + new Integer(this.inicio).hashCode();
		ret = ret*7 + new Integer(this.fim).hashCode();
		ret = ret*7 + new Integer(this.qtd).hashCode();

		for(int i=0; i<=this.qtd-1; i++)
			ret = ret*7 + this.fila[(this.inicio+i)%this.fila.length].hashCode();

		return ret;
	}

	/**
	* Metodo obrigatorio que verifica se o this e o outro objeto passado como parametro sao iguais
	* @return boolean : retorna false se os objetos forem diferentes e true se forem iguais
	* @param obj Objeto a ser comparado com o this
	*/
	public boolean equals(Object obj)
	{
		if(obj==null)
			return false;
		if(this==obj)
			return true;

		if(!(obj instanceof Fila))
			return false;

		Fila fil = (Fila)obj;

		if(fil.taxaDeCrescimento!=this.taxaDeCrescimento ||
			fil.qtd!=this.qtd)
			return false;

		for(int i=0; i<=this.qtd; i++)
			if(!(this.fila[(this.inicio+i)%this.fila.length].equals(fil.fila[(fil.inicio+i)%fil.fila.length])))
				return false;

		return true;
	}

// METODOS OBRIGATORIOS AS VEZES
	/**
	 * Construtor que copia todos as variaveis do parametro dado para o this
	 * @param modelo eh o modelo que tera as variaveis copiadas para o this (eh o molde para o this)
	 * @exeption Exception lanca excecao caso o modelo seja nulo
	*/
	public Fila(Fila modelo) throws Exception
	{
		if(modelo==null)
			throw new Exception("Fila nao instanciado!");

		this.qtd  = modelo.qtd;
		this.taxaDeCrescimento = modelo.taxaDeCrescimento;

		this.fila = new Object[modelo.fila.length];

		for(int i=0; i<=modelo.qtd-1; i++)
		{
			int iMod = (modelo.inicio+i)%this.fila.length;
			this.fila[i] = modelo.fila[iMod] instanceof Cloneable?
			this.meuCloneDeX((X)modelo.fila[iMod]):modelo.fila[iMod];
		}

 		this.inicio = 0;
		this.fim    = this.qtd-1;
	}

	/**
	 * Metodo que retorna o this clonado
	 * @return Objetct : retorna uma instancia da Classe Fila igual ao this
	*/
	public Object clone()
	{
		Fila ret = null;

		try
		{
			ret = new Fila(this);
		}catch(Exception erro)
		{}

		return ret;
	}

	protected X meuCloneDeX(X x)
	{
		X ret=null;

		try
		{
			Class<?> classe = x.getClass();
			Class<?>[] tipoParametroFormal = null;
			Method metodo = classe.getMethod("clone", tipoParametroFormal);
			Object[] parametroReal = null;
			ret = ((X)metodo.invoke(x, parametroReal));
		}catch(Exception erro)
		{
			erro.printStackTrace(System.err);
		}

		return ret;
	}

}