package theotherhalf.superconference.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import theotherhalf.superconference.api.jwt.*;
import theotherhalf.superconference.dto.UserDTO;
import theotherhalf.superconference.exceptions.ValidationException;


@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "api/register", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception
    {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(authenticationRequest.getEmail());
        userDTO.setPassword(authenticationRequest.getPassword());
        userDTO.setfirstName(authenticationRequest.getFirstName());
        userDTO.setlastName(authenticationRequest.getLastName());
        if(null == userDTO.getEmail() || null == userDTO.getfirstName() || null == userDTO.getlastName() || null == userDTO.getPassword())
        {
            throw new ValidationException("[ERROR] Invalid credentials entered.");
        }
        this.userDetailsService.save(userDTO);

//        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails, userDTO.getEmail());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody JwtRequest authenticationRequest) throws Exception
    {
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails, authenticationRequest.getEmail());

        UserDTO dto =this.userDetailsService.getUserFromEmail(authenticationRequest.getEmail());
        return ResponseEntity.ok(new JwtLoginResponse(token, dto.getlastName(), dto.getfirstName()));
    }

    private void authenticate(String username, String password) throws Exception
    {
        try
        {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (DisabledException e)
        {
            throw new Exception("USER_DISABLED", e);
        }
        catch (BadCredentialsException e)
        {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

    }

}
