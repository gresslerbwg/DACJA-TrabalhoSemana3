import static org.junit.Assert.assertEquals;

import java.util.List;

import org.dbunit.Assertion;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.junit.Before;
import org.junit.Test;

public class TesteTrabalhoSemana3 {
	
	JdbcDatabaseTester jdt;

	@Before
	public void setUp() throws Exception {
		jdt = new JdbcDatabaseTester("org.postgresql.Driver","jdbc:postgresql://localhost/coursera","gressler","4d$kaj981A,,%uSDf-9sdfa.!23");
		FlatXmlDataFileLoader loader = new FlatXmlDataFileLoader();
		jdt.setDataSet(loader.load("/inicio.xml"));
		jdt.onSetup();
	}

	@Test
	public void ranking() {
		Usuario_DAO udao = new Usuario_DAO();
		List<Usuario> lista = udao.ranking();
		assertEquals(3, lista.size());
		assertEquals("manoel", lista.get(0).getLogin());
		assertEquals("maria", lista.get(1).getLogin());
		assertEquals("joao", lista.get(2).getLogin());
	}
	
	@Test
	public void inserir() throws Exception {
		Usuario u = new Usuario();
		u.setLogin("krisca");
		u.setEmail("krisca@gmail");
		u.setNome("Krisca");
		u.setSenha("senha");
		u.setPontos(12);
		
		Usuario_DAO udao = new Usuario_DAO();
		udao.inserir(u);
		
		IDataSet currentDataset = jdt.getConnection().createDataSet();
		ITable currentTable = currentDataset.getTable("USUARIO");
		
		FlatXmlDataFileLoader loader = new FlatXmlDataFileLoader();
		IDataSet expectedDataset = loader.load("/verifica.xml");
		ITable expectedTable = expectedDataset.getTable("USUARIO");
		
		Assertion.assertEquals(expectedTable, currentTable);
	}
	
	@Test
	public void recuperar(){
		Usuario_DAO udao = new Usuario_DAO();
		Usuario u = udao.recuperar("manoel");
		assertEquals("manoel", u.getLogin());
		assertEquals("manoel@gmail", u.getEmail());
		assertEquals("Manoel Antônio", u.getNome());
		assertEquals("senha", u.getSenha());
		assertEquals(25,u.getPontos());
	}
	
	@Test
	public void adicionarPontos(){
		Usuario_DAO udao = new Usuario_DAO();
		udao.adicionarPontos("manoel",2);
		Usuario u = udao.recuperar("manoel");
		assertEquals(27,u.getPontos());
	}
}

