package Login;

import Excepciones.ExcepcionCamposVacios;
import Excepciones.ExcepcionCaracteresEspeciales;
import Excepciones.ExcepcionNombreNumerico;
import Plato.Colores.Colores;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class LoginController {

    private LoginRepository loginRepository;
    private LoginView loginView;
    private Gson gson = new Gson();
    private static final String PATH = "src/main/resources/Usuarios.json";


    public LoginController(LoginRepository loginRepository, LoginView loginView) {
        this.loginRepository = loginRepository;
        this.loginView = loginView;
        this.loadGestionUsuario();
    }

    public void loadGestionUsuario() {
        try (Reader reader = new FileReader(PATH);) {
            Type type = new TypeToken<Set<Usuario>>() {
            }.getType();
            loginRepository.setUsuarios = gson.fromJson(reader, type);
            if (this.loginRepository.setUsuarios == null) {
                this.loginRepository.setUsuarios = new HashSet<>();
            } else {
                this.loginRepository.setUsuarios.forEach(usuario -> {
                    loginRepository.agregar(usuario);
                });

            }
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }

    public LoginRepository getLoginRepository() {
        return loginRepository;
    }

    public void setLoginRepository(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    public void Update() {
        try (Writer writer = new FileWriter(PATH);) {
            gson.toJson(this.loginRepository.setUsuarios, writer);
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }

    public void addUser() throws ExcepcionCamposVacios, ExcepcionNombreNumerico {
        //abrir el archivo
        try {
            Usuario usuario = null;
            boolean flag= false;
            while(!flag) {
                usuario = this.loginView.solicitarDatosRegistro();
                if (loginRepository.setUsuarios.contains(usuario)) {
                    Colores.printInColor("Usuario ya cargado en el sistema", Colores.RED);
                } else {
                    flag=true;
                }
            }
            String cargo = loginView.pedirCargo();
            Usuario usuarioFinal = new Usuario(usuario.getUsername(), usuario.getPassword(),cargo);
            this.loginRepository.agregar(usuarioFinal);
            this.loginView.control();
            this.Update();
        } catch (ExcepcionCamposVacios excepcionCamposVacios) {
            System.out.println(excepcionCamposVacios.getMessage());
        } catch (ExcepcionNombreNumerico excepcionNombreNumerico) {
            System.out.println(excepcionNombreNumerico.getMessage());
        }

    }

    public Usuario iniciarSesion() throws ExcepcionCamposVacios {
        {
            loadGestionUsuario();
            Usuario usuarioBuscado = null;
            Usuario usuario;
            Boolean ok;
            try {
                usuario = loginView.solicitarDatosRegistro();
                usuarioBuscado = loginRepository.buscar(usuario.getUsername());
                if(usuarioBuscado==null || !usuarioBuscado.getPassword().equals(usuario.getPassword())) {
                    Colores.printInColor("Nombre de usuario o contraseña incorrectos", Colores.RED);
                    usuarioBuscado = null;
                }
            } catch (ExcepcionCamposVacios excepcionCamposVacios) {
                System.out.println(excepcionCamposVacios.getMessage());
            } catch (ExcepcionNombreNumerico e) {
                System.out.println(e.getMessage());
            }
            return usuarioBuscado;

        }
    }

    public void eliminarUsuario(){
        loadGestionUsuario();
        String nombreUser = loginView.pedirNombreUsuario();
        Usuario usuarioBuscado = loginRepository.buscar(nombreUser);
        if(usuarioBuscado==null) {
            Colores.printInColor("Nombre de usuario no encontrado", Colores.RED);
        }
        loginRepository.eliminar(usuarioBuscado);
        Update();
    }

    public void mostrarUsuarios(){
        loadGestionUsuario();
        loginView.mostrarUsuarios(loginRepository.getSetUsuarios());
    }


}
