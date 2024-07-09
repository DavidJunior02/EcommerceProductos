package com.curso.ecommerce;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.repository.IUsuarioRepository;
import com.curso.ecommerce.service.IUsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UsuarioServiceTest {

    @Autowired
    private IUsuarioRepository usuarepo;

    @Test
    public void crearUsuarioAdminTest() {
        // Crear un usuario administrador para pruebas
        Usuario admin3 = new Usuario();
        admin3.setNombre("Administrador Prueba");
        admin3.setUsername("admin");
        admin3.setEmail("admin@example.com");
        admin3.setTipo("ADMIN");
        admin3.setDireccion("mi direccion");
        admin3.setPassword("passwaord"); // Aquí puedes usar la contraseña en texto plano para pruebas

        usuarepo.save(admin3);

       
    }
}