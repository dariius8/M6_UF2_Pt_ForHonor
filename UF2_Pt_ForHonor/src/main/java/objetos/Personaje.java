package objetos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PERSONAJE")
public class Personaje {

	private int personaje_id;
	private String nombre_personaje;
	private int ataque;
	private int defensa;
	private int faccion_id;

	public Personaje() {

	}

	public Personaje(int personaje_id, String nombre_personaje, int ataque, int defensa, int faccion_id) {
		this.personaje_id = personaje_id;
		this.nombre_personaje = nombre_personaje;
		this.ataque = ataque;
		this.defensa = defensa;
		this.faccion_id = faccion_id;
	}

	@Column(name = "personaje_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getPersonaje_id() {
		return personaje_id;
	}

	public void setPersonaje_id(int personaje_id) {
		this.personaje_id = personaje_id;
	}

	@Column(name = "nombre_personaje")
	public String getNombre_personaje() {
		return nombre_personaje;
	}

	public void setNombre_personaje(String nombre_personaje) {
		this.nombre_personaje = nombre_personaje;
	}

	@Column(name = "ataque")
	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}

	@Column(name = "defensa")
	public int getDefensa() {
		return defensa;
	}

	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}

	@Column(name = "faccion_id")
	public int getFaccion_id() {
		return faccion_id;
	}

	public void setFaccion_id(int faccion_id) {
		this.faccion_id = faccion_id;
	}

}
