package Login;

import Interfaces.IABM;

import java.util.HashSet;
import java.util.Set;

public class LoginRepository implements IABM<Usuario> {

    Set<Usuario> setUsuarios;

    public LoginRepository() {

        this.setUsuarios = new HashSet<>();
    }


    public Usuario buscar(String nombre) {
        for (Usuario user : setUsuarios) {
            if (user.getUsername().equals(nombre)) {
                return user;
            }
        }
        return null;
    }

    public String BuscarCargo(Usuario usuario) {
        {
            for (Usuario user : setUsuarios) {
                if (user.equals(usuario)) {
                    return usuario.getCargo();
                }
            }
            return null;
        }
    }

    public Set<Usuario> getSetUsuarios() {
        return setUsuarios;
    }

    public boolean noContieneEspeciales(String str) {
        return str.matches("[a-zA-Z0-9]+");
    }

    @Override
    public void agregar(Usuario obj) {
        this.setUsuarios.add(obj);
    }

    @Override
    public void eliminar(Usuario obj) {
        this.setUsuarios.remove(obj);

    }

    @Override
    public void modificar(Usuario obj) {

    }
}