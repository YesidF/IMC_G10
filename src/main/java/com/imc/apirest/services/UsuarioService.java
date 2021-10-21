package com.imc.apirest.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imc.apirest.converter.UsuarioConverter;
import com.imc.apirest.dtos.LoginRequestDTO;
import com.imc.apirest.dtos.LoginResponseDTO;
import com.imc.apirest.exceptions.GeneralServiceException;
import com.imc.apirest.exceptions.NoDataFoundException;
import com.imc.apirest.exceptions.ValidateServiceException;
import com.imc.apirest.models.Perfil;
import com.imc.apirest.models.Rol;
import com.imc.apirest.models.Usuario;
import com.imc.apirest.repositories.PerfilRepository;
import com.imc.apirest.repositories.RolRepository;
import com.imc.apirest.repositories.UsuarioRepository;
import com.imc.apirest.validators.UsuarioValidator;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService {
	@Autowired
	UsuarioRepository usuarioRepo;
	@Autowired
	PerfilRepository perfilRepo;
	@Autowired
	RolRepository rolRepo;
	@Autowired
	UsuarioConverter usuarioConverter;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Value("${jwt.password}")
	private String jwtSecret;
	
	@Transactional
	public Usuario save(Usuario usuario) {
		try {
			
			UsuarioValidator.save(usuario);
			
			Usuario existUsuario = this.usuarioRepo.findByEmail(usuario.getEmail()).orElse(null);
			if(existUsuario != null && existUsuario.getId() != usuario.getId()) throw new ValidateServiceException("El email de usuario ya existe");
			
			Perfil perfilExist = perfilRepo.findById(usuario.getPerfil().getId()).orElseThrow(()->new NoDataFoundException("El perfil no existe"));
			Rol rolExist = rolRepo.findById(usuario.getRol().getId()).orElseThrow(()->new NoDataFoundException("El rol no existe"));
			
			//encriptar el password
			String passEncriptado = this.passwordEncoder.encode(usuario.getPassword());
			usuario.setPassword(passEncriptado);
			
			if(usuario.getId()==null) {
				usuario.setPerfil(perfilExist);
				usuario.setRol(rolExist);
				Usuario newUsuario = this.usuarioRepo.save(usuario);
				return newUsuario;
			}else {
				Usuario usuarioExist = usuarioRepo.findById(usuario.getId()).orElseThrow(()->new NoDataFoundException("El Usuario no existe"));
				usuarioExist.setPerfil(perfilExist);
				usuarioExist.setRol(rolExist);
				usuarioExist.setNombre(usuario.getNombre());
				usuarioExist.setApellidos(usuario.getApellidos());
				usuarioExist.setTelefono(usuario.getTelefono());
				usuarioExist.setEmail(usuario.getEmail());
				usuarioExist.setGenero(usuario.getGenero());
				usuarioExist.setPassword(usuario.getPassword());
				return usuarioExist;
			}
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}
	
	public Usuario findById(Long id) {
		try {
			return this.usuarioRepo.findById(id).orElseThrow(()->new NoDataFoundException("El usuario no existe"));
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}
	
	//GetUsuario
	public List<Usuario> findAll(Pageable page){
		try {
			return usuarioRepo.findAll(page).toList();
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}
	
	//DeleteUsuario
	@Transactional
	public void delete(Long id) {
		try {
			Usuario usuario = this.usuarioRepo.findById(id).orElseThrow(()->new NoDataFoundException("El perfil no existe"));
			this.usuarioRepo.delete(usuario);
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}
	
	public LoginResponseDTO login(LoginRequestDTO request) {
		try {
			Usuario existUsuario = this.usuarioRepo.findByEmail(request.getEmail())
					.orElseThrow(()->new ValidateServiceException("Usuario o password incorrectos"));
			
			if(!passwordEncoder.matches(request.getPassword(), existUsuario.getPassword())) {
				throw new ValidateServiceException("Usuario o password incorrectos");
			}
			
			String token = this.createToken(existUsuario);
			
			return new LoginResponseDTO(usuarioConverter.fromEntity(existUsuario),token);
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}
	
	public String createToken(Usuario usuario) {
		Date fechaCreacion = new Date();
		Date fechaVencimiento = new Date(fechaCreacion.getTime()+(1000*60*60));
		
		return Jwts.builder()
				.setSubject(Long.toString(usuario.getId()))
				.setIssuedAt(fechaCreacion)
				.setExpiration(fechaVencimiento)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(token);
			return true;
		} catch (UnsupportedJwtException e) {
			log.error("JWT in a particular format/configuration that does not match the format expected by the application");
		}
		catch (MalformedJwtException e) {
			log.error("JWT was not correctly constructed and should be rejecte");
		}
		catch (SignatureException e) {
			log.error("Signature or verifying an existing signature of a JWT failed");
		}
		catch (ExpiredJwtException e) {
			log.error("JWT was accepted after it expired and must be rejected");
		}
		return false;
	}
	
	public String getUsernameFromToken(String jwt) {
		try {
			return Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(jwt).getBody().getSubject();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ValidateServiceException("Invalid Token");
		}
	}
}
