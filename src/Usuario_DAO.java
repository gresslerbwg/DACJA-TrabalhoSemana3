import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Usuario_DAO implements UsuarioDAO {
	static{
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 //insere um novo usuário no banco de dados
	public void inserir(Usuario u){
		try{
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost/coursera","gressler","4d$kaj981A,,%uSDf-9sdfa.!23");
			
			String sql = "INSERT INTO usuario(login, email, nome, senha, pontos) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement stm = c.prepareStatement(sql);
			stm.setString(1, u.getLogin());
			stm.setString(2, u.getEmail());
			stm.setString(3, u.getNome());
			stm.setString(4, u.getSenha());
			stm.setInt(5, u.getPontos());
			stm.executeUpdate();
			
		}catch(SQLException e){
			throw new RuntimeException("Não foi possível executar o acesso para inserir o usuário", e);
		}
	}
	
	   //recupera o usuário pelo seu login
	public Usuario recuperar(String login){
		Usuario u = null;
		try{
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost/coursera","gressler","4d$kaj981A,,%uSDf-9sdfa.!23");

			String sql = "SELECT * FROM usuario WHERE login = ?";
			PreparedStatement stm = c.prepareStatement(sql);
			stm.setString(1, login);
			ResultSet rs = stm.executeQuery();

			if(rs.next()){
				u = new Usuario();
				u.setLogin(rs.getString("login"));
				u.setEmail(rs.getString("email"));
				u.setNome(rs.getString("nome"));
				u.setSenha(rs.getString("senha"));
				u.setPontos(rs.getInt("pontos"));
			}
			return u;

		}catch(SQLException e){
			throw new RuntimeException("Não foi possível executar o acesso para recuperar o usuário", e);
		}
	}
	   
	   //adiciona os pontos para o usuário no banco
	   public void adicionarPontos(String login, int pontos){
		   try{
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost/coursera","gressler","4d$kaj981A,,%uSDf-9sdfa.!23");
				
				String sql = "UPDATE usuario SET pontos = pontos + ? WHERE login = ?";
				PreparedStatement stm = c.prepareStatement(sql);
				stm.setInt(1, pontos);
				stm.setString(2, login);
				stm.executeUpdate();
				
			}catch(SQLException e){
				throw new RuntimeException("Não foi possível executar o acesso para atualizar os pontos do usuário", e);
			}
		   
	   }
	   
	   //retorna a lista de usuários ordenada por pontos (maior primeiro)
	   public List<Usuario> ranking(){
		   List<Usuario> ranking = new ArrayList<>();
			try{
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost/coursera","gressler","4d$kaj981A,,%uSDf-9sdfa.!23");
				
				String sql = "SELECT * FROM usuario ORDER BY pontos DESC";
				PreparedStatement stm = c.prepareStatement(sql);
				ResultSet rs = stm.executeQuery();
				while(rs.next()){
					Usuario u = new Usuario();
					u.setLogin(rs.getString("login"));
					u.setEmail(rs.getString("email"));
					u.setNome(rs.getString("nome"));
					u.setSenha(rs.getString("senha"));
					u.setPontos(rs.getInt("pontos"));
					ranking.add(u);
				}
				
			}catch(SQLException e){
				throw new RuntimeException("Não foi possível executar o acesso para recuperar o ranking", e);
			}
			return ranking;
		}
}
