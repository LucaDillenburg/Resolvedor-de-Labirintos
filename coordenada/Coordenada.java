package coordenada;

/**
 * Classe que armazena (X, Y) e altera esses valores
 * @author 17188, 17189
*/
public class Coordenada implements Cloneable
{
	protected int x;
	protected int y;

	/**
	 * Construtor sem parametros, inicia a cordenada como (0,0)
	*/
	public Coordenada()
	{
		this.x = 0;
		this.y = 0;
	}

	/**
	* Construtor que inicia a Coordenada com : (0, parametroRecebido)
	* @param yy parametro passado torna-se o Y da Coordenada
	*/
	public Coordenada(int yy)
	{
		this.x = 0;
	 	this.y = yy;
	}

	/**
	 * Construtor com parametros, inicia a cordenada como os valores recebidos
	 * @param xx parametro passado torna-se o X da Coordenada
	 * @param yy parametro passado torna-se o Y da Coordenada
	*/
	public Coordenada(int xx, int yy)
	{
		this.x = xx;
		this.y = yy;
	}

	/**
	 * Metodo que retorna o X da Coordenada
	 * @return int : retorna o X da Coordenada
	*/
	public int getX()
	{
		return this.x;
	}

	/**
	 * Metodo que retorna o Y da Coordenada
	 * @return int : retorna o Y da Coordenada
	*/
	public int getY()
	{
		return this.y;
	}

	/**
	 * Metodo que muda o X da Coordenada
	 * @param xx torna-se o X da Coordenada
	*/
	public void setX(int xx)
	{
		this.x = xx;
	}

	/**
	 * Metodo que muda o Y da Coordenada
	 * @param yy torna-se o Y da Coordenada
	*/
	public void setY(int yy)
	{
		this.y = yy;
	}

// METODOS OBRIGATORIOS
	/**
	 * Metodo obrigatorio que retorna o this numa String
	 * @return String : retorna o this no formato (X, Y)
	*/
	public String toString()
	{
		return "("+this.x+","+this.y+")";
	}

	/**
	* Metodo obrigatorio que retorna um int que representa o objeto (this)
	* @return int : retorna o um número "unico" para cada Coordenada diferente
	*/
	public int hashCode()
	{
		int ret = 111;
		ret = ret*7 + new Integer(this.x).hashCode();
		ret = ret*7 + new Integer(this.y).hashCode();

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
		if(this==null)
			return true;

		if(!(obj instanceof Coordenada))
			return false;

		Coordenada coord = (Coordenada)obj;

		if(coord.x==this.x && coord.y==this.y)
			return true;

		return false;
	}

// METODOS OBRIGATORIOS AS VEZES
	/**
	 * Construtor que copia todos as variaveis do parametro dado para o this
	 * @param modelo eh o modelo que tera as variaveis copiadas para o this (eh o molde para o this)
	 * @exeption Exception lanca excecao caso o modelo seja nulo
	*/
	public Coordenada(Coordenada modelo) throws Exception
	{
		if(modelo==null)
			throw new Exception("Coordenada nao instanciada!");

		this.x = modelo.x;
		this.y = modelo.y;
	}

	/**
	 * Metodo que retorna o this clonado
	 * @return Objetct : retorna uma instancia da Classe Coordenada igual ao this
	*/
	public Object clone()
	{
		Coordenada ret = null;
		try
		{
			ret = new Coordenada(this);
		}catch(Exception erro)
		{}

		return ret;
	}
}