package objetos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FACCION")
public class Faccion {

	private int faccion_id;
	private String nombre_faccion;
	private String lore;

	public Faccion() {

	}

	public Faccion(int faccion_id, String nombre_faccion, String lore) {
		this.faccion_id = faccion_id;
		this.nombre_faccion = nombre_faccion;
		this.lore = lore;
	}

	@Column(name = "faccion_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getFaccion_id() {
		return faccion_id;
	}

	public void setFaccion_id(int faccion_id) {
		this.faccion_id = faccion_id;
	}

	@Column(name = "nombre_faccion")
	public String getNombre_faccion() {
		return nombre_faccion;
	}

	public void setNombre_faccion(String nombre_faccion) {
		this.nombre_faccion = nombre_faccion;
	}

	@Column(name = "lore")
	public String getLore() {
		return lore;
	}

	public void setLore(String lore) {
		this.lore = lore;
	}

	@Override
	public String toString() {
		return "Faccion [faccion_id=" + faccion_id + ", nombre_faccion=" + nombre_faccion + ", lore=" + lore + "]";
	}
}
