package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import objetos.Faccion;
import objetos.Personaje;

public class Main {

	public static void main(String[] args) {
		conexion();
		menu();
	}

	public static void conexion() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/forhonor", "root", "");
			System.out.println("BD ForHonor conectada.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	public static void menu() {
		Scanner lector = new Scanner(System.in);
		int i = 0;
		while (i != 6) {
			System.out.println("\nMENU");
			System.out.println("   1. Listar todos los personajes");
			System.out.println("   2. Modificar el ataque de un personaje");
			System.out.println("   3. Borrar un personaje");
			System.out.println("   4. Borrar una faccion (si la faccion no tiene personajes)");
			System.out.println("   5. Cambiar un personaje de faccion (Stored Procedure)");
			System.out.println("   6. Salir");
			System.out.print("Elige una opcion: ");
			i = lector.nextInt();
			if (i > 0 && i < 7) {
				switch (i) {
				case 1:
					listarPersonajes();
					break;
				case 2:
					modificarAtaque();
					break;
				case 3:
					borrarPersonaje();
					break;
				case 4:
					borrarFaccion();
					break;
				case 5:
					cambiarPersonaje();
					break;
				default:
					System.out.println("\nAdios!");
					break;
				}
			} else
				System.out.println("\nError! Valor incorrecto.");
		}
		lector.close();
	}

	public static void listarPersonajes() {
		// entity
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ForHonor");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		String sql = "SELECT p.personaje_id, p.nombre_personaje, p.ataque, p.defensa, p.faccion_id FROM Personaje p";
		Query query = em.createQuery(sql);
		// guardamos los resultados en una list de objetos
		List<Object[]> personajes = (List<Object[]>) query.getResultList();
		System.out.println("\n---Todos los personajes---");
		// recorremos cada objeto y lo printamos
		for (Object[] personaje : personajes) {
			Integer personaje_id = (Integer) personaje[0];
			String nombre_personaje = (String) personaje[1];
			Integer ataque = (Integer) personaje[2];
			Integer defensa = (Integer) personaje[3];
			Integer faccion_id = (Integer) personaje[4];
			System.out.println(
					personaje_id + "\t" + nombre_personaje + "\t" + ataque + "\t" + defensa + "\t" + faccion_id);
		}
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public static void modificarAtaque() {
		Scanner lector = new Scanner(System.in);
		// pedimos el id del personaje y el ataque nuevo que le actualizaremos
		System.out.println("\nInserta el ID del personaje:");
		int personaje_id = lector.nextInt();
		System.out.println("Inserta el nuevo ATAQUE del personaje:");
		int ataque = lector.nextInt();
		// entity
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ForHonor");
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			// buscamos objeto personaje por id y modificamos el ataque
			Personaje p = em.find(Personaje.class, personaje_id);
			p.setAtaque(ataque);
			em.merge(p);
			em.getTransaction().commit();
			System.out.println(
					"\nPersonaje " + p.getNombre_personaje() + " actualizado con ataque " + p.getAtaque() + ".");
		} catch (Exception e) {
			System.out.println("\nError! No se ha podido actualizar.");
		} finally {
			em.close();
			emf.close();
		}
	}

	public static void borrarPersonaje() {
		Scanner lector = new Scanner(System.in);
		// pedimos el id del personaje a borrar
		System.out.println("\nInserta el ID del personaje:");
		int personaje_id = lector.nextInt();
		// entity
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ForHonor");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		try {
			// obtenemos el objeto personaje pasandole el id y lo borramos
			Personaje p = em.getReference(Personaje.class, personaje_id);
			em.remove(p);
			em.getTransaction().commit();
			System.out.println("\nPersonaje " + p.getNombre_personaje() + " borrado correctamente.");
		} catch (Exception e) {
			System.out.println("\nError! No se ha podido borrar.");
		} finally {
			em.close();
			emf.close();
		}
	}

	public static void borrarFaccion() {
		Scanner lector = new Scanner(System.in);
		// pedimos el id de la faccion a borrar
		System.out.println("\nInserta el ID de la faccion:");
		int faccion_id = lector.nextInt();
		// entity
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ForHonor");
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			// obtenemos el objeto faccion pasandole el id y lo borramos
			Faccion f = em.getReference(Faccion.class, faccion_id);
			em.remove(f);
			em.getTransaction().commit();
			System.out.println("\nFaccion " + f.getNombre_faccion() + " borrada correctamente.");
		} catch (Exception e) {
			System.out.println("\nError! No se ha podido borrar.");
		} finally {
			em.close();
			emf.close();
		}
	}

	public static void cambiarPersonaje() {
		Scanner lector = new Scanner(System.in);
		// pedimos el id del personaje y el id de la faccion destino
		System.out.println("\nInserta el ID del personaje:");
		int personaje_id = lector.nextInt();
		System.out.println("Inserta el nuevo ID de la faccion:");
		int id_faccion_destino = lector.nextInt();
		// entity
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ForHonor");
		EntityManager em = emf.createEntityManager();
		StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("Change_Faction");
		try {
			em.getTransaction().begin();
			// tipos de parametro
			storedProcedure.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
			// le pasamos las variables por parametro
			storedProcedure.setParameter(1, personaje_id);
			storedProcedure.setParameter(2, id_faccion_destino);
			storedProcedure.execute();
			em.getTransaction().commit();
			System.out.println("\nPersonaje cambiado de faccion correctamente.");
		} catch (Exception e) {
			System.out.println("\nError! No se ha podido cambiar el personaje de faccion.");
		} finally {
			em.close();
			emf.close();
		}
	}
}
