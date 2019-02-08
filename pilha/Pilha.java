package pilha;
import  java.lang.reflect.*;

/**
 * Pilha generica
 * @autor 17188, 17189
*/
public class Pilha<X> implements Cloneable
{
	protected Object[] pilha;
	protected float    taxaDeCrescimento;
	protected int      topo = -1;

	/**
	 * Construtor sem parametro que inicializa a Pilha com 10 posicoes no vetor e 50% de taxa de crescimento
	*/	public Pilha()
	{
		this.iniciacao(10, 50);
	}

	/**
	 * Construtor que inicializa a Pilha com o tamanho dado e taxa de crescimento de 50%
	 * @param tamanho sera a quantidade de posicoes da pilha
	 * @exception Exception throw exception se a pilha fosse ter menos de uma posicao
	*/
	public Pilha(int tamanho) throws Exception
	{
		if(tamanho<=0)
			throw new Exception("Tamanho invalido!");

		this.iniciacao(tamanho, 50);
	}

	/**
	 * Construtor que inicializa a Pilha com o tamanho e taxa de crescimento dados
	 * @param tamanho sera a quantidade de posicoes da pilha
	 * @param taxa sera a taxa de crescimento usada para aumentar a pilha quando esse acabar suas posicoes livres
	 * @exception Exception lanca excecao se a pilha fosse ter menos de uma posicao, ou a taxa nao aumentaria em nada o tamanho da pilha
	*/
	public Pilha(int tamanho, float taxa) throws Exception
	{
		if(tamanho<=0)
			throw new Exception("Tamanho invalido!");

		if(taxa<=0)
			throw new Exception("Taxa invalida!");

		this.iniciacao(tamanho, taxa);
	}

	protected void iniciacao(int tamanho, float taxa)
	{
		this.pilha = new Object[tamanho];
		this.taxaDeCrescimento = taxa;
	}


// METODOS ESPECIFICOS
	/**
	 * Metodo que adiciona mais um elemento no topo da pilha
	 * @param x eh o objeto que sera adicionado no topo da pílha
	 * @exeption Exception lanca excecao caso o prametro seja nulo
	*/
	public void empilhe(X x) throws Exception
	{
		if(x==null)
			throw new Exception("Valor para ser empilhado e nulo!");

		if(this.topo==this.pilha.length-1)
			this.cresca();

		this.topo++;
		this.pilha[this.topo] = x instanceof Cloneable?this.meuCloneDeX(x):x;
		//this.pilha[this.topo] = x;
	}

	protected void cresca()
	{
		float mult = 1+(this.taxaDeCrescimento/100);
		int   tam  = (int)Math.ceil(mult*this.pilha.length);
		Object[] novaPilha = new Object[tam];

		for(int i=0; i<=this.topo; i++)
			novaPilha[i] = this.pilha[i];

		this.pilha = novaPilha;
	}

	/**
	 * Metodo que retorna o ultimo objeto adicionado a pilha
	 * @return X : retorna o objeto no topo da pilha
	 * @exeption Exception lanca excecao caso a pilha esteja vazia
	*/
 	public X getElemento() throws Exception
 	{
		if(this.topo==-1)
			throw new Exception("Nao ha elementos!");

 		return meuCloneDeX((X)this.pilha[this.topo]);
 	}

	/**
	 * Metodo que retira o ultimo valor adicionado na pilha (o que esta no topo)
	 * @exeption Exception lanca excecao caso a pilha esteja vazia
	*/
 	public void desempilhe() throws Exception
 	{
		if(this.vazia())
			throw new Exception("Pilha vazia.");

 		this.pilha[this.topo] = null;
 		this.topo--;
 	}

	/**
	 * Verifica se a pilha está vazia
	 * @return boolean : retorna true se a pilha estiver vazia e false se houver algum objeto nela
	*/
 	public boolean vazia()
 	{
 		return this.topo<0;
 	}

// METODOS OBRIGATORIOS
	/**
	 * Metodo obrigatorio que retorna todos os objetos da pilha numa String
	 * @return String : retorna todos os objetos da pilha no formato {x1, x2, x3, ..., xn}
	*/
	public String toString()
	{
		String ret = "{";

		for(int i=0; i<=this.topo; i++)
			ret+= this.pilha[i] + (i==this.topo?"":", ");

		ret+="}";

		return ret;
	}

	/**
	* Metodo obrigatorio que retorna um int que representa o objeto (this)
	* @return int : retorna o um número "unico" para cada Pilha diferente
	*/
	public int hashCode()
	{
		int ret = 1;

		ret = ret*7 + new Float(this.taxaDeCrescimento).hashCode();
		ret = ret*7 + new Integer(this.topo).hashCode();

		for(int i=0; i<=this.topo; i++)
			ret = ret*7 + this.pilha[i].hashCode();

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

		if(!(obj instanceof Pilha))
			return false;

		Pilha pil = (Pilha)obj;

		if(pil.taxaDeCrescimento!=this.taxaDeCrescimento ||
			pil.topo!=this.topo)
			return false;

		for(int i=0; i<=this.topo; i++)
			if(!this.pilha[i].equals(pil.pilha[i]))
				return false;

		return true;
	}

// MetodoS OBRIGATORIOS AS VEZES
	/**
	 * Construtor que copia todos as variaveis do parametro dado para o this
	 * @param modelo eh o modelo que tera as variaveis copiadas para o this (eh o molde para o this)
	 * @exeption Exception lanca excecao caso o modelo seja nulo
	*/
	public Pilha(Pilha modelo) throws Exception
	{
		if(modelo==null)
			throw new Exception("Pilha nao instanciada!");

		this.topo  = modelo.topo;
		this.taxaDeCrescimento = modelo.taxaDeCrescimento;

		this.pilha = new Object[this.pilha.length];
		for(int i=0; i<=this.topo; i++)
			this.pilha[i] = modelo.pilha[i] instanceof Cloneable?
			this.meuCloneDeX((X)modelo.pilha[i]):modelo.pilha[i];
	}

	/**
	 * Metodo que retorna o this clonado
	 * @return Objetct : retorna uma instancia da Classe Pilha igual ao this
	*/
	public Object clone()
	{
		Pilha ret = null;

		try
		{
			ret = new Pilha(this);
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
			ret = (X)metodo.invoke(x, parametroReal);
		}catch(Exception erro)
		{}

		return ret;
	}

}